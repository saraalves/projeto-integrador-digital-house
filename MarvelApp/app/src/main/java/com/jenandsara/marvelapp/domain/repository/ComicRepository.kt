package com.jenandsara.marvelapp.domain.repository

import com.jenandsara.marvelapp.data.datasource.remote.api.ComicService

class ComicRepository {
    private val client = ComicService.Service

    suspend fun getComicsById(id: Int, offset: Int? = 0) = client.getComicsById(id, offset)

}