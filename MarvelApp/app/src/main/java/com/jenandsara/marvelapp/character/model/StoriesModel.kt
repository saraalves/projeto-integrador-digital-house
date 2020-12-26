package com.jenandsara.marvelapp.character.model

import com.google.gson.annotations.SerializedName

data class StoriesModel (
    @SerializedName("available")
    val available: Int,
    @SerializedName("returned")
    val returned: Int,
    @SerializedName("collectionURI")
    val collectionURI: Int,
    @SerializedName("items")
    val items: ItemsModel
)