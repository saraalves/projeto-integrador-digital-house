package com.example.marvelapp.detalhes.view.stories

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.stories.model.StoriesModel
import com.squareup.picasso.Picasso

class StoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _chipNome = view.findViewById<TextView>(R.id.txtChip)
    private var _chipImagem = view.findViewById<ImageView>(R.id.imgChip)

    fun bind(storiesModel: StoriesModel) {

        _chipNome.text = storiesModel.title

        val imagePath = storiesModel.thumbnail?.getImagePath()
        Picasso.get().load(imagePath).into(_chipImagem)
    }

}