package com.example.myapplication.data

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.model.OpenWeatherApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object OpenWeatherService {
    private var okHttpClient: OkHttpClient? = null
    private var retrofitInstance: OpenWeatherApiService? = null

    private fun createInstance() {
        if (okHttpClient == null) {
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val requestUrl = chain.request().url
                    val newUrl = requestUrl.newBuilder()
                        .addQueryParameter("appid", BuildConfig.WEATHER_KEY)
                        .addQueryParameter("units", "metric")
                        .build()

                    chain.proceed(chain.request().newBuilder().url(newUrl).build())
                }
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
                .build()
        }
        val retrofitBuilder = okHttpClient?.let {
            Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_BASE_URL)
                .client(it)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        if (retrofitBuilder != null) {
            retrofitInstance = retrofitBuilder.create(OpenWeatherApiService::class.java)
        }
    }

    fun getInstance(): OpenWeatherApiService? {
        return if (retrofitInstance == null) {
            createInstance()
            retrofitInstance

        } else retrofitInstance
    }


}