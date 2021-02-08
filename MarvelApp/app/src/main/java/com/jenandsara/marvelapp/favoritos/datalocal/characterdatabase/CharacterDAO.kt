package com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CharacterDAO {

    @Insert
    suspend fun saveCharacter(characterEntity: CharacterEntity)

    @Query("SELECT * FROM CharacterData WHERE idUser = :idUser")
    suspend fun getAllCharacters(idUser:String): List<CharacterEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM CharacterData WHERE idAPI = :idAPI AND idUser = :idUser)")
    suspend  fun checkIfIsFavorite(idAPI: Int, idUser: String): Boolean

    @Query("DELETE FROM CharacterData WHERE idAPI = :idAPI")
    suspend  fun deleteFavorite(idAPI: Int)
}