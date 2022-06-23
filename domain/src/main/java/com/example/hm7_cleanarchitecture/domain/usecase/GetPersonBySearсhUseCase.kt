package com.example.hm7_cleanarchitecture.domain.usecase

import com.example.hm7_cleanarchitecture.domain.model.LceState
import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.repository.PersonLocalRepository
import com.example.hm7_cleanarchitecture.domain.repository.PersonRemoteRepository
import com.example.hm7_cleanarchitecture.domain.repository.PersonSearchRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPersonBySearсhUseCase(

    private val searchRepository: PersonSearchRepository,
) {

    suspend operator fun invoke(name: Int): Flow<LceState<List<Person>>> =
        flow {

            searchRepository.getPersonBySearch(name = name)
                .fold(
                    onSuccess = { list ->
                        emit(LceState.Content(list))
                    },
                    onFailure = {
                        emit(LceState.Error(it))
                    }
                )
        }


}



//class GetPersonBySearсhUseCase(
//    private val searchRepository: PersonSearchRepository,
//) {
//    suspend operator fun invoke(name: String): Flow<List<Person>> =
//        flow {
//            searchRepository.getPersonBySearch(name)
//                .fold(
//                    onSuccess = { list ->
//                        emit(list)
//                    },
//                    onFailure = {
//                        emit(emptyList())
//                    }
//                )
//        }
//}


