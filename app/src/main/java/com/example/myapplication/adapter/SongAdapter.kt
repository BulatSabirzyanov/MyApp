package com.example.myapplication.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Song

class SongAdapter(
    private val filmList: List<Song>,
    private val navigate: (Int) -> Unit
) : RecyclerView.Adapter<SongHolder>() {

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.bind(filmList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder =
        SongHolder.create(parent, navigate)
}