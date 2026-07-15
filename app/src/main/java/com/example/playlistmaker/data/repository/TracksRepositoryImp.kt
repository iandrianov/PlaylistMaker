package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.dto.TracksSearchResponse
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.TracksSearchRequest
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.repository.TracksRepository

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    override fun searchTracks(
        expression: String,
        consumer: (List<Track>) -> Unit
    ) {

        networkClient.doRequest(
            TracksSearchRequest(expression)
        ) { response ->

            if (response is TracksSearchResponse) {

                val tracks = response.results.map { dto ->

                    Track(
                        trackId = dto.trackId,
                        trackName = dto.trackName,
                        artistName = dto.artistName,
                        trackTimeMillis = dto.trackTimeMillis,
                        artworkUrl100 = dto.artworkUrl100,
                        collectionName = dto.collectionName,
                        releaseDate = dto.releaseDate,
                        primaryGenreName = dto.primaryGenreName,
                        country = dto.country,
                        previewUrl = dto.previewUrl

                    )
                }

                consumer(tracks)
            }
        }
    }
}