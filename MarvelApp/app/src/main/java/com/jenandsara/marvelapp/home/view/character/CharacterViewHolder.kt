package com.jenandsara.marvelapp.home.view.character

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity
import com.squareup.picasso.Picasso

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imagem = view.findViewById<ImageView>(R.id.cardPersonagem)
    private val nome = view.findViewById<TextView>(R.id.txtNamePersongemCard)
    val _toggleFavorita = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleFavoritar)
    val _btnFavorita = view.findViewById<MaterialButton>(R.id.btnFavoritar)

    fun bind(character: CharacterEntity) {
        nome.text = character.name

        val imagePath = character.imgUrl
        Picasso.get()
            .load(imagePath)
            .into(imagem)
    }
}