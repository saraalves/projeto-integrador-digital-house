package com.example.marvelapp.detalhes.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.comics.model.ComicsModel
import com.example.marvelapp.comics.repository.ComicRepository
import com.example.marvelapp.comics.viewmodel.ComicViewModel
import com.example.marvelapp.detalhes.view.comics.ComicsAdapter
import com.example.marvelapp.detalhes.view.stories.StoriesAdapter
import com.example.marvelapp.login.view.LoginActivity
import com.example.marvelapp.stories.repository.StoriesRepository
import com.example.marvelapp.stories.model.StoriesModel
import com.example.marvelapp.stories.viewmodel.StoriesViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhes.*
import kotlinx.android.synthetic.main.dialog_confirmacao.view.*
import kotlinx.android.synthetic.main.dialog_image.view.*

class DetalhesActivity : AppCompatActivity() {

    private lateinit var _comicsAdapter: ComicsAdapter
    private lateinit var _storiesAdapter: StoriesAdapter

    private lateinit var _comicViewModel: ComicViewModel
    private lateinit var _storiesViewModel: StoriesViewModel

    private var _comics = mutableListOf<ComicsModel>()
    private var _stories = mutableListOf<StoriesModel>()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val id = intent.getIntExtra("ID", 0)
        val nome = intent.getStringExtra("NOME")
        val descricao = intent.getStringExtra("DESCRIÇÃO")
        val imagem = intent.getStringExtra("IMAGEM")

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL

        val managerStories = LinearLayoutManager(this)
        managerStories.orientation = LinearLayoutManager.HORIZONTAL

        val comicsList = findViewById<RecyclerView>(R.id.recyclerComics)
        val storiesList = findViewById<RecyclerView>(R.id.recyclerStrories)

        setData(descricao, nome, imagem)

        setupNavigationComic()
        setupNavigationStories()

        setupRecyclerViewComics(comicsList, manager)
        setupRecyclerViewStories(storiesList, managerStories)

        comicViewModelProvider()
        storiesViewModelProvider()

        getStoriesList(id)
        getComicList(id)

    }

    private fun setData(descricao: String?, nome: String?, imagem: String?){

        if(descricao.isNullOrEmpty()){
            findViewById<TextView>(R.id.txtDescricao).text = "Hi, I'm ${nome} !"

        } else findViewById<TextView>(R.id.txtDescricao).text = descricao

        val toolbarCollapse = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbarCollapse.title = nome

        Picasso.get().load(imagem).into(findViewById<ImageView>(R.id.imgPersonagemDetail))

        topAppBar.setOnClickListener {
            onBackPressed()
        }

        val iconShare = findViewById<View>(R.id.share)
        iconShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Compartilhando personagens favoritos.")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        val iconFavorite = findViewById<View>(R.id.favorite)
        val iconFavorit = findViewById<View>(R.id.favorit)
        iconFavorit.isVisible = false

        iconFavorite.setOnClickListener {
            iconFavorite.isVisible = false
            iconFavorit.isVisible = true
            Toast.makeText(this, "Favorito adicionado", Toast.LENGTH_SHORT).show()
        }

        iconFavorit.setOnClickListener {
            iconFavorite.isVisible = true
            iconFavorit.isVisible = false
            Toast.makeText(this, "Favorito removido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerViewComics(
            recyclerView: RecyclerView?,
            viewLayoutManager: LinearLayoutManager
    ) {
        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = viewLayoutManager
            adapter = _comicsAdapter
        }
    }

    private fun getComicList(id: Int) {
        _comicViewModel.getComicList(id).observe({lifecycle}) {
            _comics.addAll(it)
            _comicsAdapter.notifyDataSetChanged()
        }
    }

    private fun comicViewModelProvider() {
        _comicViewModel = ViewModelProvider(
                this,
                ComicViewModel.ComicsViewModelFactory(ComicRepository())
        ).get(ComicViewModel::class.java)
    }

    private fun setupNavigationStories() {
        _storiesAdapter = StoriesAdapter(_stories){
            Toast.makeText(this@DetalhesActivity, "Nothing to show", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupNavigationComic() {
        _comicsAdapter = ComicsAdapter(_comics){
            showFullComicImage(it.thumbnail?.getImagePath())
        }
    }

    private fun setupRecyclerViewStories(
            recyclerView: RecyclerView?,
            viewLayoutManager: LinearLayoutManager
    ) {
        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = viewLayoutManager
            adapter = _storiesAdapter
        }
    }

    private fun getStoriesList(id: Int) {
        _storiesViewModel.getStoriesList(id).observe({lifecycle}, {
            _stories.addAll(it)
            _storiesAdapter.notifyDataSetChanged()
        })
    }

    private fun storiesViewModelProvider() {
        _storiesViewModel = ViewModelProvider(
                this,
                StoriesViewModel.StoriesViewModelFactory(StoriesRepository())
        ).get(StoriesViewModel::class.java)
    }

    private fun showFullComicImage(path: String) {

        var imageDialog: AlertDialog?

        val dialogBuilder = AlertDialog.Builder(this@DetalhesActivity)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_image, null, false)
        dialogBuilder.setView(dialogView)

        imageDialog = dialogBuilder.create()
        imageDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        Picasso.get().load(path).into(dialogView.imgComicExpanded)
        imageDialog?.show()

    }
}