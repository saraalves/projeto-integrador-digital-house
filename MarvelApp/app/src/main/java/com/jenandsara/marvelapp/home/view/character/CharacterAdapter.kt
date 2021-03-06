package com.jenandsara.marvelapp.home.view.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.home.view.IGetCharacterClick

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
