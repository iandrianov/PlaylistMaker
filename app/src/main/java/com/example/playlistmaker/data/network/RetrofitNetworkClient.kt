package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.dto.TracksSearchResponse
import retrofit2.Call
import retrofit2.Callback

class RetrofitNetworkClient(
    private val musicApi: MusicApi
) : NetworkClient {

    override fun doRequest(
        dto: Any,
        callback: (Response) -> Unit
    ) {

        if (dto !is TracksSearchRequest) return

        musicApi.searchTracks(dto.expression)
            .enqueue(object : Callback<TracksSearchResponse> {

                override fun onResponse(
                    call: Call<TracksSearchResponse>,
                    response: retrofit2.Response<TracksSearchResponse>
                ) {

                    if (response.isSuccessful && response.body() != null) {

                        val searchResponse = response.body()!!
                        searchResponse.resultCode = 200

                        callback(searchResponse)

                    } else {

                        callback(Response().apply {
                            resultCode = response.code()
                        })
                    }
                }

                override fun onFailure(
                    call: Call<TracksSearchResponse>,
                    t: Throwable
                ) {

                    callback(Response().apply {
                        resultCode = -1
                    })
                }
            })
    }
}