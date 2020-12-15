package com.example.marvelapp.stories.repository

import com.example.marvelapp.data.model.ResponseModel
import com.example.marvelapp.network.NetworkUtils
import com.example.marvelapp.stories.model.StoriesModel
import retrofit2.http.GET
import retrofit2.http.Path

interface IStoriesEndpoint {

    @GET("v1/public/characters/{characterId}/stories")
    suspend fun getStoriesById(@Path("characterId") characterId: Int?): ResponseModel<StoriesModel>

    companion object {
        val Endpoint: IStoriesEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(IStoriesEndpoint::class.java)
        }
    }
}