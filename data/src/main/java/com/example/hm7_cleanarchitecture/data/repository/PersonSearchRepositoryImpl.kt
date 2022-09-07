package com.example.hm7_cleanarchitecture.data.repository


import com.example.hm7_cleanarchitecture.data.api.PersonApi
import com.example.hm7_cleanarchitecture.data.mapper.toDomainModel
import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.repository.PersonSearchRepository

internal class PersonSearchRepositoryImpl(
    private val searchApi: PersonApi,
) : PersonSearchRepository {

    override suspend fun getPersonBySearch(name: String) =
        runCatching {
            searchApi.getPersonBySearch(name)
                .results
                .map {
                    Person(
                        name = it.namePerson,
                        id = it.id,
                        imageUrl = it.imageUrl,
                        page = 1
                    )

                }
        }

}
