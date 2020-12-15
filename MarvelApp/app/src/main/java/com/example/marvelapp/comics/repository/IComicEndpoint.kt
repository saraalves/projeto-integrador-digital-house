package com.example.marvelapp.comics.repository

import com.example.marvelapp.comics.model.ComicsModel
import com.example.marvelapp.data.model.ResponseModel
import com.example.marvelapp.network.NetworkUtils
import retrofit2.http.GET
import retrofit2.http.Path

interface IComicEndpoint {

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun getComicsById(@Path("characterId") characterId: Int?): ResponseModel<ComicsModel>

    companion object {
        val Endpoint: IComicEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(IComicEndpoint::class.java)
        }
    }
}