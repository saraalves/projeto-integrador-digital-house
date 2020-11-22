package com.example.marvelapp.detalhes.view

import android.annotation.SuppressLint
import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.example.marvelapp.R
import kotlinx.android.synthetic.main.activity_detalhes.*

class DetalhesActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        topAppBar.setOnClickListener {
            onBackPressed()
        }

        val iconShare = findViewById<View>(R.id.share)
        iconShare.setOnClickListener {
            Toast.makeText(this, "Compartilhando nas redes sociais", Toast.LENGTH_SHORT).show()
        }

        val iconFavorite = findViewById<View>(R.id.favorite)
        iconFavorite.setOnClickListener {
            Toast.makeText(this, "Favorito removido", Toast.LENGTH_SHORT).show()
        }
    }
}