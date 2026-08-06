package com.example.playlistmaker.creator

import android.content.SharedPreferences
import com.example.playlistmaker.data.network.RetrofitClient
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.data.repository.MediaPlayerRepository
import com.example.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.domain.interactor.PlayerInteractor
import com.example.playlistmaker.domain.interactor.PlayerInteractorImpl
import com.example.playlistmaker.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.domain.interactor.SearchHistoryInteractorImpl
import com.example.playlistmaker.domain.interactor.TracksInteractor
import com.example.playlistmaker.domain.interactor.TracksInteractorImpl

object Creator {

    fun provideTracksInteractor(): TracksInteractor {

        val networkClient = RetrofitNetworkClient(
            RetrofitClient.musicApi
        )

        val repository = TracksRepositoryImpl(
            networkClient
        )

        return TracksInteractorImpl(
            repository
        )
    }

    fun provideSearchHistoryInteractor(
        sharedPreferences: SharedPreferences
    ): SearchHistoryInteractor {

        val repository =
            SearchHistoryRepositoryImpl(sharedPreferences)

        return SearchHistoryInteractorImpl(repository)
    }

    fun providePlayerInteractor(): PlayerInteractor {

        val repository = MediaPlayerRepository()

        return PlayerInteractorImpl(repository)
    }
}