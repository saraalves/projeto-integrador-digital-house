package com.jenandsara.marvelapp.perfil.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jenandsara.marvelapp.alterarsenha.view.AlterarSenhaActivity
import com.jenandsara.marvelapp.splashscreen.view.SplashScreenActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jenandsara.marvelapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PerfilFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAlterarSenha = view.findViewById<Button>(R.id.changePassword)
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
                view.findViewById<TextInputLayout>(R.id.txtNomePerfil).isEnabled = isChecked
        }

        val toggleEmail = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleEmail)
        toggleEmail.addOnButtonCheckedListener { _, _, isChecked ->
                view.findViewById<TextInputLayout>(R.id.txtEmailPerfil).isEnabled = isChecked
        }

        getInfo(view)

    }

    private fun getInfo(view: View){

        var nomePerfil = view.findViewById<TextInputEditText>(R.id.etNomeAtual)
        var emailPerfil = view.findViewById<TextInputEditText>(R.id.etEmailAtual)
        val imgPerfil = view.findViewById<CircleImageView>(R.id.imgPhotoPerfil)

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            val uid = user.uid

            nomePerfil.setText(name)
            emailPerfil.setText(email)

            if(photoUrl != null){
                Picasso.get().load(photoUrl).into(imgPerfil)
            }
        }
    }


}