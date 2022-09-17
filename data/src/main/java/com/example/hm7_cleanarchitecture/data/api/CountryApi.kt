package com.example.hm7_cleanarchitecture.data.api

import com.example.hm7_cleanarchitecture.data.model.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface CountryApi {

        //получаем список всех стран


        @GET("all")
        suspend fun getCountries(): List<CountryResponse>

        @GET("name/{name}")
        suspend fun getCountryByName(
                @Path("name") name: String
        //тут поменял на список
        ): CountryResponse

}