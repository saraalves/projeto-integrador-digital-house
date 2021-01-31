package com.jenandsara.marvelapp.home.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.character.repository.CharacterRepository
import com.jenandsara.marvelapp.character.viewmodel.CharactersViewModel
import com.jenandsara.marvelapp.detalhes.view.DetalhesActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.comics.repository.ComicRepository
import com.jenandsara.marvelapp.comics.viewmodel.ComicViewModel
import com.jenandsara.marvelapp.detalhes.viewmodel.DetalhesViewModel
import com.jenandsara.marvelapp.favoritos.datalocal.database.AppDatabase
import com.jenandsara.marvelapp.home.view.avatar.AvatarAdapter
import com.jenandsara.marvelapp.home.view.character.CharacterAdapter
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository
import com.jenandsara.marvelapp.favoritos.viewmodel.FavoriteViewModel
import kotlin.properties.Delegates

class HomeFragment(private val onlyFavorites: Boolean = false) : Fragment(), IGetCharacterClick {

    private lateinit var _view: View
    private lateinit var _viewModel: CharactersViewModel
    private lateinit var _favoritosViewModel: FavoriteViewModel
    private lateinit var _comicViewModel: ComicViewModel

    private lateinit var _characterAdapter: CharacterAdapter
    private lateinit var _avatarAdapter: AvatarAdapter
    private var position by Delegates.notNull<Int>()

    private var _character = mutableListOf<CharacterModel>()
    private var _recomendados = mutableListOf<CharacterModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkConectividade()) {

            _view = view

            val manager = LinearLayoutManager(view.context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            val avatar = view.findViewById<RecyclerView>(R.id.recyclerAvatar)

            val viewGridManager = GridLayoutManager(view.context, 2)
            val recyclerViewCard = view.findViewById<RecyclerView>(R.id.recyclerCard)

            val btnFavoritar = _view.findViewById<MaterialButton>(R.id.btnFavoritar)

            comicViewModelProvider()
            viewModelProvider()
            localViewModelProvider()

            getList(_character)

            _characterAdapter = CharacterAdapter(_character, this)

            setupNavigationAvatar()

            setupRecyclerViewAvatar(avatar, manager)
            setupRecyclerViewCard(recyclerViewCard, viewGridManager)


            searchByName(_view, _character)

            showLoading(true)
            setScrollView()

        } else {
            view.findViewById<ConstraintLayout>(R.id.ctlTeste).visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        getRecomended()
        update(_character)
    }

    private fun setupRecyclerViewCard(
        recyclerView: RecyclerView?,
        viewGridManager: GridLayoutManager
    ) {
        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = viewGridManager
            adapter = _characterAdapter
        }
    }

    private fun setupRecyclerViewAvatar(
        recyclerView: RecyclerView?,
        viewLayoutManager: LinearLayoutManager
    ) {
        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = viewLayoutManager
            adapter = _avatarAdapter
        }
    }

    private fun setupNavigation() {
        Intent(view?.context, DetalhesActivity::class.java).apply {
            bundleOf("ID" to _character[position].id)
            bundleOf("NOME" to _character[position].nome)
            bundleOf("DESCRIÇÃO" to _character[position].descricao)
            bundleOf("IMAGEM" to _character[position].thumbnail?.getImagePath())
            startActivity(this)
        }
    }

    private fun setupNavigationAvatar() {
        _avatarAdapter = AvatarAdapter(_recomendados) {
            val intent = Intent(view?.context, DetalhesActivity::class.java)
            intent.putExtra("ID", it.id)
            intent.putExtra("NOME", it.nome)
            intent.putExtra("DESCRIÇÃO", it.descricao)
            intent.putExtra("IMAGEM", it.thumbnail?.getImagePath())
            startActivity(intent)
        }
    }

    private fun update(list: List<CharacterModel>){
        list.forEach {
            _favoritosViewModel.isFavorite(it.id).observe(viewLifecycleOwner) { b: Boolean ->
                it.isFavorite = b
            }
        }

    }

    private fun getList(list: List<CharacterModel>) {
        _viewModel.getList().observe(viewLifecycleOwner) { list1 ->
            list?.let {
                _favoritosViewModel.setFavoriteCharacter(list1)
                _character.addAll(list1)
            }
            _characterAdapter.notifyDataSetChanged()
            showLoading(false)
        }
    }

    private fun getListAvatar() {
        _viewModel.getList().observe(viewLifecycleOwner) {
            _recomendados.addAll(it)
            _avatarAdapter.notifyDataSetChanged()
            showLoading(false)
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

    private fun setScrollViewAvatar() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerAvatar)
        recyclerView?.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val target = recyclerView.layoutManager as LinearLayoutManager?
                    val totalItemCount = target!!.itemCount
                    val lastVisible = target.findLastVisibleItemPosition()
                    val lastItem = lastVisible + 6 >= totalItemCount

                    if (totalItemCount > 0 && lastItem) {
                        showLoading(true)
                        _viewModel.nextPage().observe({ lifecycle }, {
                            _character.addAll(it)
                            _avatarAdapter.notifyDataSetChanged()
                            showLoading(false)
                        })
                    }
                }
            })
        }
    }

    private fun setScrollView() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerCard)
        recyclerView?.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val target = recyclerView.layoutManager as GridLayoutManager?
                    val totalItemCount = target!!.itemCount
                    val lastVisible = target.findLastVisibleItemPosition()
                    val lastItem = lastVisible + 6 >= totalItemCount

                    if (totalItemCount > 0 && lastItem) {
                        showLoading(true)
                        _viewModel.nextPage().observe({ lifecycle }, {
                            _character.addAll(it)
                            _characterAdapter.notifyDataSetChanged()
                            showLoading(false)
                        })
                    }
                }
            })
        }
    }

    private fun getListSearcheByName(list: List<CharacterModel>) {
        _viewModel.getList().observe(viewLifecycleOwner) {
            list?.let { _character.addAll(it) }
            _characterAdapter.notifyDataSetChanged()
            showLoading(false)
        }
    }

    private fun searchByName(view: View, list: MutableList<CharacterModel>) {

        val searchView = view.findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                _viewModel.searchByName(query).observe(viewLifecycleOwner) {
                    _character.clear()
                    getListSearcheByName(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _character.clear()
                    getListSearcheByName(_viewModel.initialList())
                } else {
                    _viewModel.searchByStartsWith(newText).observe(viewLifecycleOwner) {
                        _character.clear()
                        getListSearcheByName(it)
                    }
                }
                return false
            }
        })
    }

    private fun getRecomended() {
        _favoritosViewModel.getFavoriteCharacterLocal().observe(viewLifecycleOwner) {
            if (it.isNotEmpty() && it.size > 1) {
                _viewModel.getRandomFavorite(it).observe(viewLifecycleOwner) { it1 ->
                    _comicViewModel.getComicList(it1).observe(viewLifecycleOwner) { list ->
                        _viewModel.getRecomended(list).observe(viewLifecycleOwner) { it2 ->
                            _recomendados.addAll(it2)
                            _avatarAdapter.notifyDataSetChanged()
                            showLoading(false)
                        }
                    }
                }
            } else {
                getListAvatar()
            }
        }
    }

    /*  private fun getCharacters() {
          if (onlyFavorites) {
              _viewModel.getFavoriteCharacter().observe(viewLifecycleOwner) {
                  _character.addAll(it)
                  _characterAdapter.notifyDataSetChanged()
              }
          } else {
              _viewModel.getCharacters().observe(viewLifecycleOwner) {
                  _character.addAll(it)
                  _characterAdapter.notifyDataSetChanged()
              }
          }
      }*/

    /*private fun updateCharacter() {
        if (!onlyFavorites) {
            _viewModel.updateFavoriteCharacters(_character)
                .observe(viewLifecycleOwner) {
                    _characterAdapter.notifyDataSetChanged()
                }
        } else {
            _viewModel.updateFavoriteCharacters(_character)
                .observe(viewLifecycleOwner) {
                    _character.removeAll(it)
                    _characterAdapter.notifyDataSetChanged()
                }
        }
    }*/

    override fun getCharacterClick(position: Int) {
        Intent(view?.context, DetalhesActivity::class.java).apply {
            putExtra("ID", _character[position].id)
            putExtra("NOME", _character[position].nome)
            putExtra("DESCRIÇÃO", _character[position].descricao)
            putExtra("IMAGEM", _character[position].thumbnail?.getImagePath())
            startActivity(this)
        }
    }

    override fun getCharacterFavoriteClick(position: Int) {
        _favoritosViewModel.isFavorite(_character[position].id)
            .observe(viewLifecycleOwner) { isFavorite ->
                adicionarFavoritos(isFavorite, position)
                _character[position].isFavorite = !_character[position].isFavorite
            }
    }

    private fun adicionarFavoritos(isFavorite: Boolean, position: Int) {
        if (isFavorite) {
            Toast.makeText(view?.context, "Favorito já adicionado", Toast.LENGTH_SHORT).show()
        } else {
            val character = _character[position]
            _favoritosViewModel.addCharacter(
                character.nome,
                character.id,
                character.descricao,
                character.thumbnail!!.getImagePath()
            )
                .observe(viewLifecycleOwner) {
                    Log.d(
                        "TAG CHARACTER FRAGMENT",
                        "getCharacterFavoriteClick() - addCharacter"
                    )
                    if (it) {
                        _character[position].isFavorite = true
                        _characterAdapter.notifyItemChanged(position)
                    }
                }
        }
    }

//    private fun removerAdicionar(isFavorite: Boolean, position: Int) {
//        if (isFavorite) {
//            _favoritosViewModel.deleteCharacter(_character[position].id)
//                .observe(viewLifecycleOwner) {
//                    if (it) {
//                        _character[position].isFavorite = false
//                        if (onlyFavorites) {
//                            _character.removeAt(position)
//                            _characterAdapter.notifyDataSetChanged()
//                        } else {
//                            _characterAdapter.notifyItemChanged(position)
//                        }
//                    }
//                }
//        } else {
//            val character = _character[position]
//            _favoritosViewModel.addCharacter(
//                character.nome,
//                character.id,
//                character.descricao,
//                character.thumbnail!!.getImagePath()
//            )
//                .observe(viewLifecycleOwner) {
//                    Log.d(
//                        "TAG CHARACTER FRAGMENT",
//                        "getCharacterFavoriteClick() - addCharacter"
//                    )
//                    if (it) {
//                        _character[position].isFavorite = true
//                        _characterAdapter.notifyItemChanged(position)
//                    }
//                }
//        }
//    }

    private fun checkConectividade(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
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

    private fun comicViewModelProvider() {
        _comicViewModel = ViewModelProvider(
            this,
            ComicViewModel.ComicsViewModelFactory(ComicRepository())
        ).get(ComicViewModel::class.java)
    }
}
