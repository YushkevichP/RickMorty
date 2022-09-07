//package com.example.less21_androidcomponents.googlemap
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//
//import com.example.hm7_cleanarchitecture.databinding.FragmentCustomMapBinding
//import com.example.hm7_cleanarchitecture.googlemap.LocationService
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.launch
//import org.koin.android.ext.android.inject
//
//
//class CustomMapFragment : Fragment() {
//
//    private var _binding: FragmentCustomMapBinding? = null
//    private val binding get() = requireNotNull(_binding)
//
//    //koin - dependency injection
//    private val locationService by inject<LocationService>()
//
//
//    @SuppressLint("MissingPermission")
//    private val permissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { permissionGranted ->
//        if (permissionGranted) {
//
//            subscribeLocationUpdates()
////            viewLifecycleOwner.lifecycleScope.launch {
////                val location = locationService.getCurrentLocation() ?: return@launch
////
////                Toast.makeText(requireContext(),
////                    "Location: ${location.latitude}, ${location.longitude}",
////                    Toast.LENGTH_SHORT).show()
////            }
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View {
//        return FragmentCustomMapBinding.inflate(inflater, container, false)
//            .also { _binding = it }
//            .root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        with(binding) {
//            button.setOnClickListener {
//                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun subscribeLocationUpdates() {
//
//            locationService
//                .getLocationFlow()
//                .onEach {
//                    delay(3000)
//                    binding.textResult.text = "Location: ${it.latitude}, ${it.longitude}"
//                }
//                .launchIn(viewLifecycleOwner.lifecycleScope)
//
//    }
//}