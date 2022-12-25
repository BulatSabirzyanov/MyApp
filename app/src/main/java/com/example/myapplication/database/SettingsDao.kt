package com.example.myapplication.database

import androidx.room.*

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE user_id = :userId")
    suspend fun getSettingsForUser(userId: Long): Settings

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Update
    suspend fun update(settings: Settings)
}