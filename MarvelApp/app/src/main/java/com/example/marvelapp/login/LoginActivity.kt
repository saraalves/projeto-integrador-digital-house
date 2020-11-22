package com.example.marvelapp.login
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.marvelapp.R
import com.example.marvelapp.alterarsenha.AlterarSenhaActivity
import com.example.marvelapp.cadastro.CadastroActivity
import com.example.marvelapp.home.view.HomeActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<TextView>(R.id.btnLogin)
        buttonLogin.setOnClickListener{
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java )
            startActivity(intent)
        }

        val textAlterarSenha = findViewById<TextView>(R.id.btnEsqueciSenha)
        textAlterarSenha.setOnClickListener {
            val intent = Intent(this@LoginActivity, AlterarSenhaActivity::class.java)
            startActivity(intent)
        }

        val textIrProCadastro = findViewById<TextView>(R.id.btnNaoTenhoCadastro)
        textIrProCadastro.setOnClickListener {
            val intent = Intent(this@LoginActivity, CadastroActivity::class.java)
            startActivity(intent)
        }

        val imageFacebook = findViewById<ImageView>(R.id.imgLoginFacebook)
        imageFacebook.setOnClickListener {
            Toast.makeText(this, "Logar com Facebook", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        val imageGoogle = findViewById<ImageView>(R.id.imgLoginGoogle)
        imageGoogle.setOnClickListener {
            Toast.makeText(this, "Logar com Google", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }


    }
}