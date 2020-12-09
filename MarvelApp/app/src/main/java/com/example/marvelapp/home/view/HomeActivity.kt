package com.example.marvelapp.home.view

import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.marvelapp.R
import com.example.marvelapp.favoritos.view.FavoritosFragment
import com.example.marvelapp.perfil.PerfilFragment
import com.google.android.material.tabs.TabLayout

const val HOME_FRAGMENT = 0
const val FAVORITOS_FRAGMENT = 1
const val PERFIL_FRAGMENT = 2

class HomeActivity : AppCompatActivity() {

    private val _tabLayout by lazy { findViewById<TabLayout>(R.id.tabLayout) }

    private lateinit var _homeFragment: HomeFragment

    private val _tabIcons = intArrayOf(
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

        _tabLayout.setupWithViewPager(pager)

        _homeFragment = HomeFragment()

        pager.adapter = ViewPagerAdapter(
            listOf(
                _homeFragment,
                FavoritosFragment(),
                PerfilFragment()
            ),
            supportFragmentManager
        )
    }

    private fun setupTabIcons() {
        _tabLayout.getTabAt(0)?.setIcon(_tabIcons[0])
        _tabLayout.getTabAt(1)?.setIcon(_tabIcons[1])
        _tabLayout.getTabAt(2)?.setIcon(_tabIcons[2])
    }
}