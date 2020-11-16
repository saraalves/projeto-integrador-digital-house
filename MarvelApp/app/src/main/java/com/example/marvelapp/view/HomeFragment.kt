package com.example.marvelapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.model.PersonagemModel
import com.example.marvelapp.view.adapter.AvatarAdapter
import com.example.marvelapp.view.adapter.PersonagemCardAdapter
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    lateinit var _view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaAvatar(view)
        listaCard(view)
    }

    private fun listaAvatar(view: View) {
        _view = view

        val avatar = view.findViewById<RecyclerView>(R.id.recyclerAvatar)
        val manager = LinearLayoutManager(view.context)
        manager.orientation = LinearLayoutManager.HORIZONTAL

        val listaPersonagens = mutableListOf<PersonagemModel>()
        val listaAdapter = adapterAvatar()

        avatar.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = listaAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }

    private fun listaCard(view: View) {
        _view = view

        val avatar = view.findViewById<RecyclerView>(R.id.recyclerCard)
        val manager = GridLayoutManager(view.context, 2)

        val listaPersonagens = mutableListOf<PersonagemModel>()
        val listaAdapter = adapterCard()

        avatar.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = listaAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun adapterAvatar(): AvatarAdapter{
        return AvatarAdapter(
            arrayListOf(
                PersonagemModel(
                    1,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    2,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    3,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                )
            )
        )
    }

    private fun adapterCard(): PersonagemCardAdapter{
        return PersonagemCardAdapter(
            arrayListOf(
                PersonagemModel(
                    1,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                ),
                PersonagemModel(
                    2,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                ),
                PersonagemModel(
                    3,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_card
                )
            )
        )
    }

}