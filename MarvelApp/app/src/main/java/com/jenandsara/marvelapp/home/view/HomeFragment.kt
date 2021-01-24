package com.jenandsara.marvelapp.home.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import com.jenandsara.marvelapp.favoritos.datalocal.database.AppDatabase
import com.jenandsara.marvelapp.home.view.avatar.AvatarAdapter
import com.jenandsara.marvelapp.home.view.character.CharacterAdapter
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository

class HomeFragment(private val onlyFavorites: Boolean = false): Fragment(), IGetCharacterClick {

    private lateinit var _view: View
    private lateinit var _viewModel: CharactersViewModel
    private lateinit var _characterAdapter: CharacterAdapter
    private lateinit var _avatarAdapter: AvatarAdapter

    private var _character = mutableListOf<CharacterModel>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        val manager = LinearLayoutManager(view.context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        val avatar = view.findViewById<RecyclerView>(R.id.recyclerAvatar)

        val viewGridManager = GridLayoutManager(view.context, 2)
        val recyclerViewCard = view.findViewById<RecyclerView>(R.id.recyclerCard)

        setupNavigation()
        setupNavigationAvatar()
        setupRecyclerViewAvatar(avatar, manager)
        setupRecyclerViewCard(recyclerViewCard, viewGridManager)
        viewModelProvider()

        if (_character.isEmpty()) getCharacters()
        updateCharacter()
//        getList(_character)
        searchByName(_view, _character)
        getListAvatar()
        showLoading(true)
        setScrollView()
        favoritar()
        setScrollViewAvatar()
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
        _characterAdapter = CharacterAdapter(_character) {
            val intent = Intent(view?.context, DetalhesActivity::class.java)
            intent.putExtra("ID", it.id)
            intent.putExtra("NOME", it.nome)
            intent.putExtra("DESCRIÇÃO", it.descricao)
            intent.putExtra("IMAGEM", it.thumbnail?.getImagePath())
            startActivity(intent)
        }
    }

    private fun setupNavigationAvatar() {
        _avatarAdapter = AvatarAdapter(_character) {
            val intent = Intent(view?.context, DetalhesActivity::class.java)
            intent.putExtra("ID", it.id)
            intent.putExtra("NOME", it.nome)
            intent.putExtra("DESCRIÇÃO", it.descricao)
            intent.putExtra("IMAGEM", it.thumbnail?.getImagePath())
            startActivity(intent)
        }
    }

    private fun getList(list: List<CharacterModel>) {
        _viewModel.getList().observe(viewLifecycleOwner) {
            list?.let { _character.addAll(it) }
            _characterAdapter.notifyDataSetChanged()
            showLoading(false)
        }
    }

    private fun getListAvatar() {
        _viewModel.getList().observe(viewLifecycleOwner) {
            _character.addAll(it)
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

    private fun searchByName(view: View, list: MutableList<CharacterModel>) {

        val searchView = view.findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                _viewModel.searchByName(query).observe(viewLifecycleOwner) {
                    _character.clear()
                    getList(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _character.clear()
                    getList(_viewModel.initialList())
                } else {
                    _viewModel.searchByStartsWith(newText).observe(viewLifecycleOwner){
                        _character.clear()
                        getList(it)
                    }
                }
                return false
            }
        })
    }

    private fun getCharacters() {
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
    }

    private fun updateCharacter() {
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
    }

    override fun getCharacterClick (position: Int) {
        val intent = Intent(view?.context, DetalhesActivity::class.java)
        val bundle = bundleOf("CHARACTER_ID" to _character[position].id)
        startActivity(intent, bundle)

    }

    override fun getCharacterFavoriteClick(position: Int) {
        _viewModel.isFavorite(_character[position].id)
            .observe(viewLifecycleOwner) { isFavorite ->
                if (isFavorite) {
                    _viewModel.deleteCharacter(_character[position].id)
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
                    _viewModel.addCharacter(_character[position].id)
                        .observe(viewLifecycleOwner) {
                            if (it) {
                                _character[position].isFavorite = true
                                _characterAdapter.notifyItemChanged(position)
                            }
                        }
                }

                _character[position].isFavorite = !_character[position].isFavorite
            }
    }

    private fun favoritar() {
        val toggleFavoritar = view?.findViewById<MaterialButtonToggleGroup>(R.id.toggleFavoritar)
        toggleFavoritar?.addOnButtonCheckedListener { _, _, isChecked ->
            if (isChecked) {
                view?.findViewById<MaterialButton>(R.id.btnFavoritar)
                    ?.setIconResource(R.drawable.ic_baseline_favorite_24)
            } else view?.findViewById<MaterialButton>(R.id.btnFavoritar)
                ?.setIconResource(R.drawable.ic_favorit_24)
        }
    }


    private fun viewModelProvider() {
        _viewModel = ViewModelProvider(
                this,
                CharactersViewModel.CharactersViewModelFactory(CharacterRepository(),
                    CharacterLocalRepository(AppDatabase.getDatabase(_view?.context).characterDAO())
        )).get(CharactersViewModel::class.java)
    }
}
