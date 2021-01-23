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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.login.view.LOGIN_TYPE
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
            alterarSenha(view)
        }

        getInfo(view)
        logOut(view)
        loginType(view)
        updateName(view)

    }

    private fun getInfo(view: View) {

        val nomePerfil = view.findViewById<TextInputEditText>(R.id.etNomeAtual)
        val emailPerfil = view.findViewById<TextInputEditText>(R.id.etEmailAtual)
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

            if (photoUrl != null) {
                Picasso.get().load(photoUrl).into(imgPerfil)
            }
        }
    }

    private fun logOut(view: View) {

        val logout = view?.findViewById<LinearLayout>(R.id.lnlLogoutPerfil)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(view?.context, SplashScreenActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }


    private fun loginType(view: View) {
        if (LOGIN_TYPE == "FACEBOOK" || LOGIN_TYPE == "GOOGLE") {
            view.findViewById<MaterialButtonToggleGroup>(R.id.toggleNome).visibility = View.GONE
            view.findViewById<ImageButton>(R.id.imageButtonCamera).visibility = View.GONE
            view.findViewById<MaterialButton>(R.id.btnSalvarPerfil).visibility = View.GONE
            view.findViewById<Button>(R.id.changePassword).visibility = View.GONE
        }
    }


    private fun updateImage(view: View) {


    }

    private fun updateName(view: View) {

        val toggleNome = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleNome)
        toggleNome.addOnButtonCheckedListener { _, _, isChecked ->
            view.findViewById<TextInputLayout>(R.id.txtNomePerfil).isEnabled = isChecked
        }

        val user = Firebase.auth.currentUser

        val btnSalvar = view.findViewById<MaterialButton>(R.id.btnSalvarPerfil)
        btnSalvar.setOnClickListener {

            val newName = view.findViewById<TextInputEditText>(R.id.etNomeAtual).text.toString()

            val profileUpdates = userProfileChangeRequest {
                displayName = newName
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(view.context, "Dados salvos com sucesso", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

    }

    private fun alterarSenha(view: View) {

        Firebase.auth.sendPasswordResetEmail(Firebase.auth.currentUser!!.email!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(view.context, "Verifique seu email", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(view?.context, SplashScreenActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
    }
}