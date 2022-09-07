package com.example.hm7_cleanarchitecture.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hm7_cleanarchitecture.domain.model.LceState
import com.example.hm7_cleanarchitecture.domain.model.PersonDetails
import com.example.hm7_cleanarchitecture.domain.repository.PersonRemoteRepository
import com.example.hm7_cleanarchitecture.domain.usecase.GetPersonDetailsUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class PersonDetailsViewModel(
    private val id: Int,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
) : ViewModel() {

    suspend fun getdataFlow() = getPersonDetailsUseCase(id)
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )
}

