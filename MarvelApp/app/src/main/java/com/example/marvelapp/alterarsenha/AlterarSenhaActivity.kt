package com.example.marvelapp.alterarsenha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import com.example.marvelapp.R
import com.example.marvelapp.login.LoginActivity
import com.google.android.material.button.MaterialButton

class AlterarSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_senha)

        val toolbar = findViewById<Toolbar>(R.id.toolbarAlterarSenha)
        toolbar.setNavigationOnClickListener{
            showDialog()
        }

        val btnAlterarSenha = findViewById<MaterialButton>(R.id.btnSalvarSenha)
        btnAlterarSenha.setOnClickListener {
            Toast.makeText(this, "Senha alterada com sucesso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@AlterarSenhaActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDialog(){
        AlertDialog.Builder(this)
            .setTitle("Confirmar cancelamento?")
            .setMessage("Ao cancelar essa ação, seus dados cadastrais não serão alterados.")
            .setPositiveButton("CONFIRMAR"){dialog, which ->
                finish()
            }
            .setNegativeButton("CONTINUAR EDITANDO"){dialog, which ->
            }
            .show()
    }

}