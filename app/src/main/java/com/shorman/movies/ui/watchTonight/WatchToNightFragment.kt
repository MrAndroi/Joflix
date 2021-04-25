package com.shorman.movies.ui.watchTonight

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.shorman.movies.R
import com.shorman.movies.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.watch_tonight_fragment.*

@AndroidEntryPoint
class WatchToNightFragment():Fragment(R.layout.watch_tonight_fragment) {

    private val  fragmentList:ArrayList<Fragment> by lazy {
        arrayListOf(UserRequirmentsFragment(), LookingForMovieFragment())
    }
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle,fragmentList)
        watchToNightPager.adapter = pagerAdapter

    }


}