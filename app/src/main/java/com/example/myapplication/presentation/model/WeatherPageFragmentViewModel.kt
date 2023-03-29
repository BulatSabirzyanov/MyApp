package com.example.myapplication.presentation.model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.WeatherInformation
import com.example.myapplication.di.DataDependency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class WeatherPageFragmentViewModel(private val dataDependency: DataDependency) : ViewModel() {

    private val _temperatureDataState: MutableLiveData<WeatherDataModel> =
        MutableLiveData(null)
    val temperatureDataState: LiveData<WeatherDataModel> = _temperatureDataState
    fun getWeatherByCityName(cityName: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val dateNow = Date().time / 1000
            val response = dataDependency.getWeatherByCityNameUseCase(
                cityName,
                time = (dateNow - dataDependency.weatherRepository.getDateInfoByCityName(
                    cityName
                ))
            )
            Log.e("ошибка во вью модал майн фрагмент", "${response}")

            launch {
                runCatching {
                    dataDependency.getWeatherByCityNameUseCase(
                        cityName,
                        time = (dateNow - dataDependency.weatherRepository.getDateInfoByCityName(
                            cityName
                        ))
                    )


                }.onSuccess { weatherDataModel ->
                    Log.e("ошибка во вью модал майн фрагмент", "$weatherDataModel")
                    _temperatureDataState.postValue(weatherDataModel)
                    if ((dateNow - dataDependency.weatherRepository.getDateInfoByCityName(
                            cityName
                        )) > 60
                    ) {
                        withContext(Dispatchers.IO) {
                            val date = Date().time / 1000
                            val cachedWeatherResponse = WeatherInformation(
                                id = cityName + weatherDataModel.latitude.toString() + weatherDataModel.longitude.toString(),
                                date = date,
                                cityName = cityName,
                                latitude = weatherDataModel.latitude,
                                longitude = weatherDataModel.longitude,
                                temp = weatherDataModel.temperature,
                                feelsLike = weatherDataModel.feelsLike,
                                pressure = weatherDataModel.pressure,
                                humidity = weatherDataModel.humidity,
                                windSpeed = weatherDataModel.speed,
                                weatherMain = weatherDataModel.main,
                                weatherIcon = weatherDataModel.icon
                            )
                            dataDependency.weatherRepository.insertWeatherResponse(
                                cachedWeatherResponse
                            )
                        }
                    }


                }.onFailure {
                }

            }

        }
    }
}