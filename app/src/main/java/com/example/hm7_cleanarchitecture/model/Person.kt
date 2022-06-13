package com.example.hm7_cleanarchitecture.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Person(

    @PrimaryKey
    @SerializedName("id")
    val idApi: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val nameApi: String,

    @ColumnInfo(name = "image")
    @SerializedName("image")// используется, чтоб переписать название из json в наше название.
    val imageApi: String,

    @ColumnInfo(name = "page")
    val page: Int,
)