package com.example.hm7_cleanarchitecture.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hm7_cleanarchitecture.data.model.PersonEntity

//@Dao
//internal interface FavouritesDao {
//
//    @Query("SELECT * FROM personentity  LIMIT :limit")
//    suspend fun getFavourites(limit: Int): List<PersonEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addFavourite(person: PersonEntity)

//    @Query("DELETE FROM FavouriteEntity")
//    suspend fun removeFavourite(person: FavouriteEntity)

//}