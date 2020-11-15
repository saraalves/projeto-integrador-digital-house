package com.example.marvelapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelapp.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHome, HomeFragment())
            .commit()
    }
}