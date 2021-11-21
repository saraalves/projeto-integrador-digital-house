package com.jenandsara.marvelapp.v2.domain.stories.repository

class StoriesRepository {
    private val client = IStoriesEndpoint.Endpoint
    suspend fun getStoriesById(id: Int, offset: Int? = 0) = client.getStoriesById(id, offset)
}