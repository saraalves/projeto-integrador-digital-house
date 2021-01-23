package com.jenandsara.marvelapp.local.characterdatabase

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDAO {

    @Insert
    suspend fun saveCharacter(characterEntity: CharacterEntity)

    @Query("SELECT * FROM CharacterData")
    suspend fun getAllCharacters(): List<CharacterEntity>
}