package com.example.myapplication.database

class DatabaseRepository(private val database: AppDatabase) {
    suspend fun getUser(login: String, password: String): User {
        return database.userDao().getUser(login, password)
    }
    suspend fun getUserById(id: Long?): User {
        return database.userDao().getUserById(id)
    }

    suspend fun getSettingsForUser(userId: Long): Settings {
        return database.settingsDao().getSettingsForUser(userId)
    }

    suspend fun insertSettings(settings: Settings) {
        database.settingsDao().insert(settings)
    }

    suspend fun updateUser(user: User) {
        database.userDao().update(user)
    }
    suspend fun insertUser(user: User) {
        database.userDao().insert(user)
    }

    suspend fun updateSettings(settings: Settings) {
        database.settingsDao().update(settings)
    }

    fun getAllUsers(): List<User> {
        return database.userDao().getAll()
    }
}
