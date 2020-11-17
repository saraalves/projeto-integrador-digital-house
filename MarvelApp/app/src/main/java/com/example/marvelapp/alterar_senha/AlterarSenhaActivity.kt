package com.example.marvelapp.alterar_senha

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.example.marvelapp.R

class AlterarSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_senha)

        val toolbar = findViewById<Toolbar>(R.id.toolbarAlterarSenha)
        toolbar.setNavigationOnClickListener{
            finish()
        }
    }

}