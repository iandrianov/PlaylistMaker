package com.example.mymovies.data.storage

import com.example.mymovies.data.StorageClient
import com.example.mymovies.domain.api.SearchHistoryRepository
import com.example.mymovies.domain.models.Movie
import com.example.mymovies.utils.Resource

class SearchHistoryRepositoryImpl(
    private val storage: StorageClient<ArrayList<Movie>>
): SearchHistoryRepository {

    override fun saveToHistory(m: Movie) {
        val movies = storage.getData() ?: arrayListOf()
        movies.add(m)
        storage.storeData(movies)
    }

    override fun getHistory(): Resource<List<Movie>> {
        val movies = storage.getData() ?: listOf()
        return Resource.Success(movies)
    }
}