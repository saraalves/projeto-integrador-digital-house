package com.jenandsara.marvelapp.favoritos.datalocal.repository

import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterDAO
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity

class CharacterLocalRepository(private val characterDAO: CharacterDAO) {

    suspend fun saveCharacter(characterEntity: CharacterEntity) = characterDAO.saveCharacter(characterEntity)
    suspend fun getAllCharacters() = characterDAO.getAllCharacters()
    suspend  fun checkIfIsFavorite(idAPI: Int)= characterDAO.checkIfIsFavorite(idAPI)
    suspend fun deleteCharacter(id: Int)= characterDAO.deleteFavorite(id)
}