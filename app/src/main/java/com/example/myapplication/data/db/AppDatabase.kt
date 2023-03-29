package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [WeatherInformation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

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
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return database!!
            }
        }
    }

}

val migration2to3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE WeatherInformation")
    }
}
val migration3to4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE WeatherInformation")
    }
}
val migration1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE WeatherInformation ADD COLUMN newColumn TEXT")
    }
}



