package com.example.playlistmaker.data.network

interface NetworkClient {
    fun doRequest(
        dto: Any,
        callback: (Response) -> Unit
    )
}
