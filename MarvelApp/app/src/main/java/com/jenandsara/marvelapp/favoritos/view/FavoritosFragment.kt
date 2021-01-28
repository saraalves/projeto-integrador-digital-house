package com.jenandsara.marvelapp.favoritos.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.jenandsara.marvelapp.home.view.HomeFragment
import com.jenandsara.marvelapp.home.view.IGetCharacterClick

class FavoritosFragment(private val onlyFavorites: Boolean = false) : Fragment(),
    IGetCharacterClick {

    private lateinit var _view: View
    private lateinit var _favoritosAdapter: FavoritosAdapter
    private lateinit var _characterViewModel: CharactersViewModel
    private var onPause = false
    private var _listaFavoritos = mutableListOf<CharacterModel>()
    private var _listaFavoritosLocal = mutableListOf<CharacterEntity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

//    override fun onPause() {
//        super.onPause()
//        onPause = true
//    }

//    override fun onResume() {
//        super.onResume()
//        if (onPause) {
//            onPause = false
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        val favoritoRecycler = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)
        val viewGridManager = GridLayoutManager(view.context, 2)
        _favoritosAdapter = FavoritosAdapter(_listaFavoritosLocal, this)

        setupRecyclerViewCard(favoritoRecycler, viewGridManager)
        viewModelProvider()
        showLoading(true)
        getCharacters()
        updateCharacter()

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
        _characterViewModel.getFavoriteCharacterLocal().observe(viewLifecycleOwner) {
            _listaFavoritosLocal.addAll(it)
            _favoritosAdapter.notifyDataSetChanged()
        }
        showLoading(false)
    }

    override fun getCharacterClick(position: Int) {
        Intent(view?.context, DetalhesActivity::class.java).apply {
            putExtra("ID", _listaFavoritosLocal[position].id)
            putExtra("NOME", _listaFavoritosLocal[position].nome)
            putExtra("DESCRIÇÃO", _listaFavoritosLocal[position].descricao)
            putExtra("IMAGEM", _listaFavoritosLocal[position].imgPath)
            startActivity(this)
        }
    }

    override fun getCharacterFavoriteClick(position: Int) {
        _characterViewModel.isFavorite(_listaFavoritosLocal[position].id)
            .observe(viewLifecycleOwner) { isFavorite ->
                if (isFavorite) {
                    _characterViewModel.deleteCharacter(_listaFavoritosLocal[position].id)
                        .observe(viewLifecycleOwner) {
                            Log.d(
                                "TAG CHARACTER FRAGMENT",
                                "getCharacterFavoriteClick() - deleteCharacter"
                            )
                            if (it) {
                                _listaFavoritosLocal[position].isFavorite = false
                                Log.d(
                                    "TAG CHARACTER FRAGMENT",
                                    "getCharacterFavoriteClick() - deleteCharacter"
                                )
                                if (onlyFavorites) {
                                    _listaFavoritosLocal.removeAt(position)
                                    _favoritosAdapter.notifyDataSetChanged()
                                    Log.d(
                                        "TAG CHARACTER FRAGMENT",
                                        "getCharacterFavoriteClick() - onlyFavorites"
                                    )
                                } else {
                                    _favoritosAdapter.notifyItemChanged(position)
                                    Log.d(
                                        "TAG CHARACTER FRAGMENT",
                                        "getCharacterFavoriteClick() - deleteCharacter"
                                    )
                                }
                            }
                        }
                } else {
                    _characterViewModel.getFavoriteCharacterLocal()
                        .observe(viewLifecycleOwner) {
                            Log.d("TAG CHARACTER FRAGMENT", "getCharacterFavoriteClick() - addCharacter")
                            _listaFavoritosLocal[position].isFavorite = true
                            _favoritosAdapter.notifyItemChanged(position)
                            Log.d("TAG CHARACTER FRAGMENT", "getCharacterFavoriteClick() - item: $it")
                            Log.d("TAG CHARACTER FRAGMENT", "getCharacterFavoriteClick() - item: $position $isFavorite")
                            Log.d("TAG CHARACTER FRAGMENT", "getCharacterFavoriteClick() - item: $_listaFavoritosLocal")
                        }
                }

                _listaFavoritosLocal[position].isFavorite =
                    !_listaFavoritosLocal[position].isFavorite
            }
    }

    private fun updateCharacter() {
        if (!onlyFavorites) {
            _characterViewModel.updateFavoriteCharactersLocal(_listaFavoritosLocal)
                .observe(viewLifecycleOwner) {
                    _favoritosAdapter.notifyDataSetChanged()
                    showLoading(false)
                }

        } else {
            _characterViewModel.updateFavoriteCharactersLocal(_listaFavoritosLocal)
                .observe(viewLifecycleOwner) {
                    _listaFavoritosLocal.removeAll(it)
                    _favoritosAdapter.notifyDataSetChanged()
                }
        }
    }

    private fun viewModelProvider() {
        _characterViewModel = ViewModelProvider(
            this,
            CharactersViewModel.CharactersViewModelFactory(
                CharacterRepository(),
                CharacterLocalRepository(AppDatabase.getDatabase(_view.context).characterDAO())
            )
        ).get(CharactersViewModel::class.java)
    }
}