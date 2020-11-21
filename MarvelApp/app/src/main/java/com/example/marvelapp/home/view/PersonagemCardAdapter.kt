package com.example.marvelapp.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.home.model.PersonagemModel

class PersonagemCardAdapter(private var personagem: MutableList<PersonagemModel>, private val listener: (PersonagemModel) -> Unit) : RecyclerView.Adapter<PersonagemCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonagemCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_personagem, parent, false)

        return PersonagemCardViewHolder(view)
    }

    override fun getItemCount() = personagem.size

    override fun onBindViewHolder(holder: PersonagemCardViewHolder, position: Int) {
        val item = personagem[position]
        holder.bind(item)

        holder.itemView.setOnClickListener{ listener(item)}
    }
}