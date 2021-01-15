package com.jenandsara.marvelapp.favoritos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity

class FavoritosAdapter( private val listener: (CharacterEntity) -> Unit
) : RecyclerView.Adapter<FavoritosViewHolder>() {

    var _characters: MutableList<CharacterEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_favoritos, parent, false)

        return FavoritosViewHolder(view)
    }

    override fun getItemCount() = _characters.size

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val item = _characters[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }


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

}