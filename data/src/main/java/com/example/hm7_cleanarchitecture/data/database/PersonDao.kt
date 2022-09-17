package com.example.hm7_cleanarchitecture.data.database

import androidx.room.*
import com.example.hm7_cleanarchitecture.data.model.PersonDTO
import com.example.hm7_cleanarchitecture.data.model.PersonEntity
import com.example.hm7_cleanarchitecture.domain.model.Person


@Dao
internal interface PersonDao {

    @Query("SELECT * FROM personentity WHERE (:page) LIKE page LIMIT :limit")
    suspend fun getSomePersons(limit: Int, page: Int): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(list: List<PersonEntity>)

//    //todo допилить добавление в закладки
//    @Query("SELECT * FROM personentity WHERE (:isFavourite) LIKE isFavourite")
//    suspend fun getFavourite(isFavourite: Boolean = true): List<PersonEntity>
//
//    //should make isFavourite - true
//    @Update
//    fun addToFavourite(person: PersonEntity)
//
//
//    //should make isFavourite - false
//    @Update
//    fun removeFromFavourite(person: PersonEntity)
}