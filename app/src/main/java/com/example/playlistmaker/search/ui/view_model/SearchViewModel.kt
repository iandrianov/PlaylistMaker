package com.example.playlistmaker.search.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.search.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.interactor.TracksInteractor

class SearchViewModel(
    private val tracksInteractor: TracksInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : ViewModel() {

    private var lastSearchQuery = ""

    private val screenState = MutableLiveData(SearchScreenState())
    val screenStateLiveData: LiveData<SearchScreenState> = screenState

    fun loadSearchHistory() {
        updateScreenState(
            searchHistory = searchHistoryInteractor.getTracks()
        )
    }

    fun clearSearchHistory() {
        searchHistoryInteractor.clearHistory()
        updateScreenState(searchHistory = emptyList())
    }

    fun addTrackToHistory(track: Track) {
        searchHistoryInteractor.addTrack(track)
        loadSearchHistory()
    }

    fun searchTracks(expression: String) {
        if (expression.isBlank()) return

        lastSearchQuery = expression

        updateScreenState(
            searchResultState = SearchResultState.Loading
        )

        tracksInteractor.searchTracks(expression) { foundTracks, resultCode ->
            when {
                resultCode != 200 -> {
                    updateScreenState(
                        searchResultState = SearchResultState.Error
                    )
                }

                foundTracks.isEmpty() -> {
                    updateScreenState(
                        searchResultState = SearchResultState.Empty
                    )
                }

                else -> {
                    updateScreenState(
                        searchResultState = SearchResultState.Content(foundTracks)
                    )
                }
            }
        }
    }

    fun retrySearch() {
        if (lastSearchQuery.isNotEmpty()) {
            searchTracks(lastSearchQuery)
        }
    }

    fun clearSearchResults() {
        lastSearchQuery = ""
        updateScreenState(
            searchResultState = SearchResultState.Idle
        )
    }

    private fun updateScreenState(
        searchResultState: SearchResultState? = null,
        searchHistory: List<Track>? = null
    ) {
        val currentState = screenState.value ?: SearchScreenState()

        screenState.value = currentState.copy(
            searchResultState = searchResultState ?: currentState.searchResultState,
            searchHistory = searchHistory ?: currentState.searchHistory
        )
    }

    companion object {
        fun getFactory(
            tracksInteractor: TracksInteractor,
            searchHistoryInteractor: SearchHistoryInteractor
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    tracksInteractor = tracksInteractor,
                    searchHistoryInteractor = searchHistoryInteractor
                )
            }
        }
    }
}