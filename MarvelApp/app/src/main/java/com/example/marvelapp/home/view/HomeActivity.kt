package com.example.marvelapp.home.view

import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.marvelapp.R
import com.example.marvelapp.favoritos.FavoritosFragment
import com.example.marvelapp.perfil.PerfilFragment
import com.google.android.material.tabs.TabLayout


const val HOME_FRAGMENT = 0
const val FAVORITOS_FRAGMENT = 1
const val PERFIL_FRAGMENT = 2

class HomeActivity : AppCompatActivity() {

    private val tabLayout by lazy { findViewById<TabLayout>(R.id.tabLayout) }

    private lateinit var homeFragment: HomeFragment

    private val tabIcons = intArrayOf(
        R.drawable.ic_home_24,
        R.drawable.ic_favorite_gray_24,
        R.drawable.ic_person_gray_24
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_home)

        configViewPager()
        setupTabIcons()

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

    private fun setupTabIcons(){
        tabLayout.getTabAt(0)?.setIcon(tabIcons[0])
        tabLayout.getTabAt(1)?.setIcon(tabIcons[1])
        tabLayout.getTabAt(2)?.setIcon(tabIcons[2])
    }
}