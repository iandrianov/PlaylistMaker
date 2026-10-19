package com.example.mymovies.data

interface StorageClient<T> {
    fun storeData(data: T)
    fun getData(): T?
}