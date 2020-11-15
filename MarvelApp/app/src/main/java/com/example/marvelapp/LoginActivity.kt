package com.example.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
    }
}