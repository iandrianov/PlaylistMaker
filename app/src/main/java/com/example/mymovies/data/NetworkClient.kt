package com.example.mymovies.data

import com.example.mymovies.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}