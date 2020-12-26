package com.jenandsara.marvelapp.favoritos.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.home.model.PersonagemModel
import com.squareup.picasso.Picasso

class FavoritosViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val _imagem = view.findViewById<ImageView>(R.id.cardFavoritado)
    private val _nome = view.findViewById<TextView>(R.id.txtNamePersongemFavoritado)

    fun bind(personagemModel: PersonagemModel) {
        _nome.text = personagemModel.nome

        Picasso.get()
            .load(personagemModel.imagem)
            .into(_imagem)
    }
}