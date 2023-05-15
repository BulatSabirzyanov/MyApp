package com.example.myapplication.di


import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.domain.usecase.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.usecase.GetWeatherByCoordsUseCase
import com.example.myapplication.presentation.model.MainFragmentViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideMainFragmentViewModelFactory(
        weatherRepository: WeatherRepository,
        getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
        getWeatherByCoordsUseCase: GetWeatherByCoordsUseCase

    ): MainFragmentViewModelFactory {
        return MainFragmentViewModelFactory(
            weatherRepository,
            getWeatherByCityNameUseCase,
            getWeatherByCoordsUseCase
        )
    }


}