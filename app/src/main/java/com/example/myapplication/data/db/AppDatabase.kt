package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [WeatherInfo::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao


    companion object {
        @JvmStatic
        private var database: AppDatabase? = null

        private val migration2to3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add migration code here
            }
        }

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

val migration2to3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE WeatherInfo")
    }
}
val migration1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE WeatherInfo ADD COLUMN newColumn TEXT")
    }
}



