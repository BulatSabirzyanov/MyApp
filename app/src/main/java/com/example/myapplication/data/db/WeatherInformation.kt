package com.example.myapplication.data.db


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WeatherInformation")
data class WeatherInformation(
    @PrimaryKey val id: String,
    val date: Long = 0,
    val cityName: String,
    val latitude: Float?,
    val longitude: Float?,
    val temp: Float?,
    val feelsLike: Float?,
    val pressure: Float?,
    val humidity: Float?,
    val windSpeed: Float?,
    val weatherMain: String?,
    val weatherIcon: String?
)
