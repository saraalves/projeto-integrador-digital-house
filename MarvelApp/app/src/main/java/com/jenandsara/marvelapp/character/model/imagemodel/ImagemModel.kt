package com.jenandsara.marvelapp.character.model.imagemodel

import com.google.gson.annotations.SerializedName

data class ImagemModel(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extensao: String
) {
    fun getImagePath(imageResolution: String? = "detail"): String {
        return "$path/$imageResolution.$extensao".replace("http://", "https://")
    }
}