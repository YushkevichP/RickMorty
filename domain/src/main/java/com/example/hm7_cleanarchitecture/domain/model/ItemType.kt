package com.example.hm7_cleanarchitecture.domain.model


sealed class ItemType<out T> {

    data class Content<T>(val data: T) : ItemType<T>()

    object Loading : ItemType<Nothing>()

    data class Error(val throwable: Throwable) : ItemType<Nothing>()
}

