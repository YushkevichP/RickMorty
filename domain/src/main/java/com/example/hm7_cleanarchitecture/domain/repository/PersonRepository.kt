package com.example.hm7_cleanarchitecture.domain.repository

import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.model.PersonDetails

// здесь вся логика взаимодействия с системой.
interface PersonRepository {

    suspend fun getPerson(page: Int): Result<List<Person>>

    suspend fun getPersonDetails(id: Int): Result<PersonDetails>



//  suspend fun getPersonsFromDB(limit: Int, offset: Int, page: Int)
//  suspend fun insertPersons(list: List<Person>)

}