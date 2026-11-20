package com.example.playlistmaker.search.domain.interactor

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.search.domain.repository.TracksRepository

class TracksInteractorImpl(
    private val repository: TracksRepository
) : TracksInteractor {

    override fun searchTracks(
        expression: String,
        consumer: (List<Track>, Int) -> Unit
    ) {
        repository.searchTracks(
            expression,
            consumer
        )
    }
}