package com.jenandsara.marvelapp.home.view.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity

class CharacterAdapter(
    private val _listener: (CharacterEntity) -> Unit
) :
    RecyclerView.Adapter<CharacterViewHolder>() {

   val _localPersonagens = mutableListOf<CharacterEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_personagem, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount() = _localPersonagens.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        val item = _localPersonagens[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { _listener(item) }

        holder._toggleFavorita.addOnButtonCheckedListener { _, _, isChecked ->
            if (isChecked) {
                item.isFavorite = 1
                if (item.isFavorite == 1) {
                   holder. _btnFavorita.setIconResource(R.drawable.ic_baseline_favorite_24)
                }
            } else {
                item.isFavorite = 0
                if (item.isFavorite == 0) {
                    holder._btnFavorita.setIconResource(R.drawable.ic_favorite_gray_24)
                }
            }
        }
    }

    fun setList(character: CharacterEntity){
        _localPersonagens.add(character)
    }
}
