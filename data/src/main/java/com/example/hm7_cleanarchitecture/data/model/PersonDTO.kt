package com.example.hm7_cleanarchitecture.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

internal data class Response(
    @SerializedName("results")
    val results: List<PersonDTO>,
)

// серверная сущность
internal data class PersonDTO(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val namePerson: String,

    @SerializedName("image")
    val imageUrl: String,

    @SerializedName("page")
    val page: Int
)

internal data class ResponseFromFilter(
    @SerializedName("results")
    val results: List<PersonDTOFromFilter>
)

// серверная сущность
internal data class PersonDTOFromFilter(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val namePerson: String,

    @SerializedName("image")
    val imageUrl: String,

)
