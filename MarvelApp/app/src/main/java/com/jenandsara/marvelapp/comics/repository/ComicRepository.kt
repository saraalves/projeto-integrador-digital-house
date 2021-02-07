package com.jenandsara.marvelapp.comics.repository

class ComicRepository {
    private val client = IComicEndpoint.Endpoint

    suspend fun getComicsById(id: Int, offset: Int? = 0) = client.getComicsById(id, offset)

}