package com.jenandsara.marvelapp.v2.presentation.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.v2.domain.character.model.CharacterModel

class AvatarAdapter(
    private var avatar: MutableList<CharacterModel>,
    private val listener: (CharacterModel) -> Unit
) : RecyclerView.Adapter<AvatarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)

        return AvatarViewHolder(view)
    }

    override fun getItemCount() = avatar.size

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        val item = avatar[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }
}