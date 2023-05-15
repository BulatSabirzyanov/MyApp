package com.example.myapplication.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.domain.usecase.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.usecase.GetWeatherByCoordsUseCase
import javax.inject.Inject

class MainFragmentViewModelFactory @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByCoordsUseCase: GetWeatherByCoordsUseCase

) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(
                weatherRepository,
                getWeatherByCityNameUseCase,
                getWeatherByCoordsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
