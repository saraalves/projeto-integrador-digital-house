package com.example.marvelapp.character.repository

import com.example.marvelapp.character.model.CharacterModel
import com.example.marvelapp.character.model.ResponseModel
import com.example.marvelapp.network.NetworkUtils
import retrofit2.http.GET
import retrofit2.http.Query

interface ICharacterEndpoint {
    @GET("/v1/public/characters")
    suspend fun getCharacter(@Query("offset") offset: Int? = 0): ResponseModel<CharacterModel>

    companion object {
        val Endpoint: ICharacterEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(ICharacterEndpoint::class.java)
        }
    }
}