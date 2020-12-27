package com.jenandsara.marvelapp.character.model

import com.google.gson.annotations.SerializedName

data class ComicsModel(
    @SerializedName("available")
    val available: Int,
    @SerializedName("returned")
    val returned: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: ItemsModel
)
