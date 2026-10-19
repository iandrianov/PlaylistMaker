package com.example.mymovies.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovies.R
import com.example.mymovies.databinding.ListItemMovieBinding
import com.example.mymovies.domain.models.Movie


class MovieViewHolder(private val binding: ListItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        Glide.with(binding.root)
            .load(movie.image)
            .into(binding.cover)

        binding.title.text = movie.title
        binding.description.text = movie.description
    }

    companion object {
        fun from(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemMovieBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding)
        }
    }
}