package com.example.marvelapp.detalhes.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.marvelapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhes.*

class DetalhesActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val nome = intent.getStringExtra("NOME")
        val descricao = intent.getStringExtra("DESCRIÇÃO")
        val imagem = intent.getStringExtra("IMAGEM")

        if(descricao.isNullOrEmpty()){
            findViewById<TextView>(R.id.txtDescricao).text = "Description not avaliable"
        } else findViewById<TextView>(R.id.txtDescricao).text = descricao

        findViewById<TextView>(R.id.txtNomePersonagemDetail).text = nome
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
        iconFavorite.setOnClickListener {
            Toast.makeText(this, "Favorito removido", Toast.LENGTH_SHORT).show()
        }

    }
}