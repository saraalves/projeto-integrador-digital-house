package com.jenandsara.marvelapp.presentation.adapter.comics

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.data.model.comics.ComicsResponse
import com.squareup.picasso.Picasso

class   ComicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _chipNome = view.findViewById<TextView>(R.id.txtChip)
    private var _chipImagem = view.findViewById<ImageView>(R.id.imgChip)

    fun bind(comicsResponse: ComicsResponse) {

        _chipNome.text = comicsResponse.title

        val imagePath = comicsResponse.thumbnail?.getImagePath()
        Picasso.get().load(imagePath).into(_chipImagem)
    }

}