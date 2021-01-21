package com.jenandsara.marvelapp.home.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.offline.entity.CharacterEntity
import com.squareup.picasso.Picasso

class AvatarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imagem = view.findViewById<ImageView>(R.id.avatarPersonagem)
    private val nome = view.findViewById<TextView>(R.id.txtNameAvatar)

    fun bind(character: CharacterEntity) {
        nome.text = character.nome

        val imagePath = character.imgPath
        Picasso.get().load(imagePath).into(imagem)
    }

}
