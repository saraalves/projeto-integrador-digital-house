package com.jenandsara.marvelapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jenandsara.marvelapp.datalocal.dao.CharacterDao
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        // Singleton
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "local_data"
                ).build()
            }

            return INSTANCE!!
        }
    }
}