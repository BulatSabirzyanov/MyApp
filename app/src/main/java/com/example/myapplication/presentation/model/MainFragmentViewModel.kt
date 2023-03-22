package com.example.myapplication.presentation.model

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.myapplication.data.db.WeatherInfo
import com.example.myapplication.data.model.response.WeatherResponse
import com.example.myapplication.di.DataDependency
import com.example.myapplication.utils.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainFragmentViewModel : ViewModel() {


    private val _temperatureDataStateRemote: MutableLiveData<WeatherResponse?> =
        MutableLiveData(null)
    val temperatureDataStateRemote: LiveData<WeatherResponse?> = _temperatureDataStateRemote
    private val _temperatureDataStateCache: MutableLiveData<WeatherInfo?> = MutableLiveData(null)
    val temperatureDataStateCache: LiveData<WeatherInfo?> = _temperatureDataStateCache


    fun getWeatherByCityName(cityName: String, context: Context) {


        viewModelScope.launch(Dispatchers.IO) {
            val cityNames = databaseRepository.getAllCityNames()
            val dateNow = Date().time / 1000

            if (cityNames.contains(cityName) && ((dateNow - databaseRepository.getDateInfoByCityName(
                    cityName
                )) < 60)
            ) {

                val weatherInfo = databaseRepository.getCachedWeatherResponse(cityName)

                val cityNameDB = weatherInfo.cityName
                val temp = weatherInfo.temp?.toInt().toString()
                val src = weatherInfo.weatherIcon
                withContext(Dispatchers.Main) {
                    tVCityNameMainFragment.text = cityName
                    tVTempMainFragment.text = "$temp\u00B0C"
                    Glide.with(this@MainFragment).load(
                        "https://openweathermap.org/img/wn/$src.png"
                    ).into(iVWeatherIconMainFragment)

                }

            } else {
                launch {
                    runCatching {

                        DataDependency.getWeatherByCityNameUseCase.invoke(city = cityName)


                    }.onSuccess { weatherDataModel ->

                        _temperatureDataStateRemote.postValue(weatherResponse)
                        val temp = weatherResponse?.main?.temp?.toInt().toString()
                        val src = weatherResponse?.weatherList?.get(0)?.icon

                        withContext(Dispatchers.Main) {
                            tVCityNameMainFragment.text = cityName
                            tVTempMainFragment.text = "$temp\u00B0C"
                            Glide.with(this@MainFragment).load(
                                "https://openweathermap.org/img/wn/$src.png"
                            ).into(iVWeatherIconMainFragment)
                        }
                        withContext(Dispatchers.IO) {
                            val date = Date().time / 1000
                            val cachedWeatherResponse = WeatherInfo(
                                id = cityName + weatherResponse?.coords?.latitude.toString() + weatherResponse?.coords?.longitude.toString(),
                                date = date,
                                cityName = cityName,
                                latitude = weatherResponse?.coords?.latitude,
                                longitude = weatherResponse?.coords?.longitude,
                                temp = weatherResponse?.main?.temp,
                                feelsLike = weatherResponse?.main?.feelsLike,
                                tempMin = weatherResponse?.main?.tempMin,
                                tempMax = weatherResponse?.main?.tempMax,
                                pressure = weatherResponse?.main?.pressure,
                                humidity = weatherResponse?.main?.humidity,
                                windSpeed = weatherResponse?.wind?.speed,
                                weatherId = weatherResponse?.weatherList?.getOrNull(0)?.id,
                                weatherMain = weatherResponse?.weatherList?.getOrNull(0)?.main,
                                weatherDescription = weatherResponse?.weatherList?.getOrNull(
                                    0
                                )?.description,
                                weatherIcon = weatherResponse?.weatherList?.getOrNull(0)?.icon
                            )
                            databaseRepository.insertWeatherResponse(cachedWeatherResponse)
                        }


                    }.onFailure {
                        view.hideKeyboard()
                        Toast.makeText(
                            requireContext(),
                            "такого города не существует",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }
        }
    }


}