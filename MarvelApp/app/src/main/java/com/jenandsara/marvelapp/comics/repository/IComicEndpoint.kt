package com.jenandsara.marvelapp.comics.repository

import com.jenandsara.marvelapp.data.model.ResponseModel
import com.jenandsara.marvelapp.comics.model.ComicsModel
import com.jenandsara.marvelapp.network.NetworkUtils
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IComicEndpoint {

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun getComicsById(@Path("characterId") characterId: Int?, @Query("offset") offset: Int? = 0): ResponseModel<ComicsModel>

    companion object {
        val Endpoint: IComicEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(IComicEndpoint::class.java)
        }
    }
}