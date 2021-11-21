package com.jenandsara.marvelapp.v2.domain.stories.model

import com.jenandsara.marvelapp.v2.domain.character.model.image.ThumbnailModel

data class StoriesModel (
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailModel
)