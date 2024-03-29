package com.jenandsara.marvelapp.presentation.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.presentation.activity.AboutUsActivity
import com.jenandsara.marvelapp.presentation.activity.LOGIN_TYPE
import com.jenandsara.marvelapp.presentation.activity.SplashScreenActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PerfilFragment : Fragment() {

    private var imgURI: Uri? = null
    private lateinit var _view: View
    private val user = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        if(checkConectividade()){

            val btnAlterarSenha = view.findViewById<Button>(R.id.changePassword)
            btnAlterarSenha.setOnClickListener {
                alterarSenha(view)
            }

            val btnAlterarFoto = view.findViewById<ImageButton>(R.id.imageButtonCamera)
            btnAlterarFoto.setOnClickListener {
                procurarFoto()
            }

            updateProfile(view)

        }  else {

            Toast.makeText(_view.context, "Updates only work when when network connection is available", Toast.LENGTH_LONG).show()

            val btnAlterarFoto = view.findViewById<ImageButton>(R.id.imageButtonCamera)
            btnAlterarFoto.isEnabled = false

            val btnAlterarSenha = view.findViewById<Button>(R.id.changePassword)
            btnAlterarSenha.isEnabled = false

            val toggleNome = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleNome)
            toggleNome.isEnabled = false

        }

        getInfo(view)
        logOut(view)
        loginType(view)
        goToAboutUs(view)

    }

    private fun getInfo(view: View) {

        val nomePerfil = view.findViewById<TextInputEditText>(R.id.etNomeAtual)
        val emailPerfil = view.findViewById<TextInputEditText>(R.id.etEmailAtual)
        val imgPerfil = view.findViewById<CircleImageView>(R.id.imgPhotoPerfil)

        user?.let {

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
        val logout = view?.findViewById<Button>(R.id.txtSairApp)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(view?.context, SplashScreenActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun goToAboutUs(view: View) {
        val aboutUs = view?.findViewById<Button>(R.id.btnAboutUs)
        aboutUs.setOnClickListener {
            val intent = Intent(view?.context, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginType(view: View) {
        if (LOGIN_TYPE == "FACEBOOK" || LOGIN_TYPE == "GOOGLE" || user?.email!!.contains("@gmail")) {
            view.findViewById<MaterialButtonToggleGroup>(R.id.toggleNome).visibility = View.GONE
            view.findViewById<ImageButton>(R.id.imageButtonCamera).visibility = View.GONE
            view.findViewById<MaterialButton>(R.id.btnSalvarPerfil).visibility = View.INVISIBLE
            view.findViewById<Button>(R.id.changePassword).visibility = View.INVISIBLE
        }
    }

    private fun updateProfile(view: View) {

        val toggleNome = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleNome)
        toggleNome.addOnButtonCheckedListener { _, _, isChecked ->
            view.findViewById<TextInputLayout>(R.id.txtNomePerfil).isEnabled = isChecked
        }

        val btnSalvar = _view.findViewById<MaterialButton>(R.id.btnSalvarPerfil)
        btnSalvar.setOnClickListener {

            if(imgURI != null){
                enviarArquivo(user!!.uid)
            }

            val newName = view.findViewById<TextInputEditText>(R.id.etNomeAtual).text.toString()

            val profileUpdates = userProfileChangeRequest {
                displayName = newName
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(view.context, "Data has been successfully saved", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

    }

    private fun alterarSenha(view: View) {

        Firebase.auth.sendPasswordResetEmail(Firebase.auth.currentUser!!.email!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(view.context, "Check your email box", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(view?.context, SplashScreenActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
    }

    private fun procurarFoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, CONTENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CONTENT_REQUEST_CODE && resultCode == RESULT_OK) {
            imgURI = data?.data
            _view.findViewById<CircleImageView>(R.id.imgPhotoPerfil)?.setImageURI(imgURI)
        }
    }

    private fun enviarArquivo(usId: String) {
        imgURI.run {

            val extension = MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(activity?.contentResolver?.getType(imgURI!!))

            val firebaseStorage = FirebaseStorage.getInstance()
            val storageRef = firebaseStorage.getReference("${usId}/profilePicture")

            val fileReference = storageRef.child("${usId}/profilePicture.${extension}")

            fileReference.putFile(imgURI!!)
                .addOnSuccessListener {
                    obterArquivo(usId, extension)
                }
                .addOnCanceledListener {
                    Toast.makeText(
                        _view.context,
                        "Image upload has been cancelled",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        _view.context,
                        "Image upload has failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun obterArquivo(usId: String, extension: String?) {

        val imgPerfil = _view.findViewById<CircleImageView>(R.id.imgPhotoPerfil)

        val firebaseStorage = FirebaseStorage.getInstance()
        val storageRef = firebaseStorage.getReference("${usId}/profilePicture")

        storageRef.child("${usId}/profilePicture.${extension}").downloadUrl.addOnSuccessListener { imgURI ->

            Picasso.get().load(imgURI).into(imgPerfil)

            val profileUpdates = userProfileChangeRequest {
                photoUri = imgURI
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            _view.context,
                            "Data has been successfully saved",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
        }
    }

    private fun checkConectividade(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }

    companion object {
        const val CONTENT_REQUEST_CODE = 3
    }

}

