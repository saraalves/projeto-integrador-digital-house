package com.jenandsara.marvelapp.comics.model

import com.jenandsara.marvelapp.character.model.image.ThumbnailModel

data class ComicsModel(
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailModel
)