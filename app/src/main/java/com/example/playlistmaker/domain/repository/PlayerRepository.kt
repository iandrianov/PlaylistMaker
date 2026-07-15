package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.model.PlayerState

interface PlayerRepository {

    fun prepare(
        url: String,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit
    )

    fun start()

    fun pause()

    fun release()

    fun currentPosition(): Int

    fun getPlayerState(): PlayerState
}