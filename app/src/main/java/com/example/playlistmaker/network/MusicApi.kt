package com.example.playlistmaker.network

import com.example.playlistmaker.model.TracksSearchResponse
import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET("search")
    fun searchTracks(
        @Query("term") query: String
    ): retrofit2.Call<TracksSearchResponse>
}