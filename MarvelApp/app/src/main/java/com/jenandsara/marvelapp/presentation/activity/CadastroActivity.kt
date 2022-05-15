package com.jenandsara.marvelapp.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.databinding.CadastroActivityBinding
import com.jenandsara.marvelapp.databinding.DialogConfirmacaoBinding
import com.jenandsara.marvelapp.databinding.HomeActivityBinding
import kotlinx.android.synthetic.main.dialog_confirmacao.view.*

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        CadastroActivityBinding.inflate(layoutInflater)
    }
    private val bindindDialog by lazy {
        DialogConfirmacaoBinding.inflate(layoutInflater)
    }

    private var alertDialog: AlertDialog? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.checkboxCadastro.isChecked = false
        binding.btnCadastro.isEnabled = false

        binding.toolbarCadastro.setOnClickListener {
            showDialog()
        }

        validaCampos()
        setupHyperlink()
    }

    private fun showDialog() {

        val dialogBuilder = AlertDialog.Builder(this@CadastroActivity)
        dialogBuilder.setView(bindindDialog.root)

        bindindDialog.btnContinuarEditando.setOnClickListener { alertDialog?.dismiss() }

        bindindDialog.btnConfirmar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        alertDialog = dialogBuilder.create()
        alertDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog?.show()
    }

    private fun setupHyperlink() {
        val linkPrivacy = binding.txtPrivacy
        val linkTermsAndConditions = binding.txtTermsAndConditions
        linkPrivacy.movementMethod = LinkMovementMethod.getInstance()
        linkTermsAndConditions.movementMethod = LinkMovementMethod.getInstance()
    }


    override fun onBackPressed() {
        showDialog()
    }

    private fun validaCampos() {


        enableButton(binding.btnCadastro)

        binding.btnCadastro.setOnClickListener {
            val nome = binding.etNomeCadastro.text.toString()
            val email = binding.etEmailCadastro.text.toString()
            val senha = binding.edtSenhaCadastro.text.toString()
            val senhaConferir = binding.edtRepeatSenhaCadastro.text.toString()

            if (checarCamposVazios(nome, email, senha, senhaConferir)) {
                if (checarQtdDigitosSenha(8, senha)) {
                    if (senhasIguais(senha, senhaConferir)) {
                        criarCadastroFirebase(nome, email, senha)
                    }
                }
            }
        }

    }

    private fun checarCamposVazios(
        nome: String,
        email: String,
        senha: String,
        senhaRepeat: String
    ): Boolean {

        if (nome.trim().isEmpty()) {
            binding.etNomeCadastro.error = ERRO_VAZIO
            return false
        } else if (email.trim().isEmpty()) {
            binding.etEmailCadastro.error = ERRO_VAZIO
            return false
        } else if (senha.trim().isEmpty()) {
            binding.edtSenhaCadastro.error = ERRO_VAZIO
            return false
        } else if (senhaRepeat.trim().isEmpty()) {
            binding.edtRepeatSenhaCadastro.error = ERRO_VAZIO
            return false
        }
        return true
    }

    private fun enableButton(button: Button) {
        val checkbox = findViewById<CheckBox>(R.id.checkboxCadastro)

        binding.checkboxCadastro.setOnCheckedChangeListener { _, isChecked ->
            button.isEnabled = isChecked
        }
    }

    private fun checarQtdDigitosSenha(qtdDigitos: Int, senha: String): Boolean {
        if (senha.length >= qtdDigitos) {
            return true
        } else {
            binding.edtSenhaCadastro.text?.clear()
            binding.edtRepeatSenhaCadastro.text?.clear()
            binding.edtSenhaCadastro.error = ERRO_DIGITOS
            return false
        }
    }

    private fun senhasIguais(senha: String, senhaRepeat: String): Boolean {
        if (senha == senhaRepeat) {
            return true
        } else {
            Toast.makeText(
                this@CadastroActivity,
                "Passwords must match each other",
                Toast.LENGTH_SHORT
            )
                .show()
            binding.edtSenhaCadastro.text?.clear()
            binding.edtRepeatSenhaCadastro.text?.clear()
            return false
        }
    }

    private fun criarCadastroFirebase(nome: String, email: String, senha: String) {

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    val profileUpdates = userProfileChangeRequest {
                        displayName = nome
                    }

                    user!!.updateProfile(profileUpdates).addOnCompleteListener {
                        Toast.makeText(
                            baseContext,
                            "User has been successfully created",
                            Toast.LENGTH_SHORT
                        ).show()
                        sendEmail()
                        val intent = Intent(this@CadastroActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toast.makeText(
                        baseContext,
                        "An error ocurred while creating user's account",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun sendEmail() {
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                }
            }
    }

    companion object {
        const val ERRO_VAZIO = "Empty field"
        const val ERRO_DIGITOS = "Field must have at least 8 digits"

    }

}