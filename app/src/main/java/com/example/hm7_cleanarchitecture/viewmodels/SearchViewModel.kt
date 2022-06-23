package com.example.hm7_cleanarchitecture.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hm7_cleanarchitecture.domain.usecase.GetPersonBySearсhUseCase
import kotlinx.coroutines.flow.*

class SearchViewModel(
    private val getPersonBySearсhUseCase: GetPersonBySearсhUseCase
) : ViewModel() {

    private var searchingQuery = ""
    private var currentPage = 1
    private val searchQueryFlow = MutableStateFlow(searchingQuery)

    val dataSearchFlow = searchQueryFlow
        .debounce(1500)
        .flatMapLatest  {
            currentPage++
            getPersonBySearсhUseCase(searchingQuery)
        }
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    fun searcQueryChanger(newQuery: String){
        if (newQuery !=""){
            searchingQuery = newQuery
        }
        if (searchQueryFlow.value != searchingQuery && newQuery != ""){
            searchQueryFlow.value = searchingQuery
        }
    }

//    fun onLoadMore() {
//        searchQueryFlow.tryEmit(Unit)
//    }
}

