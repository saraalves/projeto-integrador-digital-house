package com.jenandsara.marvelapp.v2.presentation.stories

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.v2.domain.stories.model.StoriesModel

class StoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _chipNome = view.findViewById<TextView>(R.id.txtStoriesChip)

    fun bind(storiesModel: StoriesModel) {
        _chipNome.text = storiesModel.title
    }

}