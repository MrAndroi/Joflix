package com.shorman.movies.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.shorman.movies.R
import com.shorman.movies.adapters.ViewPagerAdapter
import com.shorman.movies.ui.movie.fragments.SavedMoviesFragment
import com.shorman.movies.ui.tvShows.fragments.SavedTvShowsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.saved_fragment.*

@AndroidEntryPoint
class SavedFragment:Fragment(R.layout.saved_fragment) {

    private val  fragmentList:ArrayList<Fragment> by lazy {
        arrayListOf(SavedMoviesFragment(), SavedTvShowsFragment())
    }
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle,fragmentList)
        savedPager.adapter = pagerAdapter

        TabLayoutMediator(tab_layout_saved, savedPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Movies"
                }
                1 -> {
                    tab.text = "Tv shows"
                }
            }
        }.attach()
    }


}