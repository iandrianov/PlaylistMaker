package com.example.playlistmaker.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.adapters.dpToPx
import com.example.playlistmaker.model.Track
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Date
import java.util.Locale

class PlayerActivity : AppCompatActivity() {
    private lateinit var backButton: ImageButton
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

    fun initViews() {
        val cover = findViewById<ImageView>(R.id.iv_cover)
        backButton = findViewById(R.id.btn_back)
        findViewById<TextView>(R.id.tv_track_name).text =
            intent.getStringExtra(EXTRA_TRACK_NAME)

        findViewById<TextView>(R.id.tv_artist).text =
            intent.getStringExtra(EXTRA_ARTIST_NAME)

        findViewById<TextView>(R.id.tv_duration).text =
            formatTrackTime(intent.getLongExtra(EXTRA_TRACK_TIME, 0L))

        findViewById<TextView>(R.id.tv_album).text =
            intent.getStringExtra(EXTRA_ALBUM)

        findViewById<TextView>(R.id.tv_year).text =
             extractYear( intent.getStringExtra(EXTRA_YEAR))

        findViewById<TextView>(R.id.tv_genre).text =
            intent.getStringExtra(EXTRA_GENRE)

        findViewById<TextView>(R.id.tv_country).text =
            intent.getStringExtra(EXTRA_COUNTRY)

        loadCover(cover)
    }

    fun initListeners(){
        backButton.setOnClickListener {
            finish()
        }
    }
    private fun loadCover(cover: ImageView) {
        val artworkUrl = intent.getStringExtra(EXTRA_ARTWORK_URL)
            ?.replaceAfterLast("/", COVER_SIZE)

        Glide.with(this)
            .load(artworkUrl)
            .transform(RoundedCorners(8.dpToPx(this)))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(cover)
    }

    private fun formatTrackTime(trackTimeMillis: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault())
            .format(Date(trackTimeMillis))
    }
    

    private fun extractYear(dateString: String?): String {
        return dateString
            ?.let { OffsetDateTime.parse(it).year.toString() }
            ?: ""
    }
}