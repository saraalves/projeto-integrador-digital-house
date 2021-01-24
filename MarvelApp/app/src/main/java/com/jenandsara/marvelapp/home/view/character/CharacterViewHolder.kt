package com.jenandsara.marvelapp.home.view.character

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.home.view.IGetCharacterClick
import com.squareup.picasso.Picasso

class CharacterViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

    private val imagem = view.findViewById<ImageView>(R.id.cardPersonagem)
    private val nome = view.findViewById<TextView>(R.id.txtNamePersongemCard)
    private val toggleFavoritar = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleFavoritar)
    private val btnFavoritar = view.findViewById<MaterialButton>(R.id.btnFavoritar)

    fun bind(character: CharacterModel, getCharacterClick: IGetCharacterClick) {
        nome.text = character.nome

        val imagePath = character.thumbnail?.getImagePath()
        Picasso.get()
            .load(imagePath)
            .into(imagem)

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
                    ?.setIconResource(R.drawable.ic_baseline_favorite_24)
            } else {
                btnFavoritar
                    ?.setIconResource(R.drawable.ic_favorit_24)
            }

        }
    }
}