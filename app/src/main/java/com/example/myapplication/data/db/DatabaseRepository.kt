package com.example.myapplication.data.db


class DatabaseRepository(private val database: AppDatabase) {

    suspend fun getCachedWeatherResponse(cityName: String): WeatherInfo {
        return database.weatherDao().getCachedWeatherResponse(cityName)
    }

    suspend fun getAllCityNames(): List<String> {
        return database.weatherDao().getAllCityNames()
    }

    suspend fun checkIfCityExists(latitude: Float, longitude: Float): Int {
        return database.weatherDao().checkIfCityExists(latitude, longitude)
    }

    suspend fun getWeatherInfoByCoords(latitude: Float, longitude: Float): WeatherInfo {
        return database.weatherDao().getWeatherInfoByCoords(latitude, longitude)
    }

    suspend fun insertWeatherResponse(cachedWeatherResponse: WeatherInfo) {
        return database.weatherDao().insertWeatherResponse(cachedWeatherResponse)
    }

    suspend fun getDateInfoByCityName(cityName: String): Long {
        return database.weatherDao().getDateInfoByCityName(cityName)
    }

    suspend fun getDateInfoByCoords(latitude: Float, longitude: Float): Long {
        return database.weatherDao().getDateInfoByCoords(latitude, longitude)
    }

}