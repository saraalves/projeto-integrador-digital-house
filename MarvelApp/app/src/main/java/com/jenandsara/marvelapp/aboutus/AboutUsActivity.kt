package com.jenandsara.marvelapp.aboutus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar
import com.jenandsara.marvelapp.R

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarAboutUs)

        toolbar.setOnClickListener {
            onBackPressed()
            finish()
        }

        setupHyperlink()
    }

    private fun setupHyperlink() {
        val linkGithubJenn = findViewById<TextView>(R.id.txtGithubJenn)
        val linkGithubSara = findViewById<TextView>(R.id.txtGithubSara)
        linkGithubJenn.movementMethod = LinkMovementMethod.getInstance()
        linkGithubSara.movementMethod = LinkMovementMethod.getInstance()
    }
}