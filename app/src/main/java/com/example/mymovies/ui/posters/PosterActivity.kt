package com.example.mymovies.ui.posters

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mymovies.utils.Creator
import com.example.mymovies.R
import com.example.mymovies.presentation.movies.MoviesViewModel
import com.example.mymovies.presentation.poster.PosterViewModel
import com.example.mymovies.presentation.poster.PosterViewModelFactory


class PosterActivity : AppCompatActivity() {

    private var viewModel: PosterViewModel? = null

    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        poster = findViewById(R.id.poster)

        val imageUrl = intent.extras?.getString("poster", "") ?: ""
        viewModel = ViewModelProvider(this, PosterViewModel.getFactory(imageUrl))
            .get(PosterViewModel::class.java)

        viewModel?.observeUrl()?.observe(this) {
            setupPosterImage(it)
        }
    }

    fun setupPosterImage(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }
}