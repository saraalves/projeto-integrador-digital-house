package com.example.marvelapp.favoritos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.detalhes.view.DetalhesActivity
import com.example.marvelapp.home.model.PersonagemModel
import com.example.marvelapp.home.view.HomeFragment
import com.squareup.picasso.Picasso

class FavoritosFragment : Fragment() {

    private lateinit var _view: View

    private val _imagem = arguments?.getInt(HomeFragment.KEY_IMAGEM)
    private val _nome = arguments?.getString(HomeFragment.KEY_NOME)
    private val _id = arguments?.getInt(HomeFragment.KEY_ID)

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

        val listaFavoritos = mutableListOf<PersonagemModel>()

        try {
            val newFavorito = PersonagemModel(_id!!, _nome!!, _imagem!!)
            listaFavoritos.add(newFavorito)
        } catch (e: Exception){
            Toast.makeText(view.context, "ERRO", Toast.LENGTH_SHORT).show()
        }

        val favoritosAdapter = FavoritosAdapter(listaFavoritos){
            val intent = Intent(view.context, DetalhesActivity::class.java)
            startActivity(intent)
        }

        favorito.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = favoritosAdapter
        }
    }
}