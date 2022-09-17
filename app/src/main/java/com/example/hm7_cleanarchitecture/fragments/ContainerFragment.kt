package com.example.hm7_cleanarchitecture.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hm7_cleanarchitecture.R
import com.example.hm7_cleanarchitecture.databinding.FragmentContainerBinding

class ContainerFragment : Fragment() {
    private var _binding: FragmentContainerBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentContainerBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

                with(binding) {
            val nestedController =
                (childFragmentManager.findFragmentById(R.id.main_container_view) as NavHostFragment)
                    .navController
            navigationView.setupWithNavController(nestedController)
        }


//        with(binding){
//            val navController = childFragmentManager.findFragmentById(R.id.main_container_view)
//                    as NavHostFragment
//            navigationView.setupWithNavController(navController.navController)
//        }
    }
}