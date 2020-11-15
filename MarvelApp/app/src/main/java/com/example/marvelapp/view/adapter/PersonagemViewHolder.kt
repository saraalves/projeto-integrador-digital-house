package com.example.marvelapp.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.model.PersonagemModel
import com.squareup.picasso.Picasso

class PersonagemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val imagem = view.findViewById<ImageView>(R.id.avatarPersonagem)
    private val nome = view.findViewById<TextView>(R.id.txtNameAvatar)

    fun bind(personagemModel: PersonagemModel) {
        nome.text = personagemModel.nome

        Picasso.get()
            .load(personagemModel.imagem)
            .into(imagem)
    }

}
