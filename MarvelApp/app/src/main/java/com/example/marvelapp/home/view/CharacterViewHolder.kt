package com.example.marvelapp.home.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.character.model.CharacterModel
import com.example.marvelapp.home.model.PersonagemModel
import com.squareup.picasso.Picasso

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imagem = view.findViewById<ImageView>(R.id.cardPersonagem)
    private val nome = view.findViewById<TextView>(R.id.txtNamePersongemCard)

    fun bind(character: CharacterModel) {
        nome.text = character.nome

        val imagePath = character.thumbnail?.getImagePath()
        Picasso.get()
            .load(imagePath)
            .into(imagem)
    }
}