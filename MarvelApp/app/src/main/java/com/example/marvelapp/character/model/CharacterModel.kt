package com.example.marvelapp.character.model

import com.google.gson.annotations.SerializedName

data class CharacterModel(
    val id: Int,
    @SerializedName("name")
    val nome: String,
    @SerializedName("description")
    val descricao: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailModel
)