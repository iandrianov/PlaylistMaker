package com.example.mymovies.presentation.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory


class PosterViewModel(private val posterUrl: String) : ViewModel() {

    companion object {
        fun getFactory(url: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PosterViewModel(url)
            }
        }
    }

    private val urlLiveData = MutableLiveData(posterUrl)
    fun observeUrl(): LiveData<String> = urlLiveData
}