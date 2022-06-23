package com.example.hm7_cleanarchitecture.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hm7_cleanarchitecture.domain.model.LceState
import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.model.UIState
import com.example.hm7_cleanarchitecture.domain.usecase.GetPersonBySearсhUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class SearchViewModel(
    private val getPersonBySearсhUseCase: GetPersonBySearсhUseCase
) : ViewModel() {
    private var isLoading = false
    private var currentPage = 1
    private var hasMoreData = true
    private var isRefreshed = false
    private var list = listOf<Person>()
    private var name = 3

    private val loadMoreFlow = MutableSharedFlow<LoadState>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow = loadMoreFlow
        .filter { !isLoading }
        .onEach {
            isLoading = true
            if (it == LoadState.REFRESH) {
                isRefreshed = true
                list = emptyList()
                currentPage = 1
            } else isRefreshed = false
        }
        .flatMapLatest {
            getPersonBySearсhUseCase(name)
        }//тоже что и ранинг редьюс но с начальным значением
        .runningFold(UIState()) { state, lce ->
            when (lce) {
                is LceState.Content -> {
                    list = list + lce.data
                    currentPage++
                    state.copy(persons = list, hasMoreData = lce.hasMoreData, throwable = null)
                }

                is LceState.Error -> {
                    state.copy(throwable = lce.throwable)
                }

                is LceState.Loading -> { // кэш
                    state.copy(persons = lce.data)
                }
            }
        }.onEach {
            isLoading = false
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

    init {
        onLoadMore()
    }

    fun onLoadMore() {
        if (!isLoading) {
            loadMoreFlow.tryEmit(LoadState.LOAD_MORE)
        }
    }

    fun onRefresh() {
        loadMoreFlow.tryEmit(LoadState.REFRESH)
    }

    enum class LoadState {
        LOAD_MORE, REFRESH
    }

}


//class SearchViewModel(
//    private val getPersonBySearсhUseCase: GetPersonBySearсhUseCase
//) : ViewModel() {
//
//    private var query = "Rick"
//    private var currentPage = 1
//
//    private val searchQueryFlow = MutableSharedFlow<Unit>(
//        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
//    )
//    val searchFlow = searchQueryFlow
//        .debounce(1000)
//        .flatMapLatest  {
//            getPersonBySearсhUseCase(query)
//        }
//        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
//
////    fun onQueryChanged(newQuery: String){
////        if (newQuery !=""){
////            query = newQuery
////        }
////        if (searchQueryFlow.value != query && newQuery != ""){
////            searchQueryFlow.value = query
////        }
////    }
//
//    fun onLoadMore() {
//        searchQueryFlow.tryEmit(Unit)
//    }
//
//    init {
//        onLoadMore()
//    }
//}