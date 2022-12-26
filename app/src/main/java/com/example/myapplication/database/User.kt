package com.example.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true,) val id: Long = 0,
    @ColumnInfo(name = "login") var login: String,
    @ColumnInfo(name = "password") val password: String,

    )