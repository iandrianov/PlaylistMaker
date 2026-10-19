package com.example.mymovies.utils

import android.app.Activity
import android.content.Context
import com.example.mymovies.data.MoviesRepositoryImpl
import com.example.mymovies.data.network.RetrofitNetworkClient
import com.example.mymovies.data.storage.PrefsStorageClient
import com.example.mymovies.data.storage.SearchHistoryRepositoryImpl
import com.example.mymovies.domain.api.MoviesInteractor
import com.example.mymovies.domain.api.MoviesRepository
import com.example.mymovies.domain.api.SearchHistoryInteractor
import com.example.mymovies.domain.api.SearchHistoryRepository
import com.example.mymovies.domain.impl.MoviesInteractorImpl
import com.example.mymovies.domain.impl.SearchHistoryInteractorImpl
import com.example.mymovies.domain.models.Movie
import com.google.gson.reflect.TypeToken

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    private fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(
            PrefsStorageClient<ArrayList<Movie>>(
                context,
                "HISTORY",
                object : TypeToken<ArrayList<Movie>>() {}.type
            )
        )
    }

    fun provideSearchHistoryInteractor(context: Context): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(getSearchHistoryRepository(context))
    }
}