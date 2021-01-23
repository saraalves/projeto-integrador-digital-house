package com.jenandsara.marvelapp.local.datamanager

import com.jenandsara.marvelapp.local.characterdatabase.CharacterDAO
import com.jenandsara.marvelapp.local.characterdatabase.CharacterEntity

class DataManager(private val characterDAO: CharacterDAO) {

    suspend fun saveCharacter(characterEntity: CharacterEntity) = characterDAO.saveCharacter(characterEntity)
    suspend fun getAllCharacters() = characterDAO.getAllCharacters()
}