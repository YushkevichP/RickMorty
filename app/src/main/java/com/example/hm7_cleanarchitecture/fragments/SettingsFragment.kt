package com.example.hm7_cleanarchitecture.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import com.example.hm7_cleanarchitecture.data.service.NightModeService
import com.example.hm7_cleanarchitecture.databinding.FragmentSettingsBinding
import com.example.hm7_cleanarchitecture.domain.model.NightMode
import com.example.hm7_cleanarchitecture.utilities.networkChangeFlow
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

//todo
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val nightModeService by inject<NightModeService>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentSettingsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkChecker(view)

        with(binding) {
            when (nightModeService.nightMode) {
                NightMode.DARK -> radioButtonDark
                NightMode.LIGHT -> radioButtonLight
                NightMode.SYSTEM -> radioButtonSystem
            }.isChecked = true

            radioGroup.setOnCheckedChangeListener { _, buttonId ->
                val (prefsMode, systemMode) = when (buttonId) {
                    radioButtonDark.id -> NightMode.DARK to AppCompatDelegate.MODE_NIGHT_YES
                    radioButtonLight.id -> NightMode.LIGHT to AppCompatDelegate.MODE_NIGHT_NO
                    radioButtonSystem.id -> NightMode.SYSTEM to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    else -> error("incorrect buttonId $buttonId")
                }

                nightModeService.nightMode = prefsMode
                AppCompatDelegate.setDefaultNightMode(systemMode)
            }

            ViewCompat.setOnApplyWindowInsetsListener(appBar) { _, insets ->
                appBar.updatePadding(
                    top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                )
                WindowInsetsCompat.CONSUMED
            }

        }


    }

    private fun networkChecker(view: View) {
        requireContext().networkChangeFlow
            .onEach {
                when (it) {
                    true -> Log.d("check", "Есть конекшн")
                    false -> Snackbar.make(view, "Нет сети", Snackbar.LENGTH_LONG).show()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
