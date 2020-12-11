package com.example.marvelapp.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.character.model.CharacterModel
import com.example.marvelapp.home.model.PersonagemModel

class CharacterAdapter(
    private val _personagens: MutableList<CharacterModel>,
    private val _listener: (CharacterModel) -> Unit
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
