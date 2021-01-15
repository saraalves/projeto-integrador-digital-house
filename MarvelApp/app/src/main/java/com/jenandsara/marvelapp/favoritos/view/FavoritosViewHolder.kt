package com.jenandsara.marvelapp.favoritos.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity
import com.squareup.picasso.Picasso

class FavoritosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val _imagem = view.findViewById<ImageView>(R.id.cardFavoritado)
    private val _nome = view.findViewById<TextView>(R.id.txtNamePersongemFavoritado)
    val _toggleFavorita = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleFavoritarFavoritos)
    val _btnFavorita = view.findViewById<MaterialButton>(R.id.btnFavoritarFavoritos)

    fun bind(character: CharacterEntity) {
        _nome.text = character.name

        Picasso.get()
            .load(character.imgUrl)
            .into(_imagem)
    }
}