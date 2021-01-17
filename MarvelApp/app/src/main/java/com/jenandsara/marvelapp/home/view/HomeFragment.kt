package com.jenandsara.marvelapp.home.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.character.repository.CharacterRepository
import com.jenandsara.marvelapp.character.viewmodel.CharactersViewModel
import com.jenandsara.marvelapp.detalhes.view.DetalhesActivity
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity
import com.jenandsara.marvelapp.datalocal.repository.LocalCharacterRepository
import com.jenandsara.marvelapp.datalocal.viewmodel.LocalCharacterViewModel
import com.jenandsara.marvelapp.db.AppDatabase
import com.jenandsara.marvelapp.home.view.avatar.AvatarAdapter
import com.jenandsara.marvelapp.home.view.character.CharacterAdapter

class HomeFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModel: CharactersViewModel
    private lateinit var _characterAdapter: CharacterAdapter
    private lateinit var _avatarAdapter: AvatarAdapter
    private lateinit var _localViewModel: LocalCharacterViewModel

    var _character = mutableListOf<CharacterModel>()

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
        getList()
        searchByName(_view, _character)
        getListAvatar()
        showLoading(true)
        setScrollView()
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
        _characterAdapter = CharacterAdapter() {


            val intent = Intent(view?.context, DetalhesActivity::class.java)
            intent.putExtra("ID", it.id_api)
            intent.putExtra("NOME", it.name)
            intent.putExtra("DESCRIÇÃO", it.description)
            intent.putExtra("IMAGEM", it.imgUrl)

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

    private fun getList() {
        localViewModelProvider()
        _viewModel.getList().observe(viewLifecycleOwner) {
            for(character in it){
                _localViewModel.adiconarChracter(character.nome, character.id, character.descricao,
                    character.thumbnail!!.getImagePath(), character.isFavorite).observe(viewLifecycleOwner) {
                    _characterAdapter.setList(it)
                }
            }
            _localViewModel.obterTodos()
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
                    //_character.clear()
                   // getList(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    //_character.clear()
                    //getList(_viewModel.initialList())
                } else {
                    _viewModel.searchByStartsWith(newText).observe(viewLifecycleOwner){
                        //_character.clear()
                        //getList(it)
                    }
                }
                return false
            }
        })
    }

    private fun viewModelProvider() {
        _viewModel = ViewModelProvider(
                this,
                CharactersViewModel.CharactersViewModelFactory(CharacterRepository())
        ).get(CharactersViewModel::class.java)
    }

    private fun localViewModelProvider() {
        _localViewModel = ViewModelProvider(
            this,
            LocalCharacterViewModel.LocalCharacterViewModelFactory(LocalCharacterRepository(AppDatabase.getDatabase(_view.context).characterDao()))
        ).get(LocalCharacterViewModel::class.java)
    }
}
