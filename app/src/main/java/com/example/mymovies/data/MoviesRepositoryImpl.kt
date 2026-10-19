package com.example.mymovies.data

import com.example.mymovies.data.dto.MoviesSearchRequest
import com.example.mymovies.data.dto.MoviesSearchResponse
import com.example.mymovies.domain.api.MoviesRepository
import com.example.mymovies.domain.models.Movie
import com.example.mymovies.utils.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)})
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}