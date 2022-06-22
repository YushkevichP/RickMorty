package com.example.hm7_cleanarchitecture.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// сущность для БД
@Entity
internal data class PersonEntity(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image")
    val imageUrl: String,

    @ColumnInfo(name = "page")
    val page: Int,

    @ColumnInfo(name = "isFavourite")
    val isFavourite : Boolean = false
)
