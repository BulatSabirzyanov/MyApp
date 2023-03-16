package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherInfo WHERE cityName = :cityName")
    fun getCachedWeatherResponse(cityName: String): WeatherInfo

    @Query("SELECT cityName FROM WeatherInfo")
    fun getAllCityNames(): List<String>

    @Query("SELECT COUNT(*) FROM WeatherInfo WHERE latitude = :latitude AND longitude = :longitude")
    fun checkIfCityExists(latitude: Float, longitude: Float): Int

    @Query("SELECT * FROM WeatherInfo WHERE latitude = :latitude AND longitude = :longitude")
    fun getWeatherInfoByCoords(latitude: Float, longitude: Float): WeatherInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherResponse(cachedWeatherResponse: WeatherInfo)

    @Query("SELECT date FROM WeatherInfo WHERE cityName = :cityName")
    fun getDateInfoByCityName(cityName: String): Long

    @Query("SELECT date FROM WeatherInfo WHERE latitude = :latitude AND longitude = :longitude")
    fun getDateInfoByCoords(latitude: Float, longitude: Float): Long
}