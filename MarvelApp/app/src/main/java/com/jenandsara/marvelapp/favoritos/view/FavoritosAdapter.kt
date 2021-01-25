package com.jenandsara.marvelapp.favoritos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity
import com.jenandsara.marvelapp.home.view.IGetCharacterClick

class FavoritosAdapter(
    private var _personagem: MutableList<CharacterEntity>,
//    private val listener: (CharacterEntity) -> Unit
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
//        holder.itemView.setOnClickListener { listener(item) }
    }
}