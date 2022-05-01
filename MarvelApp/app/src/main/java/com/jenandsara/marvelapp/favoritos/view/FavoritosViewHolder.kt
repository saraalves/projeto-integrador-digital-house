package com.jenandsara.marvelapp.favoritos.view

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity
import com.jenandsara.marvelapp.home.view.IGetCharacterClick
import com.squareup.picasso.Picasso

class FavoritosViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

    private val _imagem = view.findViewById<ImageView>(R.id.cardFavoritado)
    private val _nome = view.findViewById<TextView>(R.id.txtNamePersongemFavoritado)
    private val toggleFavoritar = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleFavoritarFavoritos)
    private val btnFavoritar = view.findViewById<MaterialButton>(R.id.btnFavoritarFavoritos)

    fun bind(personagemModel: CharacterEntity, getCharacterClick: IGetCharacterClick) {

        _nome.text = personagemModel.nome

        btnFavoritar.isChecked = personagemModel.isFavorite

            Picasso.get()
            .load(personagemModel.imgPath)
            .into(_imagem)

        favoritar()
        btnFavoritar.setOnClickListener {
            getCharacterClick.getCharacterFavoriteClick(adapterPosition)
        }

        val gestureDetector =
            GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    getCharacterClick.getCharacterClick(adapterPosition)
                    return super.onSingleTapConfirmed(e)
                }

                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    getCharacterClick.getCharacterFavoriteClick(adapterPosition)
                    return super.onDoubleTap(e)
                }
            })

        itemView.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            v.performClick()
            true
        }
    }

    private fun favoritar() {
        val toggleFavoritar = toggleFavoritar
        toggleFavoritar?.addOnButtonCheckedListener { _, _, isChecked ->
            if (isChecked) {
                btnFavoritar
                    ?.setIconResource(R.drawable.ic_favorite_gray_24)
            } else {
                btnFavoritar
                    ?.setIconResource(R.drawable.ic_baseline_favorite_24)
            }

        }
    }
}
