package com.example.playlistmaker.data.dto

import com.example.playlistmaker.data.network.Response

class TracksSearchResponse(
    val resultCount: Int,
    val results: List<TrackDto>
) : Response()