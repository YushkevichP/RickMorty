//package com.example.hm7_cleanarchitecture.viewmodels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.hm7_cleanarchitecture.domain.usecase.GetFavouriteUseCase
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.shareIn
//
//class FavouriteViewModel(
//    private val getFavouriteUseCase: GetFavouriteUseCase,
//) : ViewModel() {
//
//    suspend fun getDataFlow() = getFavouriteUseCase()
//        .shareIn(
//            scope = viewModelScope,
//            started = SharingStarted.Lazily,
//            replay = 1
//        )
//}