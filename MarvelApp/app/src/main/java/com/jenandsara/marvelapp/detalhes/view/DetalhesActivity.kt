package com.jenandsara.marvelapp.detalhes.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.comics.model.ComicsModel
import com.jenandsara.marvelapp.comics.repository.ComicRepository
import com.jenandsara.marvelapp.comics.viewmodel.ComicViewModel
import com.jenandsara.marvelapp.detalhes.view.comics.ComicsAdapter
import com.jenandsara.marvelapp.detalhes.view.stories.StoriesAdapter
import com.jenandsara.marvelapp.favoritos.datalocal.database.AppDatabase
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository
import com.jenandsara.marvelapp.favoritos.viewmodel.FavoriteViewModel
import com.jenandsara.marvelapp.stories.model.StoriesModel
import com.jenandsara.marvelapp.stories.repository.StoriesRepository
import com.jenandsara.marvelapp.stories.viewmodel.StoriesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhes.*
import kotlinx.android.synthetic.main.dialog_image.view.*
import kotlin.properties.Delegates

class DetalhesActivity : AppCompatActivity() {

    private lateinit var _comicsAdapter: ComicsAdapter
    private lateinit var _storiesAdapter: StoriesAdapter

    private lateinit var _comicViewModel: ComicViewModel
    private lateinit var _storiesViewModel: StoriesViewModel

    private lateinit var _favoritosViewModel: FavoriteViewModel

    private var _comics = mutableListOf<ComicsModel>()
    private var _stories = mutableListOf<StoriesModel>()

    private var _id by Delegates.notNull<Int>()

    private val userId = Firebase.auth.currentUser?.uid

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        _id = intent.getIntExtra("ID", 0)
        val nome = intent.getStringExtra("NOME")
        val descricao = intent.getStringExtra("DESCRIÇÃO")
        val imagem = intent.getStringExtra("IMAGEM")
        val favorite = intent.getBooleanExtra("FAVORITO", true)

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL

        val managerStories = LinearLayoutManager(this)
        managerStories.orientation = LinearLayoutManager.HORIZONTAL

        val comicsList = findViewById<RecyclerView>(R.id.recyclerComics)
        val storiesList = findViewById<RecyclerView>(R.id.recyclerStrories)

        localViewModelProvider()

        favoritar(nome!!, _id, descricao!!, imagem!!, favorite)

        setData(descricao, nome, imagem)


        setupNavigationComic()
        setupNavigationStories()

        setupRecyclerViewComics(comicsList, manager)
        setupRecyclerViewStories(storiesList, managerStories)

        comicViewModelProvider()
        storiesViewModelProvider()

        if (checkConectividade()) {
            getStoriesList(_id)
            getComicList(_id)
            setScrollViewComics()
            setScrollViewStories()
        } else {
            findViewById<TextView>(R.id.txtComics).visibility = View.GONE
            findViewById<TextView>(R.id.txtStories).visibility = View.GONE
            comicsList.visibility = View.GONE
            storiesList.visibility = View.GONE
            findViewById<ConstraintLayout>(R.id.ctlNoconection).visibility = View.VISIBLE
        }
    }

    private fun setData(descricao: String?, nome: String?, imagem: String?) {

        if (descricao.isNullOrEmpty()) {
            findViewById<TextView>(R.id.txtDescricao).text = "Hi, I'm ${nome} !"

        } else findViewById<TextView>(R.id.txtDescricao).text = descricao

        val toolbarCollapse = findViewById<CollapsingToolbarLayout>(R.id.collapseToolbar)
        toolbarCollapse.title = nome

        Picasso.get().load(imagem).into(findViewById<ImageView>(R.id.imgPersonagemDetail))

        topAppBar.setOnClickListener {
            onBackPressed()
        }

        val iconShare = findViewById<View>(R.id.share)
        iconShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check this out! $nome at MarvelApp. Image link: $imagem")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun setScrollViewComics() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerComics)
        recyclerView?.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val target = recyclerView.layoutManager as LinearLayoutManager?
                    val totalItemCount = target!!.itemCount
                    val lastVisible = target.findLastVisibleItemPosition()
                    val lastItem = lastVisible + 1 >= totalItemCount

                    if (totalItemCount > 0 && lastItem) {
                        _comicViewModel.nextPage(_id).observe({ lifecycle }, {
                            _comics.addAll(it)
                            _comicsAdapter.notifyDataSetChanged()
                        })
                    }
                }
            })
        }
    }

    private fun setScrollViewStories() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerStrories)
        recyclerView?.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val target = recyclerView.layoutManager as LinearLayoutManager?
                    val totalItemCount = target!!.itemCount
                    val lastVisible = target.findLastVisibleItemPosition()
                    val lastItem = lastVisible + 1 >= totalItemCount

                    if (totalItemCount > 0 && lastItem) {
                        _storiesViewModel.nextPage(_id).observe({ lifecycle }, {
                           _stories.addAll(it)
                           _storiesAdapter.notifyDataSetChanged()
                        })
                    }
                }
            })
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun favoritar(nome: String, id: Int, descricao: String, imgPath: String, fav: Boolean) {

        val iconFavorite = findViewById<View>(R.id.favorite)
        val iconFavorit = findViewById<View>(R.id.favorit)

        _favoritosViewModel.isFavorite(id, userId!!).observe(this) {
            iconFavorit.isVisible = it
            iconFavorite.isVisible = !it
        }

        iconFavorite.setOnClickListener {
            iconFavorite.isVisible = false
            iconFavorit.isVisible = true

            _favoritosViewModel.addCharacter(nome, id, descricao, imgPath, userId).observe(this) {
                if (it) Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }

        iconFavorit.setOnClickListener {
            iconFavorite.isVisible = true
            iconFavorit.isVisible = false
            isFavoritOrNot(iconFavorite, iconFavorit, id, nome, descricao, imgPath)
        }
    }

    private fun isFavoritOrNot(
        iconFavorite: View,
        iconFavorit: View,
        id: Int,
        nome: String,
        descricao: String,
        imgPath: String
    ) {
        iconFavorite.isVisible = true
        iconFavorit.isVisible = false
        addOrRemoveCharacter(id, nome, descricao, imgPath, iconFavorite, iconFavorit)
    }

    private fun addOrRemoveCharacter(
        id: Int,
        nome: String,
        descricao: String,
        imgPath: String,
        iconFavorite: View,
        iconFavorit: View
    ) {
        _favoritosViewModel.deleteCharacter(id).observe(this) { it ->
            if (it) {
                Snackbar.make(
                    findViewById<View>(R.id.snackbarDetalhes),
                    "Removed from favorites",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("UNDO") {
                        _favoritosViewModel.addCharacter(nome, id, descricao, imgPath, userId!!)
                            .observe(this) {
                                if (it) {
                                    iconFavorite.isVisible = false
                                    iconFavorit.isVisible = true
                                }
                            }

                    }
                    .setActionTextColor(Color.parseColor("#FFFFFF"))
                    .setTextColor(Color.parseColor("#FFFFFF"))
                    .setBackgroundTint(Color.parseColor("#666666"))
                    .show()
            }
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
        _comicViewModel.getComicList(id).observe({ lifecycle }) {
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
        _storiesAdapter = StoriesAdapter(_stories) {
            Toast.makeText(this@DetalhesActivity, "Nothing to show", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupNavigationComic() {
        _comicsAdapter = ComicsAdapter(_comics) {
            showFullComicImage(it.thumbnail.getImagePath())
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
        _storiesViewModel.getStoriesList(id).observe({ lifecycle }, {
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

        val imageDialog: AlertDialog?

        val dialogBuilder = AlertDialog.Builder(this@DetalhesActivity)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_image, null, false)
        dialogBuilder.setView(dialogView)

        imageDialog = dialogBuilder.create()
        imageDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        Picasso.get().load(path).into(dialogView.imgComicExpanded)
        imageDialog.show()

    }

    private fun localViewModelProvider() {
        _favoritosViewModel = ViewModelProvider(
            this,
            FavoriteViewModel.FavoritosViewModelFactory(
                CharacterLocalRepository(AppDatabase.getDatabase(this).characterDAO())
            )
        ).get(FavoriteViewModel::class.java)
    }

    private fun checkConectividade(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }
}