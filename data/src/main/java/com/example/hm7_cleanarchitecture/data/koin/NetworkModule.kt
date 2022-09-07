package com.example.hm7_cleanarchitecture.data.koin

import com.example.hm7_cleanarchitecture.data.api.CountryApi
import com.example.hm7_cleanarchitecture.data.api.PersonApi
import com.example.hm7_cleanarchitecture.data.model.CountryResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

internal val networkModule = module {

    single {

        val httpLoginInterceptor = HttpLoggingInterceptor()
        httpLoginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(httpLoginInterceptor)
            .build()
    }


    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
    }

    single {
        get<Retrofit.Builder>()
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()
            .create<PersonApi>()
    }


    single {
        get<Retrofit.Builder>()
            .baseUrl("https://restcountries.com/v3.1/")
            .build()
            .create<CountryApi>()
    }
}


//internal val networkModule = module {
//    single {
//        OkHttpClient.Builder()
//            .readTimeout(20, TimeUnit.SECONDS)
//            .build()
//    }
//
//    //create retrofit Person
//    single(qualifier("RickMortyApi")) {
//        Retrofit.Builder()
//            .baseUrl("https://rickandmortyapi.com/api/")
//            .addConverterFactory(GsonConverterFactory.create()) // преобразует json объекты в наши оюъекты
//            .client(get())
//            .build()
//    }
//
//    //create api Person
//    single {
//        get<Retrofit>(qualifier("RickMortyApi")).create<PersonApi>()
//    }
//
//
//}


//single {
//    OkHttpClient.Builder()
//        .readTimeout(20, TimeUnit.SECONDS)
//        .build()
//}
//
////create retrofit Person
//single(qualifier("RickMortyApi")) {
//    Retrofit.Builder()
//        .baseUrl("https://rickandmortyapi.com/api/")
//        .addConverterFactory(GsonConverterFactory.create()) // преобразует json объекты в наши оюъекты
//        .client(get())
//        .build()
//}
//
////create api Person
//single {
//    get<Retrofit>(qualifier("RickMortyApi")).create<PersonApi>()
//}
//
//
////create retrofit Country
//single(qualifier("Countries")) {
//    Retrofit.Builder()
//        .baseUrl("https://restcountries.com/v3.1/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(get())
//        .build()
//}
//
////create api Country
//single {
//    get<Retrofit>(qualifier("Countries")).create<CountryResponse>()
//}
