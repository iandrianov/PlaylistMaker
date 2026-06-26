package com.example.playlistmaker.player

import android.media.MediaPlayer

class MediaPlayerRepository {
    private val mediaPlayer = MediaPlayer()
    private var playerState = PlayerState.DEFAULT

    fun prepare(
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

    fun start(){
        mediaPlayer.start()
        playerState = PlayerState.PLAYING
    }

    fun pause(){
        mediaPlayer.pause()
        playerState = PlayerState.PAUSED
    }

    fun currentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    fun release(){
        mediaPlayer.release()
    }

    fun getPlayerState(): PlayerState {
        return playerState
    }

}