package com.jenandsara.marvelapp.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.databinding.LoginActivityBinding
import com.jenandsara.marvelapp.databinding.SplashScreenActivityBinding

class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        SplashScreenActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        animaImagem()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    fun animaImagem() {
        binding.imgSplash.alpha = 0f
        binding.imgSplash.y = 900f

        binding.imgSplash.animate()
            .alpha(1f)
            .y(250f)
            .setDuration(2000)
            .setListener(null)
    }
}