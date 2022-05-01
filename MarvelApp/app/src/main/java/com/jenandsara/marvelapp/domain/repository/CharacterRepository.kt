package com.jenandsara.marvelapp.domain.repository

import com.jenandsara.marvelapp.data.datasource.remote.api.CharacterService

class CharacterRepository {

    private val client = CharacterService.Service

    suspend fun getCharacter(offset: Int? = 0) = client.getCharacter(offset)
    suspend fun getCharacterByName(name: String?) = client.getCharacterByName(name)
    suspend fun getCharacterByStartsWith (string: String?) = client.getCharacterByStartsWith(string)
    suspend fun getRecomended (int: Int) = client.getRecomended(int)
}