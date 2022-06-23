package com.example.hm7_cleanarchitecture.koin

import com.example.hm7_cleanarchitecture.viewmodels.ListViewModel
import com.example.hm7_cleanarchitecture.viewmodels.PersonDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.hm7_cleanarchitecture.viewmodels.CountryViewModel
import com.example.hm7_cleanarchitecture.viewmodels.SearchViewModel

val viewModelModule = module {

    viewModelOf(::ListViewModel)
    viewModel { (id: Int) ->
        PersonDetailsViewModel(id, get())
    }
    viewModelOf(::CountryViewModel)
    viewModelOf(::SearchViewModel)

}
