package com.example.playlistmaker.player.ui.view_model

import com.example.playlistmaker.domain.model.PlayerState

data class PlayerScreenState(
    val playerState: PlayerState = PlayerState.DEFAULT,
    val currentPosition: Int = 0
)