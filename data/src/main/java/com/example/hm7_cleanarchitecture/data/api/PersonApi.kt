package com.example.hm7_cleanarchitecture.data.api


import com.example.hm7_cleanarchitecture.data.model.PersonDetailsDTO
import com.example.hm7_cleanarchitecture.data.model.Response
import com.example.hm7_cleanarchitecture.data.model.ResponseFromFilter

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PersonApi {

    @GET("character")
    suspend fun getUsersFromApi(
        @Query("page") page: Int,
    ): Response

    @GET("character/{id}")
    suspend fun getPersonDetailsFromApi(
        @Path("id") id: Int,
    ): PersonDetailsDTO




    @GET("character/?name={name}")
    suspend fun getPersonBySearch(
        @Path("name") name: String,
    ) : ResponseFromFilter

//    @GET("character/?name={name}&status={status}&gender={gender}")
//    suspend fun getPersonBySearch(
//        @Path("name") name: String,
//        @Path("status") status: String,
//        @Path("gender") gender: String,
//    ) : Response

}