package com.jenandsara.marvelapp.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.offline.entity.CharacterEntity

class CharacterAdapter(
    private val _personagens: MutableList<CharacterEntity>,
    private val _listener: (CharacterEntity) -> Unit
) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_personagem, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount() = _personagens.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = _personagens[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { _listener(item) }
    }
}
