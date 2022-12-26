package com.example.myapplication.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE login = :login AND password = :password")
    suspend fun getUser(login: String, password: String): User

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long?): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)


}