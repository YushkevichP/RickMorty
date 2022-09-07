package com.example.hm7_cleanarchitecture.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.hm7_cleanarchitecture.data.service.LocationService
import com.example.hm7_cleanarchitecture.domain.usecase.GetCountryUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*


//todo траблы с юзкейсом

class CountryViewModel(

    private val locationService: LocationService,
    private val getCountryUseCase: GetCountryUseCase,
) : ViewModel() {

    fun show() {
        Log.d("TAG", "заходит")
    }

    private val locationFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val countriesFlow = MutableStateFlow(Unit)

    @SuppressLint("MissingPermission")
    fun getLocationFlow() = locationService.getLocationFlow()

    @SuppressLint("MissingPermission")
    val currentLocationFlow = locationFlow
        .mapLatest {
            locationService.getCurrentLocation()
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    val getcountriesFlow = countriesFlow
        .flatMapLatest {
            getCountryUseCase()
        }
        .shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.Lazily
        )

    fun loadStartLocation() {
        locationFlow.tryEmit(Unit)
    }

//    init {
//        loadStartLocation()
//    }

}
