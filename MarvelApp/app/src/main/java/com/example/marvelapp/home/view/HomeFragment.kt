package com.example.marvelapp.home.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.home.model.PersonagemModel
import com.example.marvelapp.detalhes.view.DetalhesActivity

class HomeFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _info: PersonagemModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        }
    }

    private fun adapterAvatar(): AvatarAdapter {
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
        ) {
            val intent = Intent(view!!.context, DetalhesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun adapterCard(): PersonagemCardAdapter {
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
        ) {
            _info = it
            val intent = Intent(view!!.context, DetalhesActivity::class.java)
            startActivity(intent)

            _view.findViewById<ImageView>(R.id.imgFavorit).setOnClickListener {
                bundleOf(
                    KEY_ID to _info.id,
                    KEY_IMAGEM to _info.imagem,
                    KEY_NOME to _info.nome
                )
           }
        }
    }

    companion object{
        val KEY_ID = "ID"
        val KEY_IMAGEM = "IMAGEM"
        val KEY_NOME = "NOME"
        val KEY_FAVORITO = "FAV"
    }

}