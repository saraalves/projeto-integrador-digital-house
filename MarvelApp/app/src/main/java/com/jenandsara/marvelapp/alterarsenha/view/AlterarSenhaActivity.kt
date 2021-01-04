package com.jenandsara.marvelapp.alterarsenha.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import com.example.marvelapp.R
import com.jenandsara.marvelapp.login.view.LoginActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.dialog_confirmacao.view.*

class AlterarSenhaActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_senha)

        val toolbar = findViewById<Toolbar>(R.id.toolbarAlterarSenha)
        toolbar.setNavigationOnClickListener {
            showDialog()
        }

        val btnAlterarSenha = findViewById<MaterialButton>(R.id.btnSalvarSenha)
        btnAlterarSenha.setOnClickListener {

            Toast.makeText(this, "Senha alterada com sucesso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@AlterarSenhaActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showDialog() {

        val dialogBuilder = AlertDialog.Builder(this@AlterarSenhaActivity)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmacao, null, false)
        dialogBuilder.setView(dialogView)

        dialogView.btnContinuarEditando.setOnClickListener { alertDialog?.dismiss() }

        dialogView.btnConfirmar.setOnClickListener { finish() }

        alertDialog = dialogBuilder.create()
        alertDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog?.show()
    }

    override fun onBackPressed() {
        showDialog()
    }

}