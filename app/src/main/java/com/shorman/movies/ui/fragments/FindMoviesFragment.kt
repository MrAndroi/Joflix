package com.shorman.movies.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

@AndroidEntryPoint
class FindMoviesFragment: Fragment(R.layout.find_movies_fragment) {

    private val  fragmentList:ArrayList<Fragment> by lazy {
        arrayListOf(FragmentMovies(), FragmentTv())
    }
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var moviesViewModel:MoviesViewModel
    private lateinit var tvShowsViewModel: TvShowsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        tvShowsViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel.isNetworkAvailable.observe(viewLifecycleOwner){
            if(it){
                hideNetworkError()
            }
            else{
                showNetworkError()
            }
        }

        pagerAdapter = ViewPagerAdapter(childFragmentManager,lifecycle,fragmentList)
        pager.adapter = pagerAdapter


        etFindMoviesAndTvShows.beforeTextChanged {
            moviesViewModel.searchKeyWord(it)
            tvShowsViewModel.updateSearchQuery(it)
        }

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
    }

    private fun showNetworkError(){
        etFindMoviesAndTvShows.isVisible = false
        tvFindMovies.isVisible = false
        pager.isVisible = false
        tab_layout.isVisible = false
        networkAnimation.isVisible = true
        tvError.isVisible =true
        networkAnimation.playAnimation()
        tvError.text = "This feature is not available without internet connection!"
    }

    private fun hideNetworkError(){
        etFindMoviesAndTvShows.isVisible = true
        tvFindMovies.isVisible = true
        pager.isVisible = true
        tab_layout.isVisible = true
        networkAnimation.isVisible = false
        tvError.isVisible = false
        networkAnimation.pauseAnimation()
    }
}