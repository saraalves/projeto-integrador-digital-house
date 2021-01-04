package com.example.marvelapp.detalhes.view.comics

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.comics.model.ComicsModel
import com.squareup.picasso.Picasso

class ComicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _chipNome = view.findViewById<TextView>(R.id.txtChip)
    private var _chipImagem = view.findViewById<ImageView>(R.id.imgChip)

    fun bind(comicsModel: ComicsModel) {

        _chipNome.text = comicsModel.title

        val imagePath = comicsModel.thumbnail?.getImagePath()
        Picasso.get().load(imagePath).into(_chipImagem)
    }

}