package com.example.hm7_cleanarchitecture.googlemap

import android.Manifest
import android.annotation.SuppressLint

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import com.example.hm7_cleanarchitecture.databinding.FragmentMapBinding
import com.example.hm7_cleanarchitecture.utilities.networkChangeFlow
import com.example.hm7_cleanarchitecture.viewmodels.CountryViewModel
import com.example.hm7_cleanarchitecture.viewmodels.TestViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.qualifier


class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<CountryViewModel>()

    private var googleMap: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null


    @SuppressLint("MissingPermission")  // запрашиваем пермишн
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted) {

            // Подписываемся на обновления локации если разрешение получено
            viewModel
                .getLocationFlow()
                .onEach { updatedLocation ->

                    //когда приходит локация, то мы ее отрисовваываем через лисенер
                    locationListener?.onLocationChanged(updatedLocation)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentMapBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().networkChangeFlow
            .onEach {
                when (it) {
                    true -> Log.d("check", "Есть конекшн")
                    false -> Snackbar.make(view, "Нет сети", Snackbar.LENGTH_LONG).show()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)


        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        //---- current Location + camera annimation
        viewModel.currentLocationFlow
            .onEach { location ->
                location?.let {
                    moveCameraToLocation(location)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        //чтоб дернуть флоу с локешном
        viewModel.loadStartLocation()

        //init Map
        binding.mapView.getMapAsync { map ->
            googleMap = map.apply {
                uiSettings.isCompassEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
                uiSettings.isZoomControlsEnabled = true
            }

            map.isMyLocationEnabled = hasLocationPermission()

            map.setLocationSource(object : LocationSource {
                override fun activate(listener: LocationSource.OnLocationChangedListener) {
                    locationListener = listener
                }

                override fun deactivate() {
                    locationListener = null
                }
            })

            //get list of countries and show it ont the map
            viewModel.getcountriesFlow
                .onEach { list ->
                    list.map { country ->
                        map.addMarker(
                            MarkerOptions()
                                .title(country.name)
                                .position(LatLng(country.latitude, country.longitude))
                        )
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        binding.mapView.onCreate(savedInstanceState)
        setInsets(view)
        //setInsetsFotBar()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
        googleMap = null
        _binding = null
    }

    //анимация камеры
    private fun moveCameraToLocation(location: Location) {
        val current = LatLng(location.latitude, location.longitude)
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(current, 17f)
        )
    }

    //проверка пермишна
    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    //ставим инсеты,чтоб за систембары не заползало
    private fun setInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            googleMap?.setPadding(
                systemBarInsets.left,
                systemBarInsets.top,
                systemBarInsets.right,
                systemBarInsets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }
}
