package com.example.hm7_cleanarchitecture.data.repository

import com.example.hm7_cleanarchitecture.data.api.PersonApi
import com.example.hm7_cleanarchitecture.data.mapper.toDomainModel
import com.example.hm7_cleanarchitecture.data.mapper.toDomainModels
import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.model.PersonDetails
import com.example.hm7_cleanarchitecture.domain.repository.PersonRemoteRepository
import kotlinx.coroutines.delay

internal class PersonRemoteRepositoryImpl(
    private val personApi: PersonApi,
) : PersonRemoteRepository {

    override suspend fun getPerson(page: Int): Result<List<Person>> {

        return runCatching {
         //   delay(2000)
            personApi.getUsersFromApi(page)
                .results
                .map {personDTO ->
                    personDTO.toDomainModel()
                }
        }
    }

    override suspend fun getPersonDetails(id: Int): Result<PersonDetails> {
        return runCatching {
            personApi.getPersonDetailsFromApi(id)
                .toDomainModel()
        }
    }
}
