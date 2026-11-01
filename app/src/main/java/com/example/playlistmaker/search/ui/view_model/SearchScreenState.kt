package com.example.playlistmaker.search.ui.view_model

import com.example.playlistmaker.domain.model.Track

data class SearchScreenState(
    val searchResultState: SearchResultState = SearchResultState.Idle,
    val searchHistory: List<Track> = emptyList()
)

sealed interface SearchResultState {
    data object Idle : SearchResultState
    data object Loading : SearchResultState
    data class Content(val tracks: List<Track>) : SearchResultState
    data object Empty : SearchResultState
    data object Error : SearchResultState
}