package com.jenandsara.marvelapp.data.model.stories

import com.jenandsara.marvelapp.data.model.character.ThumbnailResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoriesResponse (
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("thumbnail") val thumbnail: ThumbnailResponse
)