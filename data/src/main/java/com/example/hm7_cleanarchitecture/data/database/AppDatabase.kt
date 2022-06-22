package com.example.hm7_cleanarchitecture.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hm7_cleanarchitecture.data.model.PersonEntity


@Database(entities = [PersonEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
//    abstract fun favouriteDao(): FavouritesDao
}