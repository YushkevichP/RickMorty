package com.example.hm7_cleanarchitecture.data.repository

import com.example.hm7_cleanarchitecture.data.database.PersonDao
import com.example.hm7_cleanarchitecture.data.mapper.toDomainModel
import com.example.hm7_cleanarchitecture.data.mapper.toPersonEntity
import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.repository.PersonLocalRepository


internal class PersonLocalRepositoryImpl(
    private val personDao: PersonDao,
) : PersonLocalRepository {

    override suspend fun getPersonsFromDB(
        limit: Int,
        page: Int,
    ): List<Person> {
        return personDao.getSomePersons(limit, page).map {
            it.toDomainModel()
        }
    }

    override suspend fun insertPersons(list: List<Person>, page: Int) {
        personDao.insertPersons(list.map { it.toPersonEntity(page) })
    }
}
