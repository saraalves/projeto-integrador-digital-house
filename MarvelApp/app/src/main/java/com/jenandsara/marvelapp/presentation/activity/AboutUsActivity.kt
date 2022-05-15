package com.jenandsara.marvelapp.presentation.activity

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.jenandsara.marvelapp.databinding.AboutUsActivityBinding

class AboutUsActivity : AppCompatActivity() {

    private val binding by lazy {
        AboutUsActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbarAboutUs.setOnClickListener {
            onBackPressed()
            finish()
        }

        setupHyperlink()
    }

    private fun setupHyperlink() {
        val linkGithubJenn = binding.txtGithubJenn
        val linkGithubSara = binding.txtGithubSara
        linkGithubJenn.movementMethod = LinkMovementMethod.getInstance()
        linkGithubSara.movementMethod = LinkMovementMethod.getInstance()
    }
}