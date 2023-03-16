package com.example.myapplication.data.db


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WeatherInfo")
data class WeatherInfo(
    @PrimaryKey val id: String,
    val date: Long = 0,
    val cityName: String,
    val latitude: Float?,
    val longitude: Float?,
    val temp: Float?,
    val feelsLike: Float?,
    val tempMin: Float?,
    val tempMax: Float?,
    val pressure: Float?,
    val humidity: Float?,
    val windSpeed: Float?,
    val weatherId: Long?,
    val weatherMain: String?,
    val weatherDescription: String?,
    val weatherIcon: String?
)
