package com.example.marvelapp.detalhes.view.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.stories.model.StoriesModel

class StoriesAdapter (
        private var _stories: MutableList<StoriesModel>, private val _listener: (StoriesModel) -> Unit
) : RecyclerView.Adapter<StoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chip_item, parent, false)

        return StoriesViewHolder(view)
    }

    override fun getItemCount() = _stories.size

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val item = _stories[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { _listener(item) }
    }
}