package com.jenandsara.marvelapp.character.repository

import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.data.model.ResponseModel
import com.jenandsara.marvelapp.network.NetworkUtils
import retrofit2.http.GET
import retrofit2.http.Query

interface ICharacterEndpoint {
    @GET("v1/public/characters")
    suspend fun getCharacter(@Query("offset") offset: Int? = 0): ResponseModel<CharacterModel>

    @GET("v1/public/characters")
    suspend fun getCharacterByName(@Query("name") name: String?): ResponseModel<CharacterModel>

    @GET("v1/public/characters")
    suspend fun getCharacterByStartsWith(@Query("nameStartsWith") nameStartsWith: String?): ResponseModel<CharacterModel>

    companion object {
        val Endpoint: ICharacterEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(ICharacterEndpoint::class.java)
        }
    }
}