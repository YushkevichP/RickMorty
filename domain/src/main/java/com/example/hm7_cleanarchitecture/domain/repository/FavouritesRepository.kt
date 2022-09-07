package com.example.hm7_cleanarchitecture.domain.repository

import com.example.hm7_cleanarchitecture.domain.model.Person

interface FavouritesRepository {

    suspend fun getFavourites() : List<Person>
    suspend fun addToFavourites(person: Person)
    suspend fun removeFavourites(person: Person)

}