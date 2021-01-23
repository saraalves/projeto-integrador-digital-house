package com.jenandsara.marvelapp.cadastro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jenandsara.marvelapp.login.view.LoginActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.jenandsara.marvelapp.R
import kotlinx.android.synthetic.main.dialog_confirmacao.view.*

class CadastroActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        auth = FirebaseAuth.getInstance()

        val toolbarCadastro = findViewById<MaterialToolbar>(R.id.toolbarCadastro)
        toolbarCadastro.setOnClickListener {
            showDialog()
        }

        val btnCadastro = findViewById<MaterialButton>(R.id.btnCadastro)
        btnCadastro.setOnClickListener {
            val nome = findViewById<EditText>(R.id.etNomeCadastro).text.toString()
            val email = findViewById<EditText>(R.id.etEmailCadastro).text.toString()
            val senha = findViewById<EditText>(R.id.edtSenhaCadastro).text.toString()
            val senhaConferir = findViewById<EditText>(R.id.edtRepeatSenhaCadastro).text.toString()

            if(checarCampos(nome, email,senha, senhaConferir)){
                if(checarQtdDigitosSenha(8, senha)){
                    if(senhasIguais(senha, senhaConferir)){
                        Toast.makeText(this, "DEU BOM", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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

    private fun cadastroFirebase(email: String, senha: String) {

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onBackPressed() {
        showDialog()
    }

    private fun checarCampos(nome:String, email: String, senha: String, senhaRepeat: String): Boolean {

        if(nome.trim().isNullOrEmpty()){
            findViewById<EditText>(R.id.etNomeCadastro).error = ERRO_VAZIO
            return false
        }else if(email.trim().isNullOrEmpty()){
            findViewById<EditText>(R.id.etEmailCadastro).error = ERRO_VAZIO
            return false
        }else if(senha.trim().isNullOrEmpty()){
            findViewById<EditText>(R.id.edtSenhaCadastro).error = ERRO_VAZIO
            return false
        }else if(senhaRepeat.trim().isNullOrEmpty()){
            findViewById<EditText>(R.id.edtRepeatSenhaCadastro).error = ERRO_VAZIO
            return false
        }
        return true
    }

    private fun checarQtdDigitosSenha(qtdDigitos:Int, senha: String): Boolean {
        if(senha.length >= qtdDigitos){
            return true
        } else {
            findViewById<EditText>(R.id.edtSenhaCadastro).text.clear()
            findViewById<EditText>(R.id.edtRepeatSenhaCadastro).text.clear()
            findViewById<EditText>(R.id.edtSenhaCadastro).error = ERRO_DIGITOS
            return false
        }
    }

    private fun senhasIguais(senha: String, senhaRepeat: String): Boolean {
        if(senha == senhaRepeat){
            return true
        } else {
            Toast.makeText(this@CadastroActivity, "As senhas devem ser iguais", Toast.LENGTH_SHORT)
                .show()
            findViewById<EditText>(R.id.edtSenhaCadastro).text.clear()
            findViewById<EditText>(R.id.edtRepeatSenhaCadastro).text.clear()
            return false
        }
    }

    companion object {
        const val ERRO_VAZIO = "Campo Vazio"
        const val ERRO_DIGITOS = "O campo deve ter no mínimo 8 dígitos"

    }

}