package com.example.marvelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.marvelapp.alterar_senha.AlterarSenhaActivity
import com.example.marvelapp.login.LoginActivity
import com.example.marvelapp.view.activity.CadastroActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textIrProLogin = findViewById<TextView>(R.id.telaLogin)
        textIrProLogin.setOnClickListener{
            val intent = Intent(this@MainActivity, LoginActivity::class.java )
            startActivity(intent)
        }

        val textAlterarSenha = findViewById<TextView>(R.id.telaAlterar)
        textAlterarSenha.setOnClickListener {
            val intent = Intent(this@MainActivity, AlterarSenhaActivity::class.java)
            startActivity(intent)
        }

        val textIrProCadastro = findViewById<TextView>(R.id.telaCadastro)
        textIrProCadastro.setOnClickListener {
            val intent = Intent(this@MainActivity, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}