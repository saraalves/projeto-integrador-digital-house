package com.jenandsara.marvelapp.data.datasource.remote.api

import com.jenandsara.marvelapp.data.model.MarvelApiResponse
import com.jenandsara.marvelapp.data.model.comics.ComicsResponse
import com.jenandsara.marvelapp.data.network.NetworkService
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicService {

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun getComicsById(@Path("characterId") characterId: Int?, @Query("offset") offset: Int? = 0): MarvelApiResponse<ComicsResponse>

    companion object {
        val Service: ComicService by lazy {
            NetworkService.getRetrofitInstance().create(ComicService::class.java)
        }
    }
}