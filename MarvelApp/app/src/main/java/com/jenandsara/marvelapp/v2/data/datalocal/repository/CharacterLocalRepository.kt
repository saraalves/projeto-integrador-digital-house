package com.jenandsara.marvelapp.v2.data.datalocal.repository

import com.jenandsara.marvelapp.v2.data.datalocal.characterdatabase.CharacterDAO
import com.jenandsara.marvelapp.v2.data.datalocal.characterdatabase.CharacterEntity

class CharacterLocalRepository(private val characterDAO: CharacterDAO) {

    suspend fun saveCharacter(characterEntity: CharacterEntity) = characterDAO.saveCharacter(characterEntity)
    suspend fun getAllCharacters(idUser: String) = characterDAO.getAllCharacters(idUser)
    suspend  fun checkIfIsFavorite(idAPI: Int, idUser: String)= characterDAO.checkIfIsFavorite(idAPI, idUser)
    suspend fun deleteCharacter(id: Int)= characterDAO.deleteFavorite(id)
}