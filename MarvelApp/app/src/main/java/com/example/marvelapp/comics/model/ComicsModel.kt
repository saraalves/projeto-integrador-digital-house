package com.example.marvelapp.comics.model

import com.example.marvelapp.character.model.ThumbnailModel
import com.example.marvelapp.creators.model.CreatorsModel

data class ComicsModel(
        val id: Int,
        val title: String,
        val thumbnail: ThumbnailModel
)
