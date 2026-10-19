package com.example.mymovies.domain.impl

import com.example.mymovies.domain.api.SearchHistoryInteractor
import com.example.mymovies.domain.api.SearchHistoryRepository
import com.example.mymovies.domain.models.Movie

class SearchHistoryInteractorImpl(
    private val repository: SearchHistoryRepository
) : SearchHistoryInteractor {

    override fun getHistory(consumer: SearchHistoryInteractor.HistoryConsumer) {
        consumer.consume(repository.getHistory().data)
    }

    override fun saveToHistory(m: Movie) {
        repository.saveToHistory(m)
    }
}