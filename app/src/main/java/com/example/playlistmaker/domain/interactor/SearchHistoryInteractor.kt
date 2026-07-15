package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.model.Track

interface SearchHistoryInteractor {

    fun getTracks(): List<Track>

    fun clearHistory()

    fun addTrack(track: Track)
}