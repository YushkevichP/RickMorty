package com.example.hm7_cleanarchitecture.domain.usecase

import com.example.hm7_cleanarchitecture.domain.model.LceState
import com.example.hm7_cleanarchitecture.domain.model.PersonDetails
import com.example.hm7_cleanarchitecture.domain.repository.PersonRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetPersonDetailsUseCase(
    private val remoteRepository: PersonRemoteRepository,

    ) {

    suspend operator fun invoke(id: Int): Flow<LceState<PersonDetails>> =
        flow {

            // delay(1000)
            remoteRepository.getPersonDetails(id = id)
                .fold(
                    onSuccess = {
                        emit(LceState.Content(it))
                    },
                    onFailure = {

                        emit(LceState.Error(it))
                    }
                )
        }
}