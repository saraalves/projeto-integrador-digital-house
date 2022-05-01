package com.jenandsara.marvelapp.data.datasource.remote.api

import com.jenandsara.marvelapp.data.model.MarvelApiResponse
import com.jenandsara.marvelapp.data.network.NetworkService
import com.jenandsara.marvelapp.data.model.stories.StoriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoriesService {

    @GET("v1/public/characters/{characterId}/stories")
    suspend fun getStoriesById(@Path("characterId") characterId: Int?, @Query("offset") offset: Int? = 0): MarvelApiResponse<StoriesResponse>

    companion object {
        val Service: StoriesService by lazy {
            NetworkService.getRetrofitInstance().create(StoriesService::class.java)
        }
    }
}