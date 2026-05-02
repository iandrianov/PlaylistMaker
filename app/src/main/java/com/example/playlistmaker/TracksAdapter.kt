package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TracksAdapter(
    private val tracks: List<Track>
) : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivArtWork: ImageView = itemView.findViewById(R.id.ivArtwork)
        val tvTrackName: TextView = itemView.findViewById(R.id.tvTrackName)
        val tvArtistName: TextView = itemView.findViewById(R.id.tvArtistName)
        val tvTrackTime: TextView = itemView.findViewById(R.id.tvTrackTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]

        holder.tvTrackName.text = track.trackName
        holder.tvArtistName.text = track.artistName
        holder.tvTrackTime.text = track.trackTime

        Glide.with(holder.itemView)
            .load(track.artworkUrl100)
            .transform(RoundedCorners(16))
            .error(R.drawable.ic_placeholder)
            .into(holder.ivArtWork)
    }

    override fun getItemCount(): Int = tracks.size

}