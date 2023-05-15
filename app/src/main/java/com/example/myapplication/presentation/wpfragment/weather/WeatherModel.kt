package com.example.myapplication.presentation.wpfragment.weather

data class WeatherModel(
    val id: Long,
    val temperature: Float,
    val feelsLike: Float,
    val main: String,
    val windSpeed: String,
    val icon: String,
    val date: String
)
