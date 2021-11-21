package com.jenandsara.marvelapp.v2.presentation.comics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.v2.domain.comics.model.ComicsModel

class ComicsAdapter (
    private var _comics: MutableList<ComicsModel>, private val _listener: (ComicsModel) -> Unit
) : RecyclerView.Adapter<ComicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chip_item, parent, false)

        return ComicsViewHolder(view)
    }

    override fun getItemCount() = _comics.size

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        val item = _comics[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { _listener(item) }
    }
}