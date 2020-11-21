package com.example.marvelapp.alterarsenha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import com.example.marvelapp.R

class AlterarSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_senha)

        val toolbar = findViewById<Toolbar>(R.id.toolbarAlterarSenha)
        toolbar.setNavigationOnClickListener{
            showDialog()
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