package com.example.hm7_cleanarchitecture.data.koin



import org.koin.dsl.module

val dataModule = module {
    includes(
        networkModule,
        databaseModule,
        repositoryModule,
        useCaseModule,
        serviceModule,

    )
}