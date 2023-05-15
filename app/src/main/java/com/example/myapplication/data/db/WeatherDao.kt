package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherInformation WHERE cityName = :cityName")
    fun getCachedWeatherResponse(cityName: String): WeatherInformation

    @Query("SELECT cityName FROM WeatherInformation")
    fun getAllCityNames(): List<String>

    @Query("SELECT COUNT(*) FROM WeatherInformation WHERE latitude = :latitude AND longitude = :longitude")
    fun checkIfCityExists(latitude: Float, longitude: Float): Int

    @Query("SELECT * FROM WeatherInformation WHERE latitude = :latitude AND longitude = :longitude")
    fun getWeatherInfoByCoords(latitude: Float, longitude: Float): WeatherInformation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherResponse(cachedWeatherResponse: WeatherInformation)

    @Query("SELECT date FROM WeatherInformation WHERE cityName = :cityName")
    fun getDateInfoByCityName(cityName: String): Long

    @Query("SELECT date FROM WeatherInformation WHERE latitude = :latitude AND longitude = :longitude")
    fun getDateInfoByCoords(latitude: Float, longitude: Float): Long
}