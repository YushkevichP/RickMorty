package com.example.hm7_cleanarchitecture.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hm7_cleanarchitecture.ItemAdapter
import com.example.hm7_cleanarchitecture.R

import com.example.hm7_cleanarchitecture.databinding.FragmentSearchBinding
import com.example.hm7_cleanarchitecture.domain.model.ItemType
import com.example.hm7_cleanarchitecture.utilities.networkChangeFlow
import com.example.hm7_cleanarchitecture.utilities.searchQueryFlow
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

        networkChecker(view)
        initRecycler()

        with(binding) {
            toolbar.searchQueryFlow
                .onEach {
                    Snackbar.make(view, it.toString(), Snackbar.LENGTH_LONG).show()
                    viewModel.searcQueryChanger(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        viewModel.dataSearchFlow
            .onEach {
                val list = it.map {
                    ItemType.Content(it)
                }
                binding.progressCircular.isVisible = false
                personAdapter.submitList(list)

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        setInsets()
    }

    private fun initRecycler() {
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = personAdapter
            recyclerView.addSpaceDecoration(resources.getDimensionPixelSize(R.dimen.bottom_space))
            swipeLayout.setOnRefreshListener {
                //  viewModel.onRefresh()
            }
            recyclerView.addPaginationScrollFlow(layoutManager, 6)
                .onEach {
                    // viewModel.onLoadMore()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
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

    private fun setInsets() {
        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(appBar) { _, insets ->
                appBar.updatePadding(
                    top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                )
                WindowInsetsCompat.CONSUMED
            }
        }
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