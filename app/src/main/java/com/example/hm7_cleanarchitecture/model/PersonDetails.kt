package com.example.hm7_cleanarchitecture.model

import com.google.gson.annotations.SerializedName

data class PersonDetails(

    val id: Int,
    val name: String,
    @SerializedName("image")
    val avatarApiDetails: String,
    // look at the api
    val gender: String,
    val status: String,
)
