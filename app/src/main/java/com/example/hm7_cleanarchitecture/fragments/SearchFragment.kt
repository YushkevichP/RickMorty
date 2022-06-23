package com.example.hm7_cleanarchitecture.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hm7_cleanarchitecture.ItemAdapter
import com.example.hm7_cleanarchitecture.R

import com.example.hm7_cleanarchitecture.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) {
            "OOPS"
        }

//
//    private val personAdapter by lazy(LazyThreadSafetyMode.NONE) {
//        ItemAdapter(requireContext()) { person ->
//            findNavController().navigate(
//                ListFragmentDirections.toDetails(person.id)
//            )
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentSearchBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchUserMenu()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun searchUserMenu() {
        with(binding) {
            toolbar.inflateMenu(R.menu.menu_search)
            toolbar.setOnMenuItemClickListener {
                //https://www.youtube.com/watch?v=CTvzoVtKoJ8&ab_channel=yoursTRULY
                when (it.itemId) {
                    R.id.search_users -> {
                        val searchView = it.actionView as? SearchView
                        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
//                                val listUser = userDao.getAllUsers()
//                                listUser.toMutableList()
//                                val filteredListUsers = listUser.filter {
//                                    it.firstName.toString().contains(newText ?: "", true)
//                                            || it.secondName.toString()
//                                        .contains(newText ?: "", true)
//                                }
//                                adapter.submitList(filteredListUsers)
                                //Toast.makeText(requireContext(),"$sortedList", Toast.LENGTH_LONG).show()
                                return true
                            }
                        })

                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }
}
