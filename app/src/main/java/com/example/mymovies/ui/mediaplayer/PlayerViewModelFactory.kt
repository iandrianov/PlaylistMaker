package com.example.mymovies.ui.mediaplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.presentation.movies.MoviesViewModel

class PlayerViewModelFactory(private val url: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            return PlayerViewModel(url) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}