package com.jenandsara.marvelapp.favoritos.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.jenandsara.marvelapp.detalhes.view.DetalhesActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.tabs.TabLayout
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.character.repository.CharacterRepository
import com.jenandsara.marvelapp.character.viewmodel.CharactersViewModel
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity
import com.jenandsara.marvelapp.favoritos.datalocal.database.AppDatabase
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository
import com.jenandsara.marvelapp.favoritos.viewmodel.FavoriteViewModel
import com.jenandsara.marvelapp.home.view.HomeFragment
import com.jenandsara.marvelapp.home.view.IGetCharacterClick
import kotlin.properties.Delegates

class FavoritosFragment(private val onlyFavorites: Boolean = false) : Fragment(),
    IGetCharacterClick {

    private lateinit var _view: View
    private lateinit var _favoritosAdapter: FavoritosAdapter
    private lateinit var _viewModel: CharactersViewModel

    private lateinit var _favoritosViewModel: FavoriteViewModel

    private var _listaFavoritosLocal = mutableListOf<CharacterEntity>()
    private var position by Delegates.notNull<Int>()
    private var isFavorite = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        val favoritoRecycler = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)
        val viewGridManager = GridLayoutManager(view.context, 2)
        _favoritosAdapter = FavoritosAdapter(_listaFavoritosLocal, this)

        localViewModelProvider()
        setupRecyclerViewCard(favoritoRecycler, viewGridManager)
        viewModelProvider()
        showLoading(true)
//        getCharacters()
        //updateCharacter()

    }

    override fun onResume() {
        super.onResume()
        getCharacters()
    }

    private fun setupRecyclerViewCard(
        recyclerView: RecyclerView?,
        viewGridManager: GridLayoutManager
    ) {
        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = viewGridManager
            adapter = _favoritosAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = view?.findViewById<View>(R.id.loading)
        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
        } else {
            viewLoading?.visibility = View.GONE
        }
    }

    private fun getCharacters() {
        _favoritosViewModel.getFavoriteCharacterLocal().observe(viewLifecycleOwner) {
            _listaFavoritosLocal.clear()
            _listaFavoritosLocal.addAll(it)
            _favoritosAdapter.notifyDataSetChanged()
        }
        showLoading(false)
    }

    override fun getCharacterClick(position: Int) {
        Intent(view?.context, DetalhesActivity::class.java).apply {
            putExtra("ID", _listaFavoritosLocal[position].idAPI)
            putExtra("NOME", _listaFavoritosLocal[position].nome)
            putExtra("DESCRIÇÃO", _listaFavoritosLocal[position].descricao)
            putExtra("IMAGEM", _listaFavoritosLocal[position].imgPath)
            putExtra("FAVORITO", _listaFavoritosLocal[position].isFavorite)
            startActivity(this)
        }
    }

    override fun getCharacterFavoriteClick(position: Int) {
        _favoritosViewModel.deleteCharacter(_listaFavoritosLocal[position].idAPI)
            .observe(viewLifecycleOwner) {
                _listaFavoritosLocal.removeAt(position)
                _favoritosAdapter.notifyDataSetChanged()
            }
    }

    private fun viewModelProvider() {
        _viewModel = ViewModelProvider(
            this,
            CharactersViewModel.CharactersViewModelFactory(
                CharacterRepository()
            )
        ).get(CharactersViewModel::class.java)
    }

    private fun localViewModelProvider() {
        _favoritosViewModel = ViewModelProvider(
            this,
            FavoriteViewModel.FavoritosViewModelFactory(
                CharacterLocalRepository(AppDatabase.getDatabase(_view.context).characterDAO())
            )
        ).get(FavoriteViewModel::class.java)
    }
}