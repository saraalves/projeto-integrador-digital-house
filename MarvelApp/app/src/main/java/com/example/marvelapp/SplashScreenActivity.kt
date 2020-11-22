package com.example.marvelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        animaImagem()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    fun animaImagem(){
        val appLogo = findViewById<LinearLayout>(R.id.imgSplash)
        appLogo.alpha = 0f
        appLogo.y = 900f

        appLogo.animate()
            .alpha(1f)
            .y(250f)
            .setDuration(2000)
            .setListener(null)
    }
}