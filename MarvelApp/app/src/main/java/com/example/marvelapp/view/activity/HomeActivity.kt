package com.example.marvelapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import androidx.viewpager.widget.ViewPager
import com.example.marvelapp.R
import com.example.marvelapp.view.fragment.FavoritosFragment
import com.example.marvelapp.view.fragment.HomeFragment
import com.example.marvelapp.view.fragment.PerfilFragment
import com.example.marvelapp.view.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

const val HOME_FRAGMENT = 0
const val FAVORITOS_FRAGMENT = 1
const val PERFIL_FRAGMENT = 2

class HomeActivity : AppCompatActivity() {

    private val tabLayout by lazy { findViewById<TabLayout>(R.id.tabLayout) }

    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_home)

        configViewPager()

    }

    private fun configViewPager() {
        val pager = findViewById<ViewPager>(R.id.viewPager)

        tabLayout.setupWithViewPager(pager)

        homeFragment = HomeFragment()

        pager.adapter = ViewPagerAdapter(
            listOf(
                homeFragment,
                FavoritosFragment(),
                PerfilFragment()
            ),
            listOf("Home", "Favoritos", "Perfil"),
            supportFragmentManager
        )
    }
}