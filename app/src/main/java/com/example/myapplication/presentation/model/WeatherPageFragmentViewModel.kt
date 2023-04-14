package com.example.myapplication.presentation.model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.WeatherInformation
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.domain.usecase.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.usecase.GetWeatherForecastByCityNameUseCase
import com.example.myapplication.presentation.wpfragment.date.DateModel
import com.example.myapplication.presentation.wpfragment.weather.WeatherModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.random.Random

class WeatherPageFragmentViewModel @AssistedInject constructor(
    private val weatherRepository: WeatherRepository,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherForecastByCityNameUseCase: GetWeatherForecastByCityNameUseCase,
    @Assisted(ASSISTED_CITY_NAME) private val cityName: String

) : ViewModel() {
    init {
        getWeatherByCityName(cityName)
    }

    private val _fullInfoState: MutableLiveData<WeatherFullInfo> =
        MutableLiveData(null)
    val fullInfoState: LiveData<WeatherFullInfo> = _fullInfoState


    private val _listOfDates: MutableLiveData<List<DateModel>> = MutableLiveData(emptyList())

    val listOfDates: LiveData<List<DateModel>> = _listOfDates

    private val _weatherModels: MutableLiveData<List<WeatherModel>> = MutableLiveData(emptyList())
    val weatherModels: LiveData<List<WeatherModel>> = _weatherModels

    private fun getWeatherByCityName(cityName: String) {

        viewModelScope.launch(Dispatchers.Main) {

            val dateNow = Date().time / 1000


            launch {
                runCatching {
                    weatherRepository.getWeatherInfo(
                        cityName, (dateNow - weatherRepository.getDateInfoByCityName(
                            cityName
                        ))
                    )


                }.onSuccess { weatherFullInfo ->
                    weatherFullInfo.weatherForecastResponse.weatherList?.let { weatherList ->
                        val listDateItems = weatherList.mapNotNull { forecastInfo ->
                            forecastInfo.weatherList?.firstOrNull()?.id?.let { id ->
                                DateModel(
                                    Random.nextLong(10000, 1000000),
                                    forecastInfo.dtTxt.orEmpty()
                                )
                            }
                        }
                        _listOfDates.postValue(listDateItems)
                        Log.e("listofDate", listDateItems.toString())
                        val weatherModelsList = weatherList.mapNotNull { forecastInfo ->
                            forecastInfo.weatherList?.firstOrNull()?.let { weatherInfo ->
                                WeatherModel(
                                    id = Random.nextLong(10000, 100000),
                                    temperature = forecastInfo.main?.temp ?: 0f,
                                    feelsLike = forecastInfo.main?.feelsLike ?: 0f,
                                    main = weatherInfo.main.orEmpty(),
                                    windSpeed = forecastInfo.wind?.speed?.toString().orEmpty(),
                                    icon = weatherInfo.icon.orEmpty(),
                                    date = forecastInfo.dtTxt.orEmpty()
                                )
                            }
                        }
                        _weatherModels.postValue(weatherModelsList)
                        Log.e("listofDate", weatherModelsList.toString())
                    }



                    _fullInfoState.postValue(weatherFullInfo)

                    if ((dateNow - weatherRepository.getDateInfoByCityName(
                            cityName
                        )) > 60
                    ) {
                        withContext(Dispatchers.IO) {
                            val date = Date().time / 1000
                            val cachedWeatherResponse = WeatherInformation(
                                id = cityName + weatherFullInfo.weatherEntity.latitude.toString() + weatherFullInfo.weatherEntity.longitude.toString(),
                                date = date,
                                cityName = cityName,
                                latitude = weatherFullInfo.weatherEntity.latitude,
                                longitude = weatherFullInfo.weatherEntity.longitude,
                                temp = weatherFullInfo.weatherEntity.temperature,
                                feelsLike = weatherFullInfo.weatherEntity.feelsLike,
                                pressure = weatherFullInfo.weatherEntity.pressure,
                                humidity = weatherFullInfo.weatherEntity.humidity,
                                windSpeed = weatherFullInfo.weatherEntity.speed,
                                weatherMain = weatherFullInfo.weatherEntity.main,
                                weatherIcon = weatherFullInfo.weatherEntity.icon
                            )
                            weatherRepository.insertWeatherResponse(
                                cachedWeatherResponse
                            )
                        }
                    }


                }

            }


        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(ASSISTED_CITY_NAME) cityName: String
        ): WeatherPageFragmentViewModel
    }

    companion object {
        const val ASSISTED_CITY_NAME = "LATITUDE"

    }
}