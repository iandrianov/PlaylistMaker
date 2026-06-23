package com.example.playlistmaker.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.playlistmaker.R

class PosterActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        val posterImageView = findViewById<ImageView>(R.id.posterImageView)

        val poster = intent.getStringExtra("poster")

        Glide.with(this)
            .load(poster)
            .into(posterImageView)
    }
}