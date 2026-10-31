package com.example.playlistmaker.search.ui.view_model

import com.example.playlistmaker.domain.model.Track

sealed interface SearchScreenState {
    data object Loading: SearchScreenState
    data class Content(val tracks: List<Track>): SearchScreenState
    data object Empty: SearchScreenState
    data object Error: SearchScreenState
}