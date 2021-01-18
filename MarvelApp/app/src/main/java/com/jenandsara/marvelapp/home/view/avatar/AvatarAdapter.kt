package com.jenandsara.marvelapp.home.view.avatar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity

class AvatarAdapter(
    private val listener: (CharacterEntity) -> Unit
) : RecyclerView.Adapter<AvatarViewHolder>() {

    private var avatar = mutableListOf<CharacterEntity>()

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

    fun setList(character: CharacterEntity){
        avatar.add(character)
    }
}