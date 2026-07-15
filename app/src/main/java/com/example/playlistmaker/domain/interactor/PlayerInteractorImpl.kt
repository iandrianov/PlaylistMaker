package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.repository.PlayerRepository
import com.example.playlistmaker.domain.model.PlayerState

class PlayerInteractorImpl(
    private val repository: PlayerRepository
) : PlayerInteractor {

    override fun prepare(
        url: String,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit
    ) {
        repository.prepare(
            url,
            onPrepared,
            onCompletion
        )
    }

    override fun start() {
        repository.start()
    }

    override fun pause() {
        repository.pause()
    }

    override fun release() {
        repository.release()
    }

    override fun currentPosition(): Int {
        return repository.currentPosition()
    }

    override fun getPlayerState(): PlayerState {
        return repository.getPlayerState()
    }
}