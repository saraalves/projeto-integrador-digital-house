package com.jenandsara.marvelapp.v2.presentation.favoritos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.v2.data.datalocal.characterdatabase.CharacterEntity
import com.jenandsara.marvelapp.v2.presentation.character.IGetCharacterClick

class FavoritosAdapter(
    private var _personagem: MutableList<CharacterEntity>,
    private val getCharacterClick: IGetCharacterClick
) : RecyclerView.Adapter<FavoritosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_favoritos, parent, false)

        return FavoritosViewHolder(view, parent.context)
    }

    override fun getItemCount() = _personagem.size

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val item = _personagem[position]
        holder.bind(item, getCharacterClick)
    }
}