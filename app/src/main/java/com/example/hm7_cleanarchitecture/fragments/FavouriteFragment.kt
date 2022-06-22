//package com.example.hm7_cleanarchitecture.fragments
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.view.isVisible
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.hm7_cleanarchitecture.ItemAdapter
//import com.example.hm7_cleanarchitecture.R
//import com.example.hm7_cleanarchitecture.databinding.FragmentFavouritesListBinding
//import com.example.hm7_cleanarchitecture.domain.model.ItemType
//import com.example.hm7_cleanarchitecture.domain.model.LceState
//import com.example.hm7_cleanarchitecture.viewmodels.FavouriteViewModel
//import com.example.hm7_cleanarchitecture.viewmodels.PersonDetailsViewModel
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.launch
//import org.koin.androidx.viewmodel.ext.android.viewModel
//import org.koin.core.parameter.parametersOf
//
//class FavouriteFragment : Fragment() {
//
//    private var _binding: FragmentFavouritesListBinding? = null
//    private val binding: FragmentFavouritesListBinding
//        get() = requireNotNull(_binding) {
//            "OOPS"
//        }
//
//    private val viewModel by viewModel<FavouriteViewModel>()
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
//        return FragmentFavouritesListBinding.inflate(inflater, container, false)
//            .also { binding ->
//                _binding = binding
//            }.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        with(binding) {
//            val layoutManager = LinearLayoutManager(requireContext())
//            recyclerView.layoutManager = layoutManager
//            recyclerView.adapter = personAdapter
//            recyclerView.addSpaceDecoration(resources.getDimensionPixelSize(R.dimen.bottom_space))
//            swipeLayout.setOnRefreshListener {
//                //  viewModel.onRefresh()
//            }
//
//            recyclerView.addPaginationScrollFlow(layoutManager, 6)
//                .onEach {
//                    //   viewModel.onLoadMore()
//                }
//                .launchIn(viewLifecycleOwner.lifecycleScope)
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.getDataFlow()
//                .onEach {
//                    when (it) {
//                        is LceState.Content -> {
//                            val favourites = it.data.map {
//                                ItemType.Content(it)
//                            } + ItemType.Loading
//
//                            personAdapter.submitList(favourites)
//                        }
//
//                        is LceState.Error -> {
//                            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
//                        }
//                        is LceState.Loading -> {
//                            //todo
//                        }
//                    }
//                }.launchIn(viewLifecycleOwner.lifecycleScope)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}