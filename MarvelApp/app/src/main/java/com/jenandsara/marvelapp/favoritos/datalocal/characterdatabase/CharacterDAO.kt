package com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CharacterDAO {

    @Insert
    suspend fun saveCharacter(characterEntity: CharacterEntity)

    @Query("SELECT * FROM CharacterData")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM CharacterData WHERE idAPI = :idAPI)")
    suspend  fun checkIfIsFavorite(idAPI: Int): Boolean

    @Query("DELETE FROM CharacterData WHERE idAPI = :idAPI")
    suspend  fun deleteFavorite(idAPI: Int)
}