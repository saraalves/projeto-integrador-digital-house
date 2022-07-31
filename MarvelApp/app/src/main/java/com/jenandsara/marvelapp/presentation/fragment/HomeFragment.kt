package com.jenandsara.marvelapp.presentation.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import com.jenandsara.marvelapp.data.model.character.CharacterResponse
import com.jenandsara.marvelapp.domain.repository.CharacterRepository
import com.jenandsara.marvelapp.presentation.viewmodel.CharactersViewModel
import com.jenandsara.marvelapp.presentation.activity.DetalhesActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jenandsara.marvelapp.domain.repository.ComicRepository
import com.jenandsara.marvelapp.presentation.viewmodel.ComicViewModel
import com.jenandsara.marvelapp.data.datasource.local.datalocal.database.AppDatabase
import com.jenandsara.marvelapp.presentation.adapter.avatar.AvatarAdapter
import com.jenandsara.marvelapp.presentation.adapter.character.CharacterAdapter
import com.jenandsara.marvelapp.data.datasource.local.datalocal.repository.CharacterLocalRepository
import com.jenandsara.marvelapp.databinding.HomeFragmentBinding
import com.jenandsara.marvelapp.presentation.viewmodel.FavoriteViewModel
import com.jenandsara.marvelapp.presentation.IGetCharacterClick
import kotlinx.android.synthetic.main.home_fragment.*
import kotlin.properties.Delegates

class HomeFragment(private val onlyFavorites: Boolean = false) : Fragment(), IGetCharacterClick {

    private var _binding: HomeFragmentBinding? = null
    private val binding: HomeFragmentBinding get() = _binding!!

    private lateinit var _view: View
    private lateinit var _viewModel: CharactersViewModel
    private lateinit var _favoritosViewModel: FavoriteViewModel
    private lateinit var _comicViewModel: ComicViewModel

    private lateinit var _characterAdapter: CharacterAdapter
    private lateinit var _avatarAdapter: AvatarAdapter
    private var position by Delegates.notNull<Int>()

    private var _character = mutableListOf<CharacterResponse>()
    private var _recomendados = mutableListOf<CharacterResponse>()

    private val userId = Firebase.auth.currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkConectividade()) {


            _view = view

            val manager = LinearLayoutManager(view.context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            val avatar = binding.recyclerAvatar

            val viewGridManager = GridLayoutManager(view.context, 2)
            val recyclerViewCard = binding.recyclerCard

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
            binding.ctlTeste.visibility = View.VISIBLE
            binding.ctlConection.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if (!checkConectividade()) {
            binding.ctlTeste.visibility = View.VISIBLE
            binding.ctlConection.visibility = View.GONE
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
            bundleOf("NOME" to _character[position].name)
            bundleOf("DESCRIÇÃO" to _character[position].description)
            bundleOf("IMAGEM" to _character[position].thumbnail?.getImagePath())
            startActivity(this)
        }
    }

    private fun setupNavigationAvatar() {
        _avatarAdapter = AvatarAdapter(_recomendados) {
            val intent = Intent(view?.context, DetalhesActivity::class.java)
            intent.putExtra("ID", it.id)
            intent.putExtra("NOME", it.name)
            intent.putExtra("DESCRIÇÃO", it.description)
            intent.putExtra("IMAGEM", it.thumbnail?.getImagePath())
            startActivity(intent)
        }
    }

    private fun update(list: List<CharacterResponse>) {
        list.forEach {
            _favoritosViewModel.isFavorite(it.id, userId!!).observe(viewLifecycleOwner) { b: Boolean ->
                it.isFavorite = b
            }
        }

    }

    private fun getList(list: List<CharacterResponse>) {
        _viewModel.getList().observe(viewLifecycleOwner) { list1 ->
            list.let {
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
        val viewLoading = binding.loadingCard
        val txtTodosCard = binding.txtTodosHome
        val txtRecomendadosHome = binding.txtRecomendadosHome

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
            recyclerCard?.visibility = View.INVISIBLE
            recyclerAvatar?.visibility = View.INVISIBLE
            txtTodosCard.visibility = View.INVISIBLE
            txtRecomendadosHome.visibility = View.INVISIBLE
        } else {
            viewLoading.visibility = View.GONE
            recyclerCard?.visibility = View.VISIBLE
            recyclerAvatar?.visibility = View.VISIBLE
            txtTodosCard.visibility = View.VISIBLE
            txtRecomendadosHome.visibility = View.VISIBLE
        }
    }


    private fun setScrollView() {
        val recyclerView = binding.recyclerCard
        recyclerView.run {
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

    private fun getListSearcheByName(list: List<CharacterResponse>) {
        _viewModel.getList().observe(viewLifecycleOwner) {
            list.let {
                update(it)
                _character.addAll(it)
            }
            _characterAdapter.notifyDataSetChanged()
        }
    }

    private fun searchByName(view: View) {

        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                _viewModel.searchByName(query).observe(viewLifecycleOwner) {
                    if (it.isEmpty()) {
                        binding.recyclerCard.visibility = View.GONE
                        binding.tvNoResult.visibility = View.VISIBLE
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
                    binding.recyclerCard.visibility = View.VISIBLE
                    binding.tvNoResult.visibility = View.GONE
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
            putExtra("NOME", _character[position].name)
            putExtra("DESCRIÇÃO", _character[position].description)
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
                character.name,
                character.id,
                character.description,
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
