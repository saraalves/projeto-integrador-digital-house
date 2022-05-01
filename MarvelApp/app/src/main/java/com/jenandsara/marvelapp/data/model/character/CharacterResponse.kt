package com.jenandsara.marvelapp.data.model.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("thumbnail") val thumbnail: ThumbnailResponse?,
    @SerialName("images") val images: List<ImageResponse>?,
    var isFavorite: Boolean = false
)