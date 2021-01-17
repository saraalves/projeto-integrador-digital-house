package com.jenandsara.marvelapp.datalocal.repository

import com.jenandsara.marvelapp.datalocal.dao.CharacterDao
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity

class LocalCharacterRepository(private val characterDao: CharacterDao) {

    suspend fun adicionarCharacter(character: CharacterEntity) = characterDao.adicionarCharacter(character)
    suspend fun atualizarIsFavorite(int: Int, id_api: Int) = characterDao.atualizarIsFavorite(int, id_api)
    suspend fun obterTodos(): List<CharacterEntity> = characterDao.obterTodos()
    suspend fun obterFavoritos(): List<CharacterEntity> = characterDao.obterFavoritos()
}