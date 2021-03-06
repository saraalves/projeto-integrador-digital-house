package com.jenandsara.marvelapp.detalhes.view.stories

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.stories.model.StoriesModel
import com.squareup.picasso.Picasso

class StoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _chipNome = view.findViewById<TextView>(R.id.txtStoriesChip)

    fun bind(storiesModel: StoriesModel) {
        _chipNome.text = storiesModel.title
    }

}