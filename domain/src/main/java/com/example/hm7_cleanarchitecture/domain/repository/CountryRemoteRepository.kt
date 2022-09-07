package com.example.hm7_cleanarchitecture.domain.repository

import com.example.hm7_cleanarchitecture.domain.model.Country

interface CountryRemoteRepository {

    suspend fun getCountries(): Result<List<Country>>
}