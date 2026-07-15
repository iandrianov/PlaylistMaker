package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.repository.TracksRepository

class TracksInteractorImpl(
    private val repository: TracksRepository
) : TracksInteractor {

    override fun searchTracks(
        expression: String,
        consumer: (List<Track>) -> Unit
    ) {
        repository.searchTracks(
            expression,
            consumer
        )
    }
}