package com.example.marvelapp.home.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.character.model.CharacterModel
import com.example.marvelapp.character.repository.CharacterRepository
import com.example.marvelapp.character.viewmodel.CharactersViewModel
import com.example.marvelapp.home.model.PersonagemModel
import com.example.marvelapp.detalhes.view.DetalhesActivity

class HomeFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModel: CharactersViewModel
    private lateinit var _characterAdapter: CharacterAdapter
    private var _character = mutableListOf<CharacterModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewGridManager = GridLayoutManager(view.context, 2)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCard)


        setupNavigation()
        setupRecyclerView(recyclerView, viewGridManager)
        viewModelProvider()
        getList()
        showLoading(true)
        setScrollView()
        listaAvatar(view)
    }

    private fun listaAvatar(view: View) {
        _view = view

        val avatar = view.findViewById<RecyclerView>(R.id.recyclerAvatar)
        val manager = LinearLayoutManager(view.context)
        manager.orientation = LinearLayoutManager.HORIZONTAL

        val listaPersonagens = mutableListOf<PersonagemModel>()
        val listaAdapter = adapterAvatar()

        avatar.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = listaAdapter
        }
    }

    private fun adapterAvatar(): AvatarAdapter {
        return AvatarAdapter(
            arrayListOf(
                PersonagemModel(
                    1,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    2,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    3,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                ),
                PersonagemModel(
                    4,
                    "CAPITÃ MARVEL",
                    R.drawable.img_avatar
                )
            )
        ) {
            val intent = Intent(view!!.context, DetalhesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView?,
        viewGridManager: GridLayoutManager
    ) {
        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = viewGridManager
            adapter = _characterAdapter
        }
    }

    private fun setupNavigation() {
        _characterAdapter = CharacterAdapter(_character) {
            val intent = Intent(view?.context, DetalhesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getList() {
        _viewModel.getList().observe(viewLifecycleOwner) {
            _character.addAll(it)
            _characterAdapter.notifyDataSetChanged()
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

    private fun viewModelProvider() {
        _viewModel = ViewModelProvider(
            this,
            CharactersViewModel.CharactersViewModelFactory(CharacterRepository())
        ).get(CharactersViewModel::class.java)
    }

}
