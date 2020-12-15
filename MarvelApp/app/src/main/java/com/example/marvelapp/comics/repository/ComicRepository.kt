package com.example.marvelapp.comics.repository

class ComicRepository {
    private val client = IComicEndpoint.Endpoint
    suspend fun getComicsById(id: Int) = client.getComicsById(id)
}