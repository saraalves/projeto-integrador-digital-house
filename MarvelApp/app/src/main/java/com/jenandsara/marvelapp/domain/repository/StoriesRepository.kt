package com.jenandsara.marvelapp.domain.repository

import com.jenandsara.marvelapp.data.datasource.remote.api.StoriesService

class StoriesRepository {
    private val client = StoriesService.Service
    suspend fun getStoriesById(id: Int, offset: Int? = 0) = client.getStoriesById(id, offset)
}