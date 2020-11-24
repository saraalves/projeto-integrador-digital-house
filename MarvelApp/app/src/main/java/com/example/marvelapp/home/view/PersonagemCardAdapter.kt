package com.example.marvelapp.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.home.model.PersonagemModel
import com.google.android.material.snackbar.Snackbar

class PersonagemCardAdapter(
    private val _personagens: MutableList<PersonagemModel>,
    private val _listener: (PersonagemModel) -> Unit
) :
    RecyclerView.Adapter<PersonagemCardViewHolder>() {

    val listaFavoritos = mutableListOf<PersonagemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonagemCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_personagem, parent, false)
        return PersonagemCardViewHolder(view)
    }

    override fun getItemCount() = _personagens.size

    override fun onBindViewHolder(holder: PersonagemCardViewHolder, position: Int) {
        val item = _personagens[position]
        holder.bind(item)

        holder.itemView.findViewById<ImageView>(R.id.imgFavorit).setOnClickListener {
            holder.itemView.findViewById<ImageView>(R.id.imgFavorit).visibility = View.GONE
            holder.itemView.findViewById<ImageView>(R.id.imgFavoritadoHome).visibility =
                View.VISIBLE
            listaFavoritos.add(item)
        }

        holder.itemView.findViewById<ImageView>(R.id.imgFavoritadoHome).setOnClickListener {
            holder.itemView.findViewById<ImageView>(R.id.imgFavorit).visibility = View.VISIBLE
            holder.itemView.findViewById<ImageView>(R.id.imgFavoritadoHome).visibility = View.GONE
            listaFavoritos.remove(item)
        }

        holder.itemView.setOnClickListener { _listener(item) }
    }
}
