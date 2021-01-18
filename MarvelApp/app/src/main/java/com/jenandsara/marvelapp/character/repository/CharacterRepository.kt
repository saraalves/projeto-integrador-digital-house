package com.jenandsara.marvelapp.character.repository

import com.jenandsara.marvelapp.datalocal.dao.CharacterDao
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity

class CharacterRepository(private val characterDao: CharacterDao? = null) {

    constructor() : this(null)

    private val client = ICharacterEndpoint.Endpoint


    suspend fun getCharacter(offset: Int? = 0) = client.getCharacter(offset)
    suspend fun getCharacterByName(name: String?) = client.getCharacterByName(name)
    suspend fun getCharacterByStartsWith (string: String?) = client.getCharacterByStartsWith(string)

    suspend fun adicionarCharacter(character: List<CharacterEntity>) = characterDao?.adicionarCharacter(character)
    suspend fun atualizarIsFavorite(int: Int, id_api: Int) = characterDao?.atualizarIsFavorite(int, id_api)
    suspend fun obterTodos(): List<CharacterEntity>? = characterDao?.obterTodos()
    suspend fun obterFavoritos(): List<CharacterEntity>? = characterDao?.obterFavoritos()
}