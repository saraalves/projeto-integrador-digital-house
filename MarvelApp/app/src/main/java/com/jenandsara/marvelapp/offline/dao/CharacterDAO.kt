package com.jenandsara.marvelapp.offline.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jenandsara.marvelapp.offline.entity.CharacterEntity

@Dao
interface CharacterDAO {
    @Insert
    suspend fun adicionarCharacter(characterEntity: CharacterEntity)

    @Query("SELECT * FROM OfflineData")
    suspend fun getAllCharacters(): List<CharacterEntity>
}