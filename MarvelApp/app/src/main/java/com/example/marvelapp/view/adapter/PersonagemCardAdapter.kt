package com.example.marvelapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.model.PersonagemModel

class PersonagemCardAdapter(private var personagem: MutableList<PersonagemModel>) : RecyclerView.Adapter<PersonagemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonagemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_personagem, parent, false)

        return PersonagemViewHolder(view)
    }

    override fun getItemCount() = personagem.size

    override fun onBindViewHolder(holder: PersonagemViewHolder, position: Int) {
        val item = personagem[position]

        holder.bind(item)
    }
}