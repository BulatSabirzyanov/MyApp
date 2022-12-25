package com.example.myapplication.database

import androidx.lifecycle.ViewModel

class SettingsViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {
    suspend fun getSettingsForUser(userId: Long): Settings {
        return databaseRepository.getSettingsForUser(userId)
    }

    suspend fun updateSettings(settings: Settings) {
        databaseRepository.updateSettings(settings)
    }
}