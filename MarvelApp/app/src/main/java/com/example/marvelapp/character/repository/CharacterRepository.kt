package com.example.marvelapp.character.repository

class CharacterRepository {

    private val client = ICharacterEndpoint.Endpoint

    suspend fun getCharacter(offset: Int? = 0) = client.getCharacter(offset)
    suspend fun getCharacterById(id: Int) = client.getCharacterById(id)
}