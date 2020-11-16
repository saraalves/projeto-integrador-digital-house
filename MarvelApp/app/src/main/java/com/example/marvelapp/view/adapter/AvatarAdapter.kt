package com.example.marvelapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.model.PersonagemModel

class AvatarAdapter(private var avatar: MutableList<PersonagemModel>) : RecyclerView.Adapter<AvatarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_avatar_personagem, parent, false)

        return AvatarViewHolder(view)
    }

    override fun getItemCount() = avatar.size

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        val item = avatar[position]

        holder.bind(item)
    }
}