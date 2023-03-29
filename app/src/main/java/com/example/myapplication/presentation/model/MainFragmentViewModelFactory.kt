package com.example.myapplication.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.di.DataDependency

class MainFragmentViewModelFactory(private val dataDependency: DataDependency) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(dataDependency) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
