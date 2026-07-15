package com.example.playlistmaker.data.repository

import android.content.SharedPreferences
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.repository.SearchHistoryRepository
import com.google.gson.Gson


private const val SEARCH_HISTORY_KEY = "search_history"
private const val MAX_HISTORY_SIZE = 10

class SearchHistoryRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SearchHistoryRepository {
    private val gson = Gson()

    override fun getTracks(): List<Track> {
        val json = sharedPreferences.getString(SEARCH_HISTORY_KEY, null)
        return Gson().fromJson(json, Array<Track>::class.java)?.toList()?: emptyList()
    }

    override fun clearHistory() {
        sharedPreferences.edit()
            .remove(SEARCH_HISTORY_KEY)
            .apply()
    }

    override fun addTrack(track: Track) {
        val tracks = getTracks()?.toMutableList()
        tracks?.removeIf { it.trackId == track.trackId }

        tracks?.add(0, track)

        tracks?.size?.let {
            if (it > MAX_HISTORY_SIZE){
                tracks.removeAt(tracks.size-1)
            }
        }

        saveTracks(tracks?.toList() ?: emptyList())
    }

    private fun saveTracks(tracks:List<Track>){
        val json =  gson.toJson(tracks)
        sharedPreferences.edit().putString(SEARCH_HISTORY_KEY,json).apply()
    }
}