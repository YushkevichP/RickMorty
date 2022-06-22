package com.example.hm7_cleanarchitecture.data.repository


import com.example.hm7_cleanarchitecture.data.database.PersonDao
import com.example.hm7_cleanarchitecture.data.mapper.toDomainModel
import com.example.hm7_cleanarchitecture.data.mapper.toPersonEntity
import com.example.hm7_cleanarchitecture.domain.model.Person
import com.example.hm7_cleanarchitecture.domain.repository.FavouritesRepository

internal class FavouritesRepositoryImpl(
    private val personDao: PersonDao,
) : FavouritesRepository {

    override suspend fun getFavourites(): List<Person> {
        return personDao.getFavourite().map {
            it.toDomainModel()
        }
    }

    override suspend fun addToFavourites(person: Person) {
        personDao.addToFavourite(person.toPersonEntity())
    }

    override suspend fun removeFavourites(person: Person) {
        personDao.removeFromFavourite(person.toPersonEntity())
    }


}

