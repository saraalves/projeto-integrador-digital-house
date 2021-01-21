package com.jenandsara.marvelapp.datalocal.dao

import androidx.room.*
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun adicionarCharacter(character: List<CharacterEntity>): Long

    @Transaction
    @Query("UPDATE LocalData SET isFavorite = :int WHERE id_api = :id_api")
    suspend fun atualizarIsFavorite(int: Int, id_api: Int)

    @Query("SELECT * FROM LocalData")
    suspend fun obterTodos(): List<CharacterEntity>

    @Query("SELECT * FROM LocalData WHERE isFavorite=1")
    suspend fun obterFavoritos(): List<CharacterEntity>

}
