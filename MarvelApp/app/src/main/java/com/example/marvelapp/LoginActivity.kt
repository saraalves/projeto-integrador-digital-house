package com.example.marvelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.widget.TextView
import com.example.marvelapp.view.HomeActivity
import com.example.marvelapp.view.HomeFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<TextView>(R.id.btnLogin)
        buttonLogin.setOnClickListener{
            val intent = Intent(this@LoginActivity, HomeActivity::class.java )
            startActivity(intent)
        }
    }
}