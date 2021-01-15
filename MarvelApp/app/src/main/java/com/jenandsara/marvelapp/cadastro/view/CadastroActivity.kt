package com.jenandsara.marvelapp.cadastro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jenandsara.marvelapp.mainactivity.view.HomeActivity
import com.jenandsara.marvelapp.login.view.LoginActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.jenandsara.marvelapp.R
import kotlinx.android.synthetic.main.dialog_confirmacao.view.*

class CadastroActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val toolbarCadastro = findViewById<MaterialToolbar>(R.id.toolbarCadastro)
        toolbarCadastro.setOnClickListener {
            showDialog()
        }

        val btnCadastro = findViewById<MaterialButton>(R.id.btnCadastro)
        btnCadastro.setOnClickListener {
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@CadastroActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showDialog() {

        val dialogBuilder = AlertDialog.Builder(this@CadastroActivity)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmacao, null, false)
        dialogBuilder.setView(dialogView)

        dialogView.btnContinuarEditando.setOnClickListener { alertDialog?.dismiss() }

        dialogView.btnConfirmar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        alertDialog = dialogBuilder.create()
        alertDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog?.show()
    }

    override fun onBackPressed() {
        showDialog()
    }
}