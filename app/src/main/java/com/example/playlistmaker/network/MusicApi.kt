package com.example.playlistmaker.network

import com.example.playlistmaker.model.TracksSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET("search?entity=song")
    fun searchTracks(
        @Query("term") text: String
    ): retrofit2.Call<TracksSearchResponse>
}