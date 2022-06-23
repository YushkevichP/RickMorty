package com.example.hm7_cleanarchitecture.data.mapper

import com.example.hm7_cleanarchitecture.data.model.CountryResponse
import com.example.hm7_cleanarchitecture.domain.model.Country

internal fun CountryResponse.toDomainModel(): Country {
    return Country(
        name = name.common,
        population = population,
        flag = flags.svg,
        latitude = latlng.get(0),
        longitude = latlng.get(1),
        //capital = capital.get(0),
        )
}

internal fun List<CountryResponse>.toDomainModel(): List<Country> {
    return map {
        it.toDomainModel()
    }
}