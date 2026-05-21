package com.example.playlistmaker.network

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.model.Movie

class MovieViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.filmName)
    private val image = itemView.findViewById<ImageView>(R.id.filmImage)
    fun bind(movie: Movie){
        title.text = movie.title
        Glide.with(itemView.context)
            .load(movie.image)
            .into(image)
    }
}