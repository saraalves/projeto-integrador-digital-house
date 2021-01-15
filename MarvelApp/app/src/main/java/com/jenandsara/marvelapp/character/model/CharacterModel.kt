package com.jenandsara.marvelapp.character.model

import com.google.gson.annotations.SerializedName
import com.jenandsara.marvelapp.character.model.imagemodel.ImagemModel
import com.jenandsara.marvelapp.character.model.imagemodel.ThumbnailModel

data class CharacterModel(
    val id: Int,
    @SerializedName("name")
    val nome: String,
    @SerializedName("description")
    val descricao: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailModel?,
    @SerializedName("images")
    val imagem: List<ImagemModel>?,
    var isFavorite: Int
)