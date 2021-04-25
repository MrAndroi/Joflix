package com.shorman.movies.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.shorman.movies.R
import com.shorman.movies.adapters.ViewPagerAdapter
import com.shorman.movies.ui.movie.fragments.FragmentMovies
import com.shorman.movies.ui.tvShows.fragments.FragmentTv
import com.shorman.movies.utils.beforeTextChanged
import com.shorman.movies.viewModels.MoviesViewModel
import com.shorman.movies.viewModels.TvShowsViewModel
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
    private lateinit var moviesViewModel:MoviesViewModel
    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var onPageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        tvShowsViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if(position == 0){
                    etFindMoviesAndTvShows.beforeTextChanged {
                        CoroutineScope(Dispatchers.Main).launch {
                            moviesViewModel.searchKeyWord(it)
                        }
                    }
                }
                else{
                    etFindMoviesAndTvShows.beforeTextChanged {
                        CoroutineScope(Dispatchers.Main).launch {
                            tvShowsViewModel.updateSearchQuery(it)
                        }
                    }
                }
            }
        }

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

        pager.registerOnPageChangeCallback(onPageChangeCallback)

    }
}