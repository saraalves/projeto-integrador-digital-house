package com.jenandsara.marvelapp.stories.repository

import com.jenandsara.marvelapp.stories.repository.IStoriesEndpoint

class StoriesRepository {
    private val client = IStoriesEndpoint.Endpoint
    suspend fun getStoriesById(id: Int) = client.getStoriesById(id)
}