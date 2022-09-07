package com.example.hm7_cleanarchitecture.data.koin

import androidx.room.Room
import com.example.hm7_cleanarchitecture.data.database.AppDatabase
import org.koin.dsl.module

internal val databaseModule = module {

    single {
        Room.databaseBuilder(
            get(), //дает нам уже готовый из койна
            AppDatabase::class.java,
            "app_database"
        )
            .build()
    }

    single {
        get<AppDatabase>().personDao()
    }

}