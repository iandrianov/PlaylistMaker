package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.model.PlayerState

interface PlayerInteractor {

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