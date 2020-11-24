package com.example.marvelapp.home.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    private val _fragments: List<Fragment>,
    private val _titulos: List<String>,
    manager: FragmentManager
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = _fragments[position]

    override fun getCount() = _fragments.size

    override fun getPageTitle(position: Int) = _titulos[position]
}