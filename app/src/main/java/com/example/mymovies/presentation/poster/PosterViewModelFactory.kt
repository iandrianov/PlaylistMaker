package com.example.mymovies.presentation.poster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PosterViewModelFactory(
    private val posterUrl: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PosterViewModel(posterUrl) as T
    }
}