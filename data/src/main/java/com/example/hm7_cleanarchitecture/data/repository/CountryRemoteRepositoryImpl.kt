package com.example.hm7_cleanarchitecture.data.repository

import com.example.hm7_cleanarchitecture.data.api.CountryApi
import com.example.hm7_cleanarchitecture.data.mapper.toDomainModel
import com.example.hm7_cleanarchitecture.domain.model.Country
import com.example.hm7_cleanarchitecture.domain.repository.CountryRemoteRepository

internal class CountryRemoteRepositoryImpl(
    private val countryApi: CountryApi,
) : CountryRemoteRepository {

    override suspend fun getCountries() =
        runCatching {
           countryApi.getCountries()
                .map { //it.toDomainModel()
                    Country(
                        name = it.name.common,
                        flag = it.flags.png,
                        latitude = it.latlng[0],
                        longitude = it.latlng[1],
                        // capital = it.capital.get(0),
                        population = it.population

                    )
                }
        }
}
