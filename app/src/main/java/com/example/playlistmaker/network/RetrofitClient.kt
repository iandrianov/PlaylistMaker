package com.example.playlistmaker.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val MOVIE_BASE_URL = "https://tv-api.com/"
    private const val MUSIC_BASE_URL = "https://itunes.apple.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun createRetrofit(baseUrl:String): Retrofit {
       return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

    val movieApi: MovieApi by lazy {
        createRetrofit(MOVIE_BASE_URL).create(MovieApi::class.java)
    }

    val musicApi: MusicApi by lazy {
        createRetrofit(MUSIC_BASE_URL).create(MusicApi::class.java)
    }
}