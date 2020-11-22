package com.example.marvelapp.perfil

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.marvelapp.R
import com.example.marvelapp.alterarsenha.AlterarSenhaActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_perfil.*

class PerfilFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAlterarSenha = view.findViewById<TextView>(R.id.changePassword)
        btnAlterarSenha.setOnClickListener {
            val intent = Intent(view.context, AlterarSenhaActivity::class.java)
            startActivity(intent)
        }

        val btnSalvar = view.findViewById<MaterialButton>(R.id.btnSalvarPerfil)
        btnSalvar.setOnClickListener {
            Toast.makeText(view.context, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

}