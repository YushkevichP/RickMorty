package com.example.hm7_cleanarchitecture.data.koin

import com.example.hm7_cleanarchitecture.domain.usecase.*

import org.koin.core.module.dsl.factoryOf

import org.koin.dsl.module

internal val useCaseModule = module {
    factory { GetPersonUseCase(get(), get()) }
    factory { GetCountryUseCase(get()) }
    factory { GetPersonDetailsUseCase(get()) }
    factory { GetPersonBySearсhUseCase(get()) }

}
