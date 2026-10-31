package com.example.playlistmaker.player.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.adapters.dpToPx
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.model.PlayerState
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.player.ui.view_model.PlayerViewModel
import com.google.android.material.internal.ViewUtils.dpToPx
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var playButton: ImageButton
    private lateinit var currentTime: TextView

    private val viewModel: PlayerViewModel by viewModels {
        Creator.providePlayerViewModelFactory()
    }

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        const val EXTRA_TRACK_NAME = "extra_track_name"
        const val EXTRA_ARTIST_NAME = "extra_artist_name"
        const val EXTRA_TRACK_TIME = "extra_track_time"
        const val EXTRA_ARTWORK_URL = "extra_artwork_url"
        const val EXTRA_ALBUM = "extra_album"
        const val EXTRA_GENRE = "extra_genre"
        const val EXTRA_COUNTRY = "extra_country"
        const val EXTRA_YEAR = "extra_year"

        const val COVER_SIZE = "512x512bb.jpg"
        const val PREVIEW_URL = "preview_url"

        fun createIntent(context: Context, track: Track): Intent {
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(EXTRA_TRACK_NAME, track.trackName)
                putExtra(EXTRA_ARTIST_NAME, track.artistName)
                putExtra(EXTRA_TRACK_TIME, track.trackTimeMillis)
                putExtra(EXTRA_ARTWORK_URL, track.artworkUrl100)
                putExtra(EXTRA_ALBUM, track.collectionName)
                putExtra(EXTRA_GENRE, track.primaryGenreName)
                putExtra(EXTRA_YEAR, track.releaseDate)
                putExtra(EXTRA_COUNTRY, track.country)
                putExtra(PREVIEW_URL, track.previewUrl)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tracks_player)

        setupInsets()
        initViews()
        initListeners()
        observeViewModel()

        val url = intent.getStringExtra(PREVIEW_URL) ?: return

        if (viewModel.playerStateLiveData.value == PlayerState.DEFAULT) {
            viewModel.prepare(url)
        }
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }

    private fun initViews() {
        backButton = findViewById(R.id.btn_back)
        playButton = findViewById(R.id.playButton)
        currentTime = findViewById(R.id.current_time)

        findViewById<TextView>(R.id.tv_track_name).text =
            intent.getStringExtra(EXTRA_TRACK_NAME)

        findViewById<TextView>(R.id.tv_artist).text =
            intent.getStringExtra(EXTRA_ARTIST_NAME)

        findViewById<TextView>(R.id.tv_duration).text =
            formatTrackTime(intent.getIntExtra(EXTRA_TRACK_TIME, 0))

        findViewById<TextView>(R.id.tv_album).text =
            intent.getStringExtra(EXTRA_ALBUM)

        findViewById<TextView>(R.id.tv_year).text =
            extractYear(intent.getStringExtra(EXTRA_YEAR))

        findViewById<TextView>(R.id.tv_genre).text =
            intent.getStringExtra(EXTRA_GENRE)

        findViewById<TextView>(R.id.tv_country).text =
            intent.getStringExtra(EXTRA_COUNTRY)

        loadCover()
    }

    private fun initListeners() {
        backButton.setOnClickListener {
            finish()
        }

        playButton.setOnClickListener {
            viewModel.togglePlayback()
        }
    }

    private fun observeViewModel() {
        viewModel.playerStateLiveData.observe(this) { state ->
            when (state) {
                PlayerState.DEFAULT -> {
                    playButton.isEnabled = false
                }

                PlayerState.PREPARED -> {
                    playButton.isEnabled = true
                    playButton.setImageResource(R.drawable.ic_play_btn_84_84)
                    handler.removeCallbacks(updateProgressRunnable)
                }

                PlayerState.PLAYING -> {
                    playButton.isEnabled = true
                    playButton.setImageResource(R.drawable.ic_play_button_pause_84_84)
                    handler.removeCallbacks(updateProgressRunnable)
                    handler.post(updateProgressRunnable)
                }

                PlayerState.PAUSED -> {
                    playButton.isEnabled = true
                    playButton.setImageResource(R.drawable.ic_play_btn_84_84)
                    handler.removeCallbacks(updateProgressRunnable)
                }
            }
        }

        viewModel.currentPositionLiveData.observe(this) { position ->
            currentTime.text = formatTrackTime(position)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun loadCover() {
        val artworkUrl = intent.getStringExtra(EXTRA_ARTWORK_URL)
            ?.replaceAfterLast('/', COVER_SIZE)

        val artworkImage = findViewById<ImageView>(R.id.iv_cover)

        Glide.with(this)
            .load(artworkUrl)
            .placeholder(R.drawable.ic_placeholder)
            .transform(RoundedCorners(dpToPx(this, 8).toInt()))
            .into(artworkImage)
    }

    private fun formatTrackTime(timeMillis: Int): String {
        val totalSeconds = timeMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    private fun extractYear(date: String?): String {
        return date?.take(4).orEmpty()
    }

    private val updateProgressRunnable = object : Runnable {
        override fun run() {
            viewModel.updateProgress()
            handler.postDelayed(this, 300)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(updateProgressRunnable)
        super.onDestroy()
    }
}