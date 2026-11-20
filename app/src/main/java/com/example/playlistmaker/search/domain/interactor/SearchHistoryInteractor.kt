package com.example.playlistmaker.search.domain.interactor

import com.example.playlistmaker.domain.model.Track

interface SearchHistoryInteractor {

    fun getTracks(): List<Track>

    fun clearHistory()

    fun addTrack(track: Track)
}