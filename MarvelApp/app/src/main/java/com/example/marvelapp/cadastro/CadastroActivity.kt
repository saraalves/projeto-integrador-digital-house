package com.example.marvelapp.cadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.marvelapp.R
import com.example.marvelapp.home.view.HomeActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val toolbarCadastro = findViewById<MaterialToolbar>(R.id.toolbarCadastro)
        toolbarCadastro.setOnClickListener{
            showDialog()
        }

        val btnCadastro = findViewById<MaterialButton>(R.id.btnCadastro)
        btnCadastro.setOnClickListener {
            val intent = Intent(this@CadastroActivity, HomeActivity::class.java)
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