package com.example.marvelapp.cadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.marvelapp.R
import com.example.marvelapp.home.view.HomeActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
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
        }
    }

    private fun showDialog() {

        val dialogBuilder = AlertDialog.Builder(this@CadastroActivity)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmacao, null, false)
        dialogBuilder.setView(dialogView)

        dialogView.btnContinuarEditando.setOnClickListener { alertDialog?.dismiss() }

        dialogView.btnConfirmar.setOnClickListener { finish() }

        alertDialog = dialogBuilder.create()
        alertDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog?.show()
    }
}