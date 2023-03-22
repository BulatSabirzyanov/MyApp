package com.example.myapplication.domain.entity

import com.example.myapplication.presentation.model.WeatherDataModel

data class WeatherEntity(
    val temperature: Float,
    val pressure: Float,
    val humidity: Float,
    val feelsLike: Float,
    val speed: Float,
    val main: String,
    val icon: String,
    val name: String
)

fun WeatherEntity.mapWeatherEntity(): WeatherDataModel {
    return WeatherDataModel(
        temperature = this.temperature,
        pressure = this.pressure,
        humidity = this.humidity,
        feelsLike = this.feelsLike,
        speed = this.speed,
        main = this.main,
        icon = this.icon,
        name = this.name
    )
}
