package com.example.myapplication.di


import com.example.myapplication.data.repository.WeatherRepositoryImpl
import com.example.myapplication.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindsModule {

    @Binds
    abstract fun bindWeatherRepositoryImpl_to_WeatherRepository(repositoryImpl: WeatherRepositoryImpl): WeatherRepository
}