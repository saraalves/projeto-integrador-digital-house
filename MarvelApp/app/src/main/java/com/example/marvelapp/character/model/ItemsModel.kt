package com.example.marvelapp.character.model

import com.google.gson.annotations.SerializedName

data class ItemsModel(
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)

