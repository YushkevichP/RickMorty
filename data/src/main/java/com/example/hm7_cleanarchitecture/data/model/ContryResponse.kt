package com.example.hm7_cleanarchitecture.data.model

import com.google.gson.annotations.SerializedName


//todo можно заюзать флаг/столицу/ население(?)
data class CountryResponse(

    @SerializedName("latlng")
    val latlng: List<Double>,

    @SerializedName("name")
    val name: Name,

    @SerializedName("population")
    val population: Int,

    @SerializedName("flags")
    val flags: Flags,

    )

data class CapitalInfo(
    @SerializedName("latlng")
    val latlng: List<Double>,
)

data class Flags(
    @SerializedName("png")
    val png: String,
    @SerializedName("svg")
    val svg: String,
)


data class Name(
    @SerializedName("common")
    val common: String,
    @SerializedName("official")
    val official: String,
)


// todo добавить если поулчится в инфо по клику

//    @SerializedName("capital")
//    val capital: List<String>,
//
//    @SerializedName("capitalInfo")
//    val capitalInfo: CapitalInfo,










