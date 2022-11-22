package com.example.myapplication.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.City
import com.example.myapplication.databinding.CityListItemBinding

class CityHolder(
    private val binding: CityListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var city: City? = null

    fun bind(item: City) {
        city = item
        with(binding) {
            tvId.text = item.id.toString()
            tvName.text = item.name
        }
    }

    fun updateFields(bundle: Bundle) {
        bundle.run {
            getString("ID")?.also {
                binding.tvId.text = it
            }
            getString("NAME")?.also {
                binding.tvName.text = it
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup
        ) =
            CityHolder(
                CityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }
}
