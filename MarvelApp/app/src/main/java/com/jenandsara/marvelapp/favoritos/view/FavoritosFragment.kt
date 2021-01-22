package com.jenandsara.marvelapp.favoritos.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.detalhes.view.DetalhesActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel

class FavoritosFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _favoritosAdapter: FavoritosAdapter

    private var _listaFavoritos = mutableListOf<CharacterModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listaFavorito(view)
        favoritar()
    }

    private fun listaFavorito(view: View) {
        _view = view

        val favorito = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)
        val manager = GridLayoutManager(view.context, 2)

        _listaFavoritos
        _favoritosAdapter = FavoritosAdapter(_listaFavoritos) {
            val intent = Intent(view.context, DetalhesActivity::class.java)
            startActivity(intent)
        }

        favorito.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _favoritosAdapter
        }
    }

    private fun favoritar() {
        view?.findViewById<MaterialButton>(R.id.btnFavoritarFavoritos)?.setIconResource(R.drawable.ic_baseline_favorite_24)
        val toggleFavoritarFavoritos = view?.findViewById<MaterialButtonToggleGroup>(R.id.toggleFavoritarFavoritos)
        toggleFavoritarFavoritos?.addOnButtonCheckedListener { _, _, isChecked ->
            if(isChecked) {
                view?.findViewById<MaterialButton>(R.id.btnFavoritarFavoritos)?.setIconResource(R.drawable.ic_favorite_gray_24)
            } else view?.findViewById<MaterialButton>(R.id.btnFavoritarFavoritos)?.setIconResource(R.drawable.ic_baseline_favorite_24)
        }
    }

}