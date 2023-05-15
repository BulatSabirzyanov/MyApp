package com.example.myapplication.di

import android.app.Application
import android.content.Context
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.OpenWeatherService
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.DatabaseRepository
import com.example.myapplication.data.mappers.WeatherInfoMapper
import com.example.myapplication.data.mappers.WeatherResponseMapper
import com.example.myapplication.data.model.OpenWeatherApiService
import com.example.myapplication.data.repository.WeatherRepositoryImpl
import com.example.myapplication.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    fun provideOpenWeatherApiService(): OpenWeatherApiService {
        return OpenWeatherService.getInstance()!!
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        remoteSource: OpenWeatherApiService,
        localSource: DatabaseRepository,
        weatherResponseMapper: WeatherResponseMapper,
        weatherInfoMapper: WeatherInfoMapper
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            remoteSource,
            localSource,
            weatherResponseMapper,
            weatherInfoMapper
        )
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(context: Context): DatabaseRepository {
        return DatabaseRepository(AppDatabase.getInstance(context = context))
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val requestUrl = chain.request().url
                    val newUrl = requestUrl.newBuilder()
                        .addQueryParameter("appid", BuildConfig.WEATHER_KEY)
                        .addQueryParameter("units", "metric")
                        .build()
                    chain.proceed(chain.request().newBuilder().url(newUrl).build())
                }
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }
}
