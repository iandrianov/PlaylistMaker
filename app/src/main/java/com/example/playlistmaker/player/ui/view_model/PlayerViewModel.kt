
package com.example.playlistmaker.player.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.domain.interactor.PlayerInteractor
import com.example.playlistmaker.domain.model.PlayerState

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor
) : ViewModel() {

    private val playerState = MutableLiveData(PlayerState.DEFAULT)
    val playerStateLiveData: LiveData<PlayerState> = playerState

    private val currentPosition = MutableLiveData(0)
    val currentPositionLiveData: LiveData<Int> = currentPosition

    fun prepare(url: String) {
        playerInteractor.prepare(
            url,
            onPrepared = {
                playerState.value = PlayerState.PREPARED
            },
            onCompletion = {
                playerState.value = PlayerState.PREPARED
                currentPosition.value = 0
            }
        )
    }

    fun togglePlayback() {
        when (playerInteractor.getPlayerState()) {
            PlayerState.PREPARED, PlayerState.PAUSED -> {
                playerInteractor.start()
                playerState.value = PlayerState.PLAYING
            }

            PlayerState.PLAYING -> {
                playerInteractor.pause()
                playerState.value = PlayerState.PAUSED
            }

            PlayerState.DEFAULT -> Unit
        }
    }

    fun updateProgress() {
        currentPosition.value = playerInteractor.currentPosition()
    }

    override fun onCleared() {
        playerInteractor.release()
        super.onCleared()
    }

    companion object {
        fun getFactory(
            playerInteractor: PlayerInteractor
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModel(playerInteractor)
            }
        }
    }
}
