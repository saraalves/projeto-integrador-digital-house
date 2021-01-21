package com.jenandsara.marvelapp.offline.repository

import com.jenandsara.marvelapp.offline.dao.CharacterDAO
import com.jenandsara.marvelapp.offline.entity.CharacterEntity

class OfflineRepository(private val characterDAO: CharacterDAO) {
    suspend fun adicionarCharacter(characterEntity: CharacterEntity) = characterDAO.adicionarCharacter(characterEntity)
    suspend fun getAllCharacters() = characterDAO.getAllCharacters()
}