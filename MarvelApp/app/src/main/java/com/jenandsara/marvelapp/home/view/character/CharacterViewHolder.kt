package com.jenandsara.marvelapp.home.view.character

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.squareup.picasso.Picasso

class CharacterViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

    private val imagem = view.findViewById<ImageView>(R.id.cardPersonagem)
    private val nome = view.findViewById<TextView>(R.id.txtNamePersongemCard)
    private val favorite = view.findViewById<MaterialButton>(R.id.btnFavoritar)
    private val constraintFavorite = view.findViewById<ConstraintLayout>(R.id.ctlCardPersonagem)

    interface OnCharacterClickListener {
        fun onCharacterClick(position: Int)
        fun onCharacterFavoriteClick(position: Int)
    }

    fun bind(character: CharacterModel, onCharacterClickListener: OnCharacterClickListener) {
        nome.text = character.nome

        val imagePath = character.thumbnail?.getImagePath()
        Picasso.get()
            .load(imagePath)
            .into(imagem)

        if (character.isFavorite) {
            favorite.background = ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_red_24
            )

            constraintFavorite.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimary
                )
            )
        } else {
            favorite.background = ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_gray_24
            )
        }

        favorite.setOnClickListener {
            onCharacterClickListener.onCharacterFavoriteClick(adapterPosition)
        }
    }
}