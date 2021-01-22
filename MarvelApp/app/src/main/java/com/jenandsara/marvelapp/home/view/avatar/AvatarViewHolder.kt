package com.jenandsara.marvelapp.home.view.avatar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.squareup.picasso.Picasso

class AvatarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imagem = view.findViewById<ImageView>(R.id.avatarPersonagem)
    private val nome = view.findViewById<TextView>(R.id.txtNameAvatar)

    fun bind(character: CharacterModel) {
        nome.text = character.nome

        val imagePath = character.thumbnail?.getImagePath()
        Picasso.get().load(imagePath).into(imagem)
    }

}
