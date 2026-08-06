package com.example.playlistmaker.data.repository

import android.media.MediaPlayer
import com.example.playlistmaker.domain.repository.PlayerRepository
import com.example.playlistmaker.domain.model.PlayerState

class MediaPlayerRepository : PlayerRepository {
    private val mediaPlayer = MediaPlayer()
    private var playerState = PlayerState.DEFAULT

    override fun prepare(
        url: String,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit
    ) {
        mediaPlayer.setDataSource(url)

        mediaPlayer.setOnPreparedListener {
            playerState = PlayerState.PREPARED
            onPrepared()
        }

        mediaPlayer.setOnCompletionListener {
            playerState = PlayerState.PREPARED
            onCompletion()
        }

        mediaPlayer.prepareAsync()
    }

    override fun start(){
        mediaPlayer.start()
        playerState = PlayerState.PLAYING
    }

    override fun pause(){
        mediaPlayer.pause()
        playerState = PlayerState.PAUSED
    }

    override fun currentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun release(){
        mediaPlayer.release()
    }

    override fun getPlayerState(): PlayerState {
        return playerState
    }

}