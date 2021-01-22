package com.jenandsara.marvelapp.perfil.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.jenandsara.marvelapp.alterarsenha.view.AlterarSenhaActivity
import com.jenandsara.marvelapp.splashscreen.view.SplashScreenActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputLayout
import com.jenandsara.marvelapp.R

class PerfilFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAlterarSenha = view.findViewById<TextView>(R.id.changePassword)
        btnAlterarSenha.setOnClickListener {
        }

        val btnSalvar = view.findViewById<MaterialButton>(R.id.btnSalvarPerfil)
        btnSalvar.setOnClickListener {
            Toast.makeText(view.context, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show()
        }

        val logout = view.findViewById<LinearLayout>(R.id.lnlLogoutPerfil)
        logout.setOnClickListener {
            val intent = Intent(view.context, SplashScreenActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        val toggleNome = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleNome)
        toggleNome.addOnButtonCheckedListener { _, _, isChecked ->
            if(isChecked) {
                view.findViewById<TextInputLayout>(R.id.txtNomePerfil).isEnabled = true
            } else view.findViewById<TextInputLayout>(R.id.txtNomePerfil).isEnabled = false
        }

        val toggleEmail = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleEmail)
        toggleEmail.addOnButtonCheckedListener { _, _, isChecked ->
            if(isChecked) {
                view.findViewById<TextInputLayout>(R.id.txtEmailPerfil).isEnabled = true
            } else  view.findViewById<TextInputLayout>(R.id.txtEmailPerfil).isEnabled = false
        }

    }
}