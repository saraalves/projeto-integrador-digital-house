package com.jenandsara.marvelapp.v2.domain.stories.repository

import com.jenandsara.marvelapp.v2.data.model.ResponseModel
import com.jenandsara.marvelapp.v2.domain.network.NetworkUtils
import com.jenandsara.marvelapp.v2.domain.stories.model.StoriesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IStoriesEndpoint {

    @GET("v1/public/characters/{characterId}/stories")
    suspend fun getStoriesById(@Path("characterId") characterId: Int?, @Query("offset") offset: Int? = 0): ResponseModel<StoriesModel>

    companion object {
        val Endpoint: IStoriesEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(IStoriesEndpoint::class.java)
        }
    }
}