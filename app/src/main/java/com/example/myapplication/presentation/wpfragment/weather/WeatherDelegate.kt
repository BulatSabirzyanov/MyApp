package com.example.myapplication.presentation.wpfragment.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.myapplication.databinding.WeatherItemBinding
import com.example.myapplication.presentation.wpfragment.AdapterDelegate
import com.example.myapplication.presentation.wpfragment.DelegateItem

class WeatherDelegate(private val glide: RequestManager) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as WeatherModel)
    }
//    c днем рождения!

    override fun isOfViewType(item: DelegateItem): Boolean = item is WeatherDelegateItem

    inner class ViewHolder(private val binding: WeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: WeatherModel) {
            with(binding) {
                glide.load("https://openweathermap.org/img/wn/${model.icon}.png")
                    .into(iVIconWeatherItem)
                tVMainWeatherItem.text = model.main
                tVTempWeatherItem.text = "${model.temperature.toInt()}°C"
                tVFeelsLikeWeatherItem.text = "Ощущается ${model.feelsLike.toInt()}°C"
                tVWindSpeedWeatherItem.text = "Скорость ветра ${model.windSpeed}"
                tVDateWeatherItem.text = model.date.split(" ")[1]
            }
        }
    }
}

