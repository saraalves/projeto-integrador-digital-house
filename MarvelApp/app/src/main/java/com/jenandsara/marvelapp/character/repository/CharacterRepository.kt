package com.jenandsara.marvelapp.character.repository

class CharacterRepository {

    private val client = ICharacterEndpoint.Endpoint

    suspend fun getCharacter(offset: Int? = 0) = client.getCharacter(offset)
    suspend fun getCharacterByName(name: String?) = client.getCharacterByName(name)
    suspend fun getCharacterByStartsWith (string: String?) = client.getCharacterByStartsWith(string)

}