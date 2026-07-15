package com.example.playlistmaker.domain.repository


import com.example.playlistmaker.domain.model.Track

interface SearchHistoryRepository {

    fun getTracks(): List<Track>

    fun clearHistory()

    fun addTrack(track: Track)
}