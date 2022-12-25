package com.example.myapplication.database

class DatabaseRepository(private val database: AppDatabase) {
    fun getUser(login: String, password: String): User? {
        return database.userDao().getUser(login, password)
    }

    suspend fun getSettingsForUser(userId: Long): Settings {
        return database.settingsDao().getSettingsForUser(userId)
    }

    suspend fun updateUser(user: User) {
        database.userDao().update(user)
    }
    fun insertUser(user: User) {
        database.userDao().insert(user)
    }

    suspend fun updateSettings(settings: Settings) {
        database.settingsDao().update(settings)
    }
}
