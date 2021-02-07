package com.jenandsara.marvelapp.favoritos.datalocal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterDAO
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDAO(): CharacterDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "characterdata"
                ).build()
            }
            return INSTANCE!!
        }

    }
}