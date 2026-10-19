package com.example.mymovies.data.network

import com.example.mymovies.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {

    @GET("/en/API/SearchMovie/k_j4r66gt6/{expression}")
    fun searchMovies(
        @Path("expression") expression: String
    ): Call<MoviesSearchResponse>
}