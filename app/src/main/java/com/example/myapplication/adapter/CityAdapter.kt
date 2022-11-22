package com.example.myapplication.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.City

class CityAdapter : ListAdapter<City, CityHolder>(CityComparator()) {

    class CityComparator : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: City, newItem: City): Any? {
            val bundle = Bundle()
            if (oldItem.id != newItem.id) {
                bundle.putString("ID", newItem.id.toString())
            }
            if (oldItem.name != newItem.name) {
                bundle.putString("NAME", newItem.name)
            }
            if (bundle.isEmpty) return null
            return bundle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder =
        CityHolder.create(parent)

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CityHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf { it is Bundle }?.let {
                holder.updateFields(it as Bundle)
            }
        }
    }

    override fun submitList(list: MutableList<City>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}