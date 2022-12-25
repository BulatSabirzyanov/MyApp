package com.example.myapplication.database


import android.content.Context

import androidx.lifecycle.ViewModel

class UserViewModel(context: Context) : ViewModel() {
    private val database = AppDatabase.getInstance(context)
    private val databaseRepository = DatabaseRepository(database)
    suspend fun getUser(login: String, password: String): User? {

        return databaseRepository.getUser(login, password)
    }

    suspend fun updateUser(user: User) {
        databaseRepository.updateUser(user)
    }
}