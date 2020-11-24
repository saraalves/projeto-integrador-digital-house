package com.example.marvelapp.favoritos.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.home.model.PersonagemModel
import com.google.android.material.snackbar.Snackbar

class FavoritosAdapter(private var _personagem: MutableList<PersonagemModel>, private val listener: (PersonagemModel) -> Unit) : RecyclerView.Adapter<FavoritosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_favoritos, parent, false)

        return FavoritosViewHolder(view)
    }

    override fun getItemCount() = _personagem.size

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val item = _personagem[position]
        holder.bind(item)

        holder.itemView.findViewById<ImageView>(R.id.imgFavoritar).setOnClickListener {
            holder.itemView.findViewById<ImageView>(R.id.imgFavoritar).visibility = View.GONE
            holder.itemView.findViewById<ImageView>(R.id.imgFavoritado).visibility = View.VISIBLE
            _personagem.add(_personagem[position])
        }

        holder.itemView.findViewById<ImageView>(R.id.imgFavoritado).setOnClickListener {
            holder.itemView.findViewById<ImageView>(R.id.imgFavoritar).visibility = View.VISIBLE
            holder.itemView.findViewById<ImageView>(R.id.imgFavoritado).visibility = View.GONE
            _personagem.remove(_personagem[position])
        }

        holder.itemView.setOnClickListener { listener(item) }
    }
}