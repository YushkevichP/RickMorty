package com.example.hm7_cleanarchitecture.fragments

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.example.hm7_cleanarchitecture.domain.model.ItemType
import com.example.hm7_cleanarchitecture.domain.model.LceState
import com.example.hm7_cleanarchitecture.utilities.networkChangeFlow
import com.example.hm7_cleanarchitecture.utilities.searchQueryFlow
import com.example.hm7_cleanarchitecture.viewmodels.ListViewModel
import com.example.hm7_cleanarchitecture.viewmodels.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) {
            "OOPS"
        }

    private val viewModel by viewModel<SearchViewModel>()

    private val personAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ItemAdapter(requireContext()) { person ->
            findNavController().navigate(
                ListFragmentDirections.toDetails(person.id)
            )
        }
    }

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

        requireContext().networkChangeFlow
            .onEach {
                when (it) {
                    true -> Log.d("check", "Есть конекшн")
                    false -> Snackbar.make(view, "Нет сети", Snackbar.LENGTH_LONG).show()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        requireContext().networkChangeFlow
            .onEach {
                when (it) {
                    true -> Log.d("check", "Есть конекшн")
                    false -> Snackbar.make(view, "Нет сети", Snackbar.LENGTH_LONG).show()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.dataFlow
            .onEach { uiState ->
                val pageList = if (uiState.persons.isNotEmpty()) {
                    uiState.persons.map {
                        ItemType.Content(it)
                    } + ItemType.Loading
                } else uiState.persons.map {
                    ItemType.Content(it)
                }

                personAdapter.submitList(pageList)
                binding.progressCircular.isVisible = uiState.persons.isEmpty()
                binding.swipeLayout.isRefreshing = false

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = personAdapter
            recyclerView.addSpaceDecoration(resources.getDimensionPixelSize(R.dimen.bottom_space))
            swipeLayout.setOnRefreshListener {
                viewModel.onRefresh()
            }

            recyclerView.addPaginationScrollFlow(layoutManager, 6)
                .onEach {
                    viewModel.onLoadMore()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}





//with(binding) {
//
//    toolbar.searchQueryFlow
//        .onStart { emit("HELLO") }
//        .debounce(1000)
//        .mapLatest {
//            it //getPersonfrom smt  --- ретрофитовский запрос всунуть асинхронный
//        }.onEach {
//            // сюда самбитить лист.
//            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
//        }.launchIn(viewLifecycleOwner.lifecycleScope)
//}

//
//class SearchFragment : Fragment() {
//
//    private var _binding: FragmentSearchBinding? = null
//    private val binding: FragmentSearchBinding
//        get() = requireNotNull(_binding) {
//            "OOPS"
//        }
//
//    private val viewModel by viewModel<SearchViewModel>()
//
//    private val personAdapter by lazy(LazyThreadSafetyMode.NONE) {
//        ItemAdapter(requireContext()) { person ->
//            findNavController().navigate(
//                ListFragmentDirections.toDetails(person.id)
//            )
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View {
//        return FragmentSearchBinding.inflate(inflater, container, false)
//            .also { binding ->
//                _binding = binding
//            }.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        requireContext().networkChangeFlow
//            .onEach {
//                when (it) {
//                    true -> Log.d("check", "Есть конекшн")
//                    false -> Snackbar.make(view, "Нет сети", Snackbar.LENGTH_LONG).show()
//                }
//            }.launchIn(viewLifecycleOwner.lifecycleScope)
//
//
//        with(binding) {
//            val layoutManager = LinearLayoutManager(requireContext())
//            recyclerView.layoutManager = layoutManager
//            recyclerView.adapter = personAdapter
//            recyclerView.addSpaceDecoration(resources.getDimensionPixelSize(R.dimen.bottom_space))
//            swipeLayout.setOnRefreshListener {
//                //  viewModel.onRefresh()
//            }
//            recyclerView.addPaginationScrollFlow(layoutManager, 6)
//                .onEach {
//                    //    viewModel.onLoadMore()
//                }
//                .launchIn(viewLifecycleOwner.lifecycleScope)
//        }
//
//
//
////        with(binding) {
////            toolbar.searchQueryFlow
////                .onEach {
////                    viewModel.onQueryChanged(it)
////                }.launchIn(viewLifecycleOwner.lifecycleScope)
////        }
//
//
//        viewModel.searchFlow
//            .onEach {
//                val list = it.map {
//                    ItemType.Content(it)
//                }
//                personAdapter.submitList(list)
//            }
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//}