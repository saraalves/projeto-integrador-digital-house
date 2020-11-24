package com.example.marvelapp.favoritos.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.detalhes.view.DetalhesActivity
import com.example.marvelapp.home.model.PersonagemModel
import com.example.marvelapp.home.view.HomeFragment


class FavoritosFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _favoritosAdapter: FavoritosAdapter

    private  var _listaFavoritos = mutableListOf<PersonagemModel>(
        PersonagemModel(20, "CAPITÃ MARVEL", R.drawable.img_card),
        PersonagemModel(21, "CAPITÃ MARVEL", R.drawable.img_card),
        PersonagemModel(22, "CAPITÃ MARVEL", R.drawable.img_card)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listaFavorito(view)
    }

    private fun listaFavorito(view: View) {
        _view = view

        val favorito = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)
        val manager = GridLayoutManager(view.context, 2)

        _listaFavoritos
        _favoritosAdapter = FavoritosAdapter(_listaFavoritos){
            val intent = Intent(view.context, DetalhesActivity::class.java)
            startActivity(intent)
        }

        favorito.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _favoritosAdapter
        }
    }

}