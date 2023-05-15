package com.example.myapplication.presentation.wpfragment.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.databinding.DateItemBinding
import com.example.myapplication.presentation.wpfragment.AdapterDelegate
import com.example.myapplication.presentation.wpfragment.DelegateItem

class DateDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DateItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as DateModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is DateDelegateItem

    class ViewHolder(private val binding: DateItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: DateModel) {
            with(binding) {
                var data = model.date.split(" ")[0].split("-").slice(1..2)

                //date.text = "${data[0]}.${data[1]}"
                date.text = model.date
            }
        }
    }

}