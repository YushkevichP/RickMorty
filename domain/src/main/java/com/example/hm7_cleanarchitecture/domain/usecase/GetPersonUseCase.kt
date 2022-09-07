package com.example.hm7_cleanarchitecture.domain.usecase

import com.example.hm7_cleanarchitecture.domain.model.LceState
import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.repository.PersonLocalRepository
import com.example.hm7_cleanarchitecture.domain.repository.PersonRemoteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class GetPersonUseCase(
    private val remoteRepository: PersonRemoteRepository,
    private val localRepository: PersonLocalRepository,
) {

   suspend operator fun invoke(page: Int): Flow<LceState<List<Person>>> =
        flow {
            //достаем кеш при первой подгрузке
            if(page == START_PAGE){
                val cache = localRepository.getPersonsFromDB(AMOUNT_LIMIT, page)
                emit(LceState.Loading(cache))
            }
            delay(1000)
            remoteRepository.getPerson(page)
                .fold(
                    onSuccess = { list ->
                        localRepository.insertPersons(list, page)
                        if (list.size < 20) {
                            emit(LceState.Content(list, hasMoreData = false))
                        } else {
                            emit(LceState.Content(list))
                        }
                    },
                    onFailure = {
                        emit(LceState.Error(it))
                    }
                )
        }

    companion object{
        private const val START_PAGE = 1
        private const val AMOUNT_LIMIT = 20
    }
}








