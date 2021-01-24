package com.jenandsara.marvelapp.favoritos.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity
import com.squareup.picasso.Picasso

class FavoritosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val _imagem = view.findViewById<ImageView>(R.id.cardFavoritado)
    private val _nome = view.findViewById<TextView>(R.id.txtNamePersongemFavoritado)

    fun bind(personagemModel: CharacterEntity) {
        _nome.text = personagemModel.nome

        Picasso.get()
            .load(personagemModel.imgPath)
            .into(_imagem)
    }
}