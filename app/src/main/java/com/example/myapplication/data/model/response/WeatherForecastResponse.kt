package com.example.myapplication.data.model.response

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("list")
    val weatherList: List<WeatherForecastInfo>? = null,
    @SerializedName("city")
    val city: WeatherCityInfo? = null
)

data class WeatherForecastInfo(
    @SerializedName("main")
    val main: WeatherForecastMain? = null,
    @SerializedName("weather")
    val weatherList: List<WeatherInfo>? = null,
    @SerializedName("wind")
    val wind: WeatherWind? = null,
    @SerializedName("dt_txt")
    val dtTxt: String? = null

)

data class WeatherCityInfo(
    @SerializedName("name")
    val name: String?,
    @SerializedName("sunrise")
    val sunrise: Long?,
    @SerializedName("sunset")
    val sunset: Long?

)

data class WeatherForecastMain(
    @SerializedName("temp")
    val temp: Float?,
    @SerializedName("feels_like")
    val feelsLike: Float?,
    @SerializedName("pressure")
    val pressure: Float?,
    @SerializedName("humidity")
    val humidity: Float?
)
