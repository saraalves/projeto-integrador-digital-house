package com.jenandsara.marvelapp.v2.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.jenandsara.marvelapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        animaImagem()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    fun animaImagem() {
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