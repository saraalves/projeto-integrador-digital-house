package com.jenandsara.marvelapp.data.model.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThumbnailResponse(
    @SerialName("path") val path: String,
    @SerialName("extension") val extension: String
) {
    fun getImagePath(imageResolution: String? = "detail"): String {
        return "$path/$imageResolution.$extension".replace("http://", "https://")
    }
}