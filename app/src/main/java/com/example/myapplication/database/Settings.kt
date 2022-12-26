package com.example.myapplication.database

import androidx.room.Entity
import androidx.room.*

@Entity(tableName = "settings",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"])
    ]
)
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "setting_1") val setting1: Boolean = true,
    @ColumnInfo(name = "setting_2") val setting2: Boolean = true,
    @ColumnInfo(name = "setting_3") val setting3: Boolean = true,
    @ColumnInfo(name = "user_id") val userId: Long
)