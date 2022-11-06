package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataModel
import com.example.myapplication.R

class DataAdapter : RecyclerView.Adapter<DataViewHolder>() {
    private var dataList = mutableListOf<DataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = when(viewType){
            VIEW_LIKED -> R.layout.card_liked
            VIEW_CATEGORIES -> R.layout.card_categories
            VIEW_HEADER -> R.layout.card_header
            VIEW_ALBUMS -> R.layout.card_album
            else -> 0
        }

        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataList[position]){
            is DataModel.Liked -> VIEW_LIKED
            is DataModel.Categories -> VIEW_CATEGORIES
            is DataModel.Header -> VIEW_HEADER
            is DataModel.Albums -> VIEW_ALBUMS
        }
    }
    fun setItems(data: List<DataModel>){
        dataList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size


    companion object{
        const val VIEW_LIKED = 1
        const val VIEW_HEADER = 1
        const val VIEW_CATEGORIES = 1
        const val VIEW_ALBUMS = 1
    }

}