package com.example.hm7_cleanarchitecture.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hm7_cleanarchitecture.domain.model.LceState
import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.model.UIState
import com.example.hm7_cleanarchitecture.domain.usecase.GetPersonUseCase

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class ListViewModel(
    private val getPersonUseCase: GetPersonUseCase,
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 1
    private var hasMoreData = true
    private var isRefreshed = false
    private var list = listOf<Person>()

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
            getPersonUseCase(currentPage)
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
        if (!isLoading && hasMoreData) {
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

