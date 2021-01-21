package com.jenandsara.marvelapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jenandsara.marvelapp.offline.dao.CharacterDAO
import com.jenandsara.marvelapp.offline.entity.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun characterDAO(): CharacterDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabse(context: Context): AppDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "offlineData"
                ).build()
            }
            return INSTANCE!!
        }
    }
}