package com.jenandsara.marvelapp.favoritos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.jenandsara.marvelapp.favoritos.view.FavoritosViewHolder
import com.jenandsara.marvelapp.home.model.PersonagemModel

class FavoritosAdapter(
    private var _personagem: MutableList<PersonagemModel>,
    private val listener: (PersonagemModel) -> Unit
) : RecyclerView.Adapter<FavoritosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_favoritos, parent, false)

        return FavoritosViewHolder(view)
    }

    override fun getItemCount() = _personagem.size

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val item = _personagem[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }
}