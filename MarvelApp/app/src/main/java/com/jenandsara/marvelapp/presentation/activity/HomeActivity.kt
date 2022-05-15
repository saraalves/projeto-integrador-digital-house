package com.jenandsara.marvelapp.presentation.activity

import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import androidx.appcompat.app.AppCompatActivity
import com.jenandsara.marvelapp.R
import com.jenandsara.marvelapp.databinding.HomeActivityBinding
import com.jenandsara.marvelapp.presentation.fragment.FavoritosFragment
import com.jenandsara.marvelapp.presentation.fragment.HomeFragment
import com.jenandsara.marvelapp.presentation.fragment.PerfilFragment

const val HOME_FRAGMENT = 0
const val FAVORITOS_FRAGMENT = 1
const val PERFIL_FRAGMENT = 2

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        HomeActivityBinding.inflate(layoutInflater)
    }

    private val tabLayout by lazy { binding.tabLayout }

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
        setContentView(binding.root)

        configViewPager()
        setupTabIcons()

    }

    private fun configViewPager() {

        tabLayout.setupWithViewPager(binding.viewPager)

        _homeFragment = HomeFragment()
        _favoritosFragment = FavoritosFragment()
        _perfilFragment = PerfilFragment()

       val listaFragmentos = listOf(
                _homeFragment,
                _favoritosFragment,
                _perfilFragment
        )

        binding.viewPager.adapter = ViewPagerAdapter(listaFragmentos, supportFragmentManager)

    }

    private fun setupTabIcons() {
        tabLayout.getTabAt(0)?.setIcon(_tabIcons[0])
        tabLayout.getTabAt(1)?.setIcon(_tabIcons[1])
        tabLayout.getTabAt(2)?.setIcon(_tabIcons[2])
    }

    override fun onBackPressed() {
        when (tabLayout.selectedTabPosition) {
            0 -> super.onBackPressed()
            1 -> tabLayout.getTabAt(0)!!.select()
            2 -> tabLayout.getTabAt(1)!!.select()
        }
    }
}