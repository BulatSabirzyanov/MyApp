package com.example.myapplication.database

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE login = :login AND password = :password")
    fun getUser(login: String, password: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}