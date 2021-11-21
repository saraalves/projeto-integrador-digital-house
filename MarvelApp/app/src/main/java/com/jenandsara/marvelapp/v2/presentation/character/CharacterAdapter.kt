package com.jenandsara.marvelapp.v2.presentation.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.v2.domain.character.model.CharacterModel

class CharacterAdapter(
    private val _personagens: MutableList<CharacterModel>,
//    private val _listener: (CharacterModel) -> Unit,
    private val getCharacterClick: IGetCharacterClick
) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_personagem, parent, false)
        return CharacterViewHolder(view, parent.context)
    }

    override fun getItemCount() = _personagens.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = _personagens[position]
        holder.bind(item, getCharacterClick)
//        holder.itemView.setOnClickListener { _listener(item) }
    }
}
