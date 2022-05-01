package com.jenandsara.marvelapp.data.datasource.remote.api

import com.jenandsara.marvelapp.data.model.character.CharacterResponse
import com.jenandsara.marvelapp.data.model.MarvelApiResponse
import com.jenandsara.marvelapp.data.network.NetworkService
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("v1/public/characters")
    suspend fun getCharacter(@Query("offset") offset: Int? = 0): MarvelApiResponse<CharacterResponse>

    @GET("v1/public/characters")
    suspend fun getCharacterByName(@Query("name") name: String?): MarvelApiResponse<CharacterResponse>

    @GET("v1/public/characters")
    suspend fun getCharacterByStartsWith(@Query("nameStartsWith") nameStartsWith: String?): MarvelApiResponse<CharacterResponse>

    @GET("v1/public/comics/{comicId}/characters")
    suspend fun getRecomended(@Path("comicId") comicId: Int?): MarvelApiResponse<CharacterResponse>

    companion object {

        val Service: CharacterService by lazy {
            NetworkService.getRetrofitInstance().create(CharacterService::class.java)
        }
    }
}