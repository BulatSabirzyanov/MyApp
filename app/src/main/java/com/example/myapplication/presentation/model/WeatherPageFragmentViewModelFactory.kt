package com.example.myapplication.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.di.DataDependency

class WeatherPageFragmentViewModelFactory(private val dataDependency: DataDependency) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherPageFragmentViewModel::class.java)) {
            return WeatherPageFragmentViewModel(dataDependency) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}