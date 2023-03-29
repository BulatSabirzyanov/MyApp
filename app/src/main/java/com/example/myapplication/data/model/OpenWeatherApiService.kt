package com.example.myapplication.data.model

import com.example.myapplication.data.model.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface OpenWeatherApiService {

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") city: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByCityLocation(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): WeatherResponse

    suspend fun getWeatherByCityNameQueryMap(
        @QueryMap param: Map<String, String>
    ): WeatherResponse
}