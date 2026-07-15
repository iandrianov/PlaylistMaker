package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.model.Track

interface TracksRepository {

    fun searchTracks(
        expression: String,
        consumer: (List<Track>, Int) -> Unit
    )
}