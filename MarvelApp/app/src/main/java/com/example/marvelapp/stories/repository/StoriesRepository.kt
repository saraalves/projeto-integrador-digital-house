package com.example.marvelapp.stories.repository

class StoriesRepository {
    private val client = IStoriesEndpoint.Endpoint
    suspend fun getStoriesById(id: Int) = client.getStoriesById(id)
}