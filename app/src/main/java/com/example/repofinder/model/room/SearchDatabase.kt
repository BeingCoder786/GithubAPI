package com.example.repofinder.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.repofinder.model.Repository

@Database(
    entities = [Repository::class],
    version = 12, // YYYY MM DD IT (Iteration)
    exportSchema = false,
)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao

    companion object {
        const val DATABASE_NAME = "application-db-search"

        @Volatile
        private var instance: SearchDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): SearchDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): SearchDatabase {
            return Room.databaseBuilder(context, SearchDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
        }
    }
}