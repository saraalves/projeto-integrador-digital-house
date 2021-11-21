package com.jenandsara.marvelapp.v2.domain.comics.model

import com.jenandsara.marvelapp.v2.domain.character.model.image.ThumbnailModel

data class ComicsModel(
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailModel
)