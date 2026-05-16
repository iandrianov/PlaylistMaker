package com.example.playlistmaker.model


data class TracksSearchResponse(
    val resultCount: Int,
    val results: List<com.example.playlistmaker.model.Track>,
)
