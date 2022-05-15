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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.jenandsara.marvelapp.databinding.PerfilFragmentBinding
import com.jenandsara.marvelapp.presentation.activity.AboutUsActivity
import com.jenandsara.marvelapp.presentation.activity.LOGIN_TYPE
import com.jenandsara.marvelapp.presentation.activity.SplashScreenActivity
import com.squareup.picasso.Picasso

class PerfilFragment : Fragment() {

    private var _binding: PerfilFragmentBinding? = null
    private val binding: PerfilFragmentBinding get() = _binding!!

    private var imgURI: Uri? = null
    private lateinit var _view: View
    private val user = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PerfilFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        if (checkConectividade()) {

            binding.changePassword.setOnClickListener {
                alterarSenha(view)
            }

            binding.imageButtonCamera.setOnClickListener {
                procurarFoto()
            }

            updateProfile(view)

        } else {

            Toast.makeText(
                _view.context,
                "Updates only work when when network connection is available",
                Toast.LENGTH_LONG
            ).show()

            binding.imageButtonCamera.isEnabled = false

            binding.changePassword.isEnabled = false

            binding.toggleNome.isEnabled = false

        }

        getInfo(view)
        logOut(view)
        loginType(view)
        goToAboutUs(view)

    }

    private fun getInfo(view: View) {

        val nomePerfil = binding.etNomeAtual
        val emailPerfil = binding.etEmailAtual
        val imgPerfil = binding.imgPhotoPerfil

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
        binding.txtSairApp.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(view.context, SplashScreenActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun goToAboutUs(view: View) {
        binding.btnAboutUs.setOnClickListener {
            val intent = Intent(view.context, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginType(view: View) {
        if (LOGIN_TYPE == "FACEBOOK" || LOGIN_TYPE == "GOOGLE" || user?.email!!.contains("@gmail")) {
            binding.toggleNome.visibility = View.GONE
            binding.imageButtonCamera.visibility = View.GONE
            binding.btnSalvarPerfil.visibility = View.INVISIBLE
            binding.changePassword.visibility = View.INVISIBLE
        }
    }

    private fun updateProfile(view: View) {

        binding.toggleNome.addOnButtonCheckedListener { _, _, isChecked ->
            binding.txtNomePerfil.isEnabled = isChecked
        }

        binding.btnSalvarPerfil.setOnClickListener {

            if (imgURI != null) {
                enviarArquivo(user!!.uid)
            }

            val newName = binding.etNomeAtual.text.toString()

            val profileUpdates = userProfileChangeRequest {
                displayName = newName
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            view.context,
                            "Data has been successfully saved",
                            Toast.LENGTH_SHORT
                        )
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
                    val intent = Intent(view.context, SplashScreenActivity::class.java)
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
            binding.imgPhotoPerfil.setImageURI(imgURI)
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

        val imgPerfil = binding.imgPhotoPerfil

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

