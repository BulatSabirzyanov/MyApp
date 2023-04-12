package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.presentation.model.WeatherPageFragmentViewModel
import com.example.myapplication.presentation.screens.MainFragment
import com.example.myapplication.presentation.screens.WeatherPageFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, PresentationModule::class, DomainModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(weatherPageFragment: WeatherPageFragment)

    fun weatherPageFragmentViewModel(): WeatherPageFragmentViewModel.Factory

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}