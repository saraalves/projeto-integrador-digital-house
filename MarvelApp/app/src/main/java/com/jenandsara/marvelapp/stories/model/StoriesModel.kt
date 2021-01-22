package com.jenandsara.marvelapp.stories.model

import com.jenandsara.marvelapp.character.model.image.ThumbnailModel

data class StoriesModel (
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailModel
)