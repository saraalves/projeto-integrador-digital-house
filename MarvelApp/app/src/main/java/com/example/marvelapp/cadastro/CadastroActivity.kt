package com.example.marvelapp.cadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelapp.R
import com.google.android.material.appbar.MaterialToolbar

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val toolbarCadastro = findViewById<MaterialToolbar>(R.id.toolbarCadastro)
        toolbarCadastro.setOnClickListener{
            onBackPressed()
        }
    }
}