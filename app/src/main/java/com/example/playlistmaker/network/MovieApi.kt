package com.example.playlistmaker.network


import com.example.playlistmaker.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun searchMovies(
        @Path("expression") expression: String
    ): Call<MovieResponse>
}