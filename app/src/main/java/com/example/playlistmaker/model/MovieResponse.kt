package com.example.playlistmaker.model

data class MovieResponse(
    val searchType: String,
    val valexpression: String,
    val results: List<Movie>,
    val errorMessage: String
)