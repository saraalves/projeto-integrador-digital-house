package com.jenandsara.marvelapp.detalhes.view.stories

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.data.model.stories.StoriesResponse

class StoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _chipNome = view.findViewById<TextView>(R.id.txtStoriesChip)

    fun bind(storiesResponse: StoriesResponse) {
        _chipNome.text = storiesResponse.title
    }

}