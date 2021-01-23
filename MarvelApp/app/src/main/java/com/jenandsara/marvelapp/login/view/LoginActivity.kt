package com.jenandsara.marvelapp.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window.FEATURE_NO_TITLE
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.cadastro.view.CadastroActivity
import com.jenandsara.marvelapp.manageractivity.view.HomeActivity
import com.jenandsara.marvelapp.login.AppUtil


var LOGIN_TYPE = ""

class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var auth: FirebaseAuth

    private val imageFacebook: ImageView by lazy { findViewById<ImageView>(R.id.imgLoginFacebook) }
    private lateinit var callbackManager: CallbackManager

    private val imageGoogle: ImageView by lazy { findViewById<ImageView>(R.id.imgLoginGoogle) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()

        imageFacebook.setOnClickListener { loginFacebook() }

        imageGoogle.setOnClickListener { signIn() }

        validaCampos()

        val textAlterarSenha = findViewById<TextView>(R.id.btnEsqueciSenha)
        textAlterarSenha.setOnClickListener {
            Toast.makeText(this, "Alterar senha", Toast.LENGTH_SHORT).show()
        }

        val textIrProCadastro = findViewById<TextView>(R.id.btnNaoTenhoCadastro)
        textIrProCadastro.setOnClickListener {
            val intent = Intent(this@LoginActivity, CadastroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // começa as configs pro login social com o facebook

    private fun irParaHome(uiid: String) {
        AppUtil.salvarIdUsuario(application.applicationContext, uiid)
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        finish()
    }

    private fun loginFacebook() {
        val instanceFirebase = LoginManager.getInstance()

        instanceFirebase.logInWithReadPermissions(this, listOf("email", "public_profile"))
        instanceFirebase.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                val credential: AuthCredential =
                    FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { irParaHome(loginResult.accessToken.userId) }

                LOGIN_TYPE = "FACEBOOK"
            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "Cancelado!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // configurações pra login no google

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                    LOGIN_TYPE = "GOOGLE"
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("MainActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("MainActivity", exception.toString())
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Valide seu email", Toast.LENGTH_LONG).show()
                auth.signOut()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "Erro ao logar", Toast.LENGTH_LONG).show()
                }
            }
    }

    // configuração login com email e senha

    private fun validaCampos() {
        val buttonLogin = findViewById<TextView>(R.id.btnLogin)
        buttonLogin.setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmailLogin).text.toString()
            val senha = findViewById<EditText>(R.id.etSenhaLogin).text.toString()

            if (checarCamposVazios(email, senha)) {
                firebaseLoginSenha(email, senha)
            }
        }
    }


    private fun checarCamposVazios(email: String, senha: String): Boolean {

        if (email.trim().isEmpty()) {
            findViewById<EditText>(R.id.etEmailLogin).error = CadastroActivity.ERRO_VAZIO
            return false
        } else if (senha.trim().isEmpty()) {
            findViewById<EditText>(R.id.etSenhaLogin).error = CadastroActivity.ERRO_VAZIO
            return false
        }
        return true
    }

    private fun firebaseLoginSenha(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user!!.isEmailVerified) {
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        LOGIN_TYPE = "SENHA"
                    } else Toast.makeText(baseContext, "Falha de autenticação", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(baseContext, "Falha de autenticação", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 1
    }
}