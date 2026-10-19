package com.example.mymovies.domain.api

import com.example.mymovies.domain.models.Movie
import com.example.mymovies.utils.Resource

interface SearchHistoryRepository {
    fun saveToHistory(m: Movie)
    fun getHistory(): Resource<List<Movie>>
}