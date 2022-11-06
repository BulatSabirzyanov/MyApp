package com.example.myapplication.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataModel
import com.example.myapplication.R


class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private fun bindLiked(data: DataModel.Liked){
        val title_liked: TextView = itemView.findViewById(R.id.tv_title_liked)
        val discription_liked: TextView = itemView.findViewById(R.id.tv_description)

        title_liked.text = data.title_liked
        discription_liked.text = data.discription_liked


    }
    private fun bindCategories(data: DataModel.Categories){
        val title_catecories: TextView = itemView.findViewById(R.id.tv_title_categories)
        title_catecories.text = data.title_categories

    }
    private fun bindHeader(data: DataModel.Header){
        val title_header: TextView = itemView.findViewById(R.id.tv_title_header)
        title_header.text = data.title_header
    }
    private fun bindAlbums(data: DataModel.Albums){
        val title_albums: TextView = itemView.findViewById(R.id.tv_title_header)
        title_albums.text = data.title_albums
    }


    fun bind(data:DataModel){
        when(data){
            is DataModel.Liked ->bindLiked(data)
            is DataModel.Categories -> bindCategories(data)
            is DataModel.Header -> bindHeader(data)
            is DataModel.Albums -> bindAlbums(data)
        }
    }
}