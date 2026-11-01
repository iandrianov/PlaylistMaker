
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

    private val screenState = MutableLiveData(PlayerScreenState())
    val screenStateLiveData: LiveData<PlayerScreenState> = screenState

    fun prepare(url: String) {
        playerInteractor.prepare(
            url,
            onPrepared = {
                updateScreenState(
                    playerState = PlayerState.PREPARED
                )
            },
            onCompletion = {
                updateScreenState(
                    playerState = PlayerState.PREPARED,
                    currentPosition = 0
                )
            }
        )
    }

    fun togglePlayback() {
        when (playerInteractor.getPlayerState()) {
            PlayerState.PREPARED, PlayerState.PAUSED -> {
                playerInteractor.start()
                updateScreenState(playerState = PlayerState.PLAYING)
            }

            PlayerState.PLAYING -> {
                playerInteractor.pause()
                updateScreenState(playerState = PlayerState.PAUSED)
            }

            PlayerState.DEFAULT -> Unit
        }
    }

    fun updateProgress() {
        updateScreenState(
            currentPosition = playerInteractor.currentPosition()
        )
    }

    private fun updateScreenState(
        playerState: PlayerState? = null,
        currentPosition: Int? = null
    ) {
        val currentState = screenState.value ?: PlayerScreenState()

        screenState.value = currentState.copy(
            playerState = playerState ?: currentState.playerState,
            currentPosition = currentPosition ?: currentState.currentPosition
        )
    }

    override fun onCleared() {
        playerInteractor.release()
        super.onCleared()
    }

    companion object {
        fun getFactory(
            playerInteractor: PlayerInteractor
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    PlayerViewModel(playerInteractor)
                }
            }
    }
}
