package com.jenandsara.marvelapp.v2.presentation.activities

import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.jenandsara.marvelapp.v2.presentation.fragments.FavoritosFragment
import com.jenandsara.marvelapp.v2.presentation.fragments.PerfilFragment
import com.google.android.material.tabs.TabLayout
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.v2.presentation.ViewPagerAdapter
import com.jenandsara.marvelapp.v2.presentation.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_home.*

const val HOME_FRAGMENT = 0
const val FAVORITOS_FRAGMENT = 1
const val PERFIL_FRAGMENT = 2

class HomeActivity : AppCompatActivity() {

    private val _tabLayout by lazy { findViewById<TabLayout>(R.id.tabLayout) }

    private lateinit var _homeFragment: HomeFragment
    private lateinit var _favoritosFragment: FavoritosFragment
    private lateinit var _perfilFragment: PerfilFragment

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

        val _pager = findViewById<ViewPager>(R.id.viewPager)

        _tabLayout.setupWithViewPager(_pager)

        _homeFragment = HomeFragment()
        _favoritosFragment = FavoritosFragment()
        _perfilFragment = PerfilFragment()

       val listaFragmentos = listOf(
                _homeFragment,
                _favoritosFragment,
                _perfilFragment
        )

        _pager.adapter = ViewPagerAdapter(listaFragmentos, supportFragmentManager)

    }

    private fun setupTabIcons() {
        _tabLayout.getTabAt(0)?.setIcon(_tabIcons[0])
        _tabLayout.getTabAt(1)?.setIcon(_tabIcons[1])
        _tabLayout.getTabAt(2)?.setIcon(_tabIcons[2])
    }

    override fun onBackPressed() {
        when (tabLayout.selectedTabPosition) {
            0 -> super.onBackPressed()
            1 -> _tabLayout.getTabAt(0)!!.select()
            2 -> _tabLayout.getTabAt(1)!!.select()
        }
    }
}