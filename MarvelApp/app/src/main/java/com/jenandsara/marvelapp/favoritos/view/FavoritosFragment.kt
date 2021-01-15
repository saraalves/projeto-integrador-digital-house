package com.jenandsara.marvelapp.favoritos.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.detalhes.view.DetalhesActivity
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.db.AppDatabase
import com.jenandsara.marvelapp.datalocal.repository.LocalCharacterRepository
import com.jenandsara.marvelapp.datalocal.viewmodel.LocalCharacterViewModel

class FavoritosFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _favoritosAdapter: FavoritosAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        listaFavorito(_view)
        getFavoritos()
    }

    private fun listaFavorito(view: View) {
        _view = view

        val favorito = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)
        val manager = GridLayoutManager(view.context, 2)

        _favoritosAdapter = FavoritosAdapter() {
            val intent = Intent(view.context, DetalhesActivity::class.java)
            startActivity(intent)
        }

        favorito.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _favoritosAdapter
        }
    }

    private fun getFavoritos(){

        val localViewModel = ViewModelProvider(this, LocalCharacterViewModel.LocalCharacterViewModelFactory(
            LocalCharacterRepository((AppDatabase.getDatabase(_view.context).characterDao()))
        )).get(LocalCharacterViewModel::class.java)
    }
}