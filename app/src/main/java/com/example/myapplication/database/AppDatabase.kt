package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.ProfileFragment


@Database(entities = [User::class, Settings::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @JvmStatic
        private var database: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "database-name"
                    )
                        .build()
                }
                return database!!
            }
        }



    }
}