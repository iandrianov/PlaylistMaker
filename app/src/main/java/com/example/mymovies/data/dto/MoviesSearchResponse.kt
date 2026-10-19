package com.example.mymovies.data.dto

import com.example.mymovies.domain.models.Movie

data class MoviesSearchResponse(
    val searchType: String,
    val valexpression: String,
    val results: List<Movie>,
    val errorMessage: String
) : Response()