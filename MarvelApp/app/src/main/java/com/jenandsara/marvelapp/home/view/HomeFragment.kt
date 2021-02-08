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
import android.widget.TextView
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.comics.repository.ComicRepository
import com.jenandsara.marvelapp.comics.viewmodel.ComicViewModel
import com.jenandsara.marvelapp.favoritos.datalocal.database.AppDatabase
import com.jenandsara.marvelapp.home.view.avatar.AvatarAdapter
import com.jenandsara.marvelapp.home.view.character.CharacterAdapter
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository
import com.jenandsara.marvelapp.favoritos.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_home.*
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

    private val userId = Firebase.auth.currentUser?.uid

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

            comicViewModelProvider()
            viewModelProvider()
            localViewModelProvider()

            showLoading(true)
            getList(_character)

            _characterAdapter = CharacterAdapter(_character, this)

            setupNavigationAvatar()

            setupRecyclerViewAvatar(avatar, manager)
            setupRecyclerViewCard(recyclerViewCard, viewGridManager)


            searchByName(_view)

            setScrollView()
            getRecomended()

        } else {
            view.findViewById<ConstraintLayout>(R.id.ctlTeste).visibility = View.VISIBLE
            view.findViewById<ConstraintLayout>(R.id.ctlConection).visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if (!checkConectividade()) {
            view?.findViewById<ConstraintLayout>(R.id.ctlTeste)?.visibility = View.VISIBLE
            view?.findViewById<ConstraintLayout>(R.id.ctlConection)?.visibility = View.GONE
        } else {
            showLoading(true)
            getList(_character)
        }
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

    private fun update(list: List<CharacterModel>) {
        list.forEach {
            _favoritosViewModel.isFavorite(it.id, userId!!).observe(viewLifecycleOwner) { b: Boolean ->
                it.isFavorite = b
            }
        }

    }

    private fun getList(list: List<CharacterModel>) {
        _viewModel.getList().observe(viewLifecycleOwner) { list1 ->
            list?.let {
                showLoading(true)
                update(list1)
                _favoritosViewModel.setFavoriteCharacter(list1, userId!!).observe(viewLifecycleOwner) {
                    _character.clear()
                    _character.addAll(list1)
                    _characterAdapter.notifyDataSetChanged()

                }
                showLoading(false)
            }

        }

    }

    private fun getListAvatar() {
        _viewModel.getList().observe(viewLifecycleOwner) {
            _recomendados.addAll(it)
            _avatarAdapter.notifyDataSetChanged()
        }
    }


    private fun showLoading(isLoading: Boolean) {
        val viewLoading = view?.findViewById<View>(R.id.loadingCard)
        val txtTodosCard = view?.findViewById<View>(R.id.txtTodosHome)
        val txtRecomendadosHome = view?.findViewById<View>(R.id.txtRecomendadosHome)

        if (isLoading) {
            viewLoading?.visibility = View.VISIBLE
            recyclerCard?.visibility = View.INVISIBLE
            recyclerAvatar?.visibility = View.INVISIBLE
            txtTodosCard?.visibility = View.INVISIBLE
            txtRecomendadosHome?.visibility = View.INVISIBLE
        } else {
            viewLoading?.visibility = View.GONE
            recyclerCard?.visibility = View.VISIBLE
            recyclerAvatar?.visibility = View.VISIBLE
            txtTodosCard?.visibility = View.VISIBLE
            txtRecomendadosHome?.visibility = View.VISIBLE
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
                        _viewModel.nextPage().observe({ lifecycle }, {
                            update(it)
                            _character.addAll(it)
                            _characterAdapter.notifyDataSetChanged()
//                            showLoading()
                        })
                    }
                }
            })
        }
    }

    private fun getListSearcheByName(list: List<CharacterModel>) {
        _viewModel.getList().observe(viewLifecycleOwner) {
            list?.let {
                update(it)
                _character.addAll(it)
            }
            _characterAdapter.notifyDataSetChanged()
        }
    }

    private fun searchByName(view: View) {

        val searchView = view.findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                _viewModel.searchByName(query).observe(viewLifecycleOwner) {
                    if (it.isEmpty()) {
                        view.findViewById<RecyclerView>(R.id.recyclerCard).visibility = View.GONE
                        view.findViewById<TextView>(R.id.tvNoResult).visibility = View.VISIBLE
                    } else {
                        _character.clear()
                        getListSearcheByName(it)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _character.clear()
                    getListSearcheByName(_viewModel.initialList())
                    view.findViewById<RecyclerView>(R.id.recyclerCard).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.tvNoResult).visibility = View.GONE
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
        showLoading(true)
        _favoritosViewModel.getFavoriteCharacterLocal(userId!!).observe(viewLifecycleOwner) {
            if (it.isNotEmpty() && it.size > 1) {
                _viewModel.getRandomFavorite(it).observe(viewLifecycleOwner) { it1 ->
                    _comicViewModel.getComicList(it1).observe(viewLifecycleOwner) { list ->
                        if (list.isNotEmpty() && list.size > 1) {
                            _viewModel.getRecomended(list).observe(viewLifecycleOwner) { it2 ->
                                if (it2.isNotEmpty() && it2.size > 1) {
                                    _recomendados.addAll(it2)
                                    _avatarAdapter.notifyDataSetChanged()
                                    showLoading(false)
                                } else {
                                    getListAvatar()
                                    showLoading(false)
                                }
                            }

                        } else {
                            getListAvatar()
                            showLoading(false)
                        }

                    }
                }
            } else {
                getListAvatar()
                showLoading(false)
            }
        }
    }

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
        _favoritosViewModel.isFavorite(_character[position].id, userId!!)
            .observe(viewLifecycleOwner) { isFavorite ->
                removerAdicionar(isFavorite, position)
                _character[position].isFavorite = !_character[position].isFavorite
            }
    }

    private fun removerAdicionar(isFavorite: Boolean, position: Int) {
        if (isFavorite) {
            _favoritosViewModel.deleteCharacter(_character[position].id)
                .observe(viewLifecycleOwner) {
                    if (it) {
                        _character[position].isFavorite = false
                        if (onlyFavorites) {
                            _character.removeAt(position)
                            _characterAdapter.notifyDataSetChanged()
                        } else {
                            _characterAdapter.notifyItemChanged(position)
                        }
                    }
                }
        } else {
            val character = _character[position]
            _favoritosViewModel.addCharacter(
                character.nome,
                character.id,
                character.descricao,
                character.thumbnail!!.getImagePath(),
                userId!!
            )
                .observe(viewLifecycleOwner) {
                    if (it) {
                        _character[position].isFavorite = true
                        _characterAdapter.notifyItemChanged(position)
                    }
                }
        }
    }

    private fun checkConectividade(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
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
