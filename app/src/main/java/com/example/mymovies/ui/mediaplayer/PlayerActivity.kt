package com.example.mymovies.ui.mediaplayer

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.R
import java.text.SimpleDateFormat
import java.util.Locale

// Не забываем унаследоваться от AppCompatActivity т.к. будем работать с ViewModel
class PlayerActivity : AppCompatActivity() {

    private lateinit var viewModel: PlayerViewModel

    private lateinit var playButton: Button
    private lateinit var timerTextView: TextView

    private val url: String = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview112/v4/ac/c7/d1/acc7d13f-6634-495f-caf6-491eccb505e8/mzaf_4002676889906514534.plus.aac.p.m4a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playButton = findViewById(R.id.playButton)
        timerTextView = findViewById(R.id.timerTextView)

        viewModel = ViewModelProvider(this,PlayerViewModel.getFactory(url))
            .get(PlayerViewModel::class.java)

        viewModel.observePlayerState().observe(this) {
            changeButtonText(it == PlayerViewModel.STATE_PLAYING)
            enableButton(it != PlayerViewModel.STATE_DEFAULT)
        }

        viewModel.observeProgressTime().observe(this) {
            timerTextView.text = it
        }

        playButton.setOnClickListener {
            viewModel.onPlayButtonClicked()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    private fun enableButton(isEnabled: Boolean) {
        playButton.isEnabled = isEnabled
    }

    private fun changeButtonText(isPlaying: Boolean) {
        playButton.text = if (isPlaying) "PAUSE" else "PLAY"
    }
}

private fun PlayerViewModel.onPause() {
    TODO("Not yet implemented")
}
