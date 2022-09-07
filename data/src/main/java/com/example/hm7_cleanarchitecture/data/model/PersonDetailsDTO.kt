package com.example.hm7_cleanarchitecture.data.model

import com.google.gson.annotations.SerializedName

internal data class PersonDetailsDTO(

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val avatarApiDetails: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("status")
    val status: String,
)