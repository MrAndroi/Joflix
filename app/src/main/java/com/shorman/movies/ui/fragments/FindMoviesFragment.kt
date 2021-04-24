package com.shorman.movies.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.shorman.movies.R
import com.shorman.movies.adapters.ViewPagerAdapter
import com.shorman.movies.ui.movie.fragments.FragmentMovies
import com.shorman.movies.ui.tvShows.FragmentTv
import com.shorman.movies.utils.beforeTextChanged
import com.shorman.movies.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.find_movies_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindMoviesFragment: Fragment(R.layout.find_movies_fragment) {

    private val  fragmentList:ArrayList<Fragment> by lazy {
        arrayListOf(FragmentMovies(), FragmentTv())
    }
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var mainViewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle,fragmentList)
        pager.adapter = pagerAdapter

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Movies"
                }
                1 -> {
                    tab.text = "Tv shows"
                }
            }
        }.attach()

        etFindMoviesAndTvShows.beforeTextChanged {
            CoroutineScope(Dispatchers.Main).launch {
                mainViewModel.searchKeyWord(it)
            }

        }

    }
}