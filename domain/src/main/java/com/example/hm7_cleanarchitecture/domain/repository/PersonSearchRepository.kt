package com.example.hm7_cleanarchitecture.domain.repository

import com.example.hm7_cleanarchitecture.domain.model.Person

interface PersonSearchRepository {
    suspend fun getPersonBySearch(name: String): Result<List<Person>>
}