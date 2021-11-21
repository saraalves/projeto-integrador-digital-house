package com.jenandsara.marvelapp.v2.presentation.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.v2.domain.stories.model.StoriesModel

class StoriesAdapter (
    private var _stories: MutableList<StoriesModel>, private val _listener: (StoriesModel) -> Unit
) : RecyclerView.Adapter<StoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stories_chip_item, parent, false)

        return StoriesViewHolder(view)
    }

    override fun getItemCount() = _stories.size

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val item = _stories[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { _listener(item) }
    }
}