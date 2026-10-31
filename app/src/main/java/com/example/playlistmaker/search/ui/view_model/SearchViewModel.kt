
package com.example.playlistmaker.search.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.search.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.interactor.TracksInteractor
import com.example.playlistmaker.domain.model.Track

class SearchViewModel(
    private val tracksInteractor: TracksInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : ViewModel() {

    private val screenState = MutableLiveData<SearchScreenState>()
    val screenStateLiveData: LiveData<SearchScreenState> = screenState

    private val searchHistory = MutableLiveData<List<Track>>()
    val searchHistoryLiveData: LiveData<List<Track>> = searchHistory

    fun loadSearchHistory() {
        searchHistory.value = searchHistoryInteractor.getTracks()
    }

    fun clearSearchHistory() {
        searchHistoryInteractor.clearHistory()
        searchHistory.value = emptyList()
    }

    fun addTrackToHistory(track: Track) {
        searchHistoryInteractor.addTrack(track)
        loadSearchHistory()
    }

    fun searchTracks(expression: String) {
        if (expression.isBlank()) return

        screenState.value = SearchScreenState.Loading

        tracksInteractor.searchTracks(expression) { foundTracks, resultCode ->
            when {
                resultCode != 200 -> {
                    screenState.postValue(SearchScreenState.Error)
                }

                foundTracks.isEmpty() -> {
                    screenState.postValue(SearchScreenState.Empty)
                }

                else -> {
                    screenState.postValue(SearchScreenState.Content(foundTracks))
                }
            }
        }
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
