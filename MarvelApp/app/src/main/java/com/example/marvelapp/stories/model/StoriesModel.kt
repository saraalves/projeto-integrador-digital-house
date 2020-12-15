package com.example.marvelapp.stories.model

import com.example.marvelapp.character.model.ThumbnailModel
import com.example.marvelapp.creators.model.CreatorsModel

data class StoriesModel (
        val id: Int,
        val title: String,
        val thumbnail: ThumbnailModel
)