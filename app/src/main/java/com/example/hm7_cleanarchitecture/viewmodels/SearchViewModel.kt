package com.example.hm7_cleanarchitecture.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hm7_cleanarchitecture.domain.usecase.GetPersonBySearсhUseCase
import kotlinx.coroutines.flow.*

class SearchViewModel(
    private val getPersonBySearсhUseCase: GetPersonBySearсhUseCase,
) : ViewModel() {
        //todo допилить т.к. из поиска они идут по страница (сделать счетчик)
    private var searchingQuery = ""
    private val searchQueryFlow = MutableStateFlow(searchingQuery)

    val dataSearchFlow = searchQueryFlow
        .debounce(1500)
        .flatMapLatest {

            getPersonBySearсhUseCase(searchingQuery)
        }
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    fun searcQueryChanger(newQuery: String) {
        if (newQuery != "") {
            searchingQuery = newQuery
        }
        if (searchQueryFlow.value != searchingQuery && newQuery != "") {
            searchQueryFlow.value = searchingQuery
        }
    }

    fun onRefresh() {
        searchQueryFlow.tryEmit("")
    }

}

