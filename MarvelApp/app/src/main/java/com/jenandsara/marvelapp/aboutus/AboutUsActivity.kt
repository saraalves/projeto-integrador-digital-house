package com.jenandsara.marvelapp.aboutus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import com.jenandsara.marvelapp.R

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarAboutUs)

        toolbar.setOnClickListener{
            onBackPressed()
            finish()
        }

    }
}