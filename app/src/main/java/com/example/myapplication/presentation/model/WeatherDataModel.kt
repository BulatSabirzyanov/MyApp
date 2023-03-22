package com.example.myapplication.presentation.model

data class WeatherDataModel(
    val temperature: Float,
    val pressure: Float,
    val humidity: Float,
    val feelsLike: Float,
    val speed: Float,
    val main: String,
    val icon: String,
    val name: String
)


