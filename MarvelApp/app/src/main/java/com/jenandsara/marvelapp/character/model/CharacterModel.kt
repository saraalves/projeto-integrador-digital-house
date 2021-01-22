package com.jenandsara.marvelapp.character.model

import com.google.gson.annotations.SerializedName
import com.jenandsara.marvelapp.character.model.image.ImagemModel
import com.jenandsara.marvelapp.character.model.image.ThumbnailModel

data class CharacterModel(
    val id: Int,
    @SerializedName("name")
    val nome: String,
    @SerializedName("description")
    val descricao: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailModel?,
    @SerializedName("images")
    val imagem: List<ImagemModel>?
)