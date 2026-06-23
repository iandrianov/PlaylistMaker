package com.example.playlistmaker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.model.Movie
import com.example.playlistmaker.network.MovieViewHolder
import com.example.playlistmaker.R

class MovieAdapter(private var movies: List<Movie>, val clickListener: MovieClickListener): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener { clickListener.onMovieClick(movies.get(position)) }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    fun interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }



}



