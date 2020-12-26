package com.jenandsara.marvelapp.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.cadastro.view.CadastroActivity
import com.jenandsara.marvelapp.home.view.HomeActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<TextView>(R.id.btnLogin)
        buttonLogin.setOnClickListener {
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val textAlterarSenha = findViewById<TextView>(R.id.btnEsqueciSenha)
        textAlterarSenha.setOnClickListener {
            Toast.makeText(this, "Alterar senha", Toast.LENGTH_SHORT).show()
        }

        val textIrProCadastro = findViewById<TextView>(R.id.btnNaoTenhoCadastro)
        textIrProCadastro.setOnClickListener {
            val intent = Intent(this@LoginActivity, CadastroActivity::class.java)
            startActivity(intent)
            finish()
        }

        val imageFacebook = findViewById<ImageView>(R.id.imgLoginFacebook)
        imageFacebook.setOnClickListener {
            Toast.makeText(this, "Logar com Facebook", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val imageGoogle = findViewById<ImageView>(R.id.imgLoginGoogle)
        imageGoogle.setOnClickListener {
            Toast.makeText(this, "Logar com Google", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}