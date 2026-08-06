package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.model.Track

interface TracksInteractor {

    fun searchTracks(
        expression: String,
        consumer: (List<Track>, Int) -> Unit
    )
}