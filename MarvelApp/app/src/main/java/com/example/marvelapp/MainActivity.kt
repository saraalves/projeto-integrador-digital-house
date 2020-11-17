package com.example.marvelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.helloWorld)
        text.setOnClickListener{
            val intent = Intent(this@MainActivity, LoginActivity::class.java )
            startActivity(intent)
        }

        val textOne = findViewById<TextView>(R.id.telaAlterar)
        textOne.setOnClickListener {
            val intent = Intent(this@MainActivity, AlterarSenhaActivity::class.java)
            startActivity(intent)
        }
    }
}