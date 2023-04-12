package com.example.myapplication.data

import com.example.myapplication.data.model.OpenWeatherApiService
import okhttp3.OkHttpClient

object OpenWeatherForecastService {
    private var okHttpClient: OkHttpClient? = null
    private var retrofitInstance: OpenWeatherApiService? = null
}