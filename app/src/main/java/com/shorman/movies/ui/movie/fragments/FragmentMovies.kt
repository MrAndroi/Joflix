package com.shorman.movies.ui.movie.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.shorman.movies.others.Constans.FOOTER_VIEW_TYPE
import com.shorman.movies.R
import com.shorman.movies.adapters.LoadStatusAdapter
import com.shorman.movies.ui.movie.adapters.MoviesAdapter
import com.shorman.movies.ui.fragments.FindMoviesFragmentDirections
import com.shorman.movies.viewModels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class FragmentMovies:Fragment(R.layout.movies_fragment) {

    private lateinit var moviesViewModel:MoviesViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)

        moviesAdapter = MoviesAdapter{
            moviesViewModel.changeMovieID(it)
            val directions = FindMoviesFragmentDirections.actionFindMoviesFragmentToMovieDetailsFragment(it)
            findNavController().navigate(directions)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpObservers()

        swipeToRefreshMovies.setOnRefreshListener {
            refreshMovies()
        }

        setUpRecyclerViewAdapter()

    }

    private fun setUpRecyclerView(){
        val gridLayoutManager = GridLayoutManager(requireContext(),2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = moviesAdapter.getItemViewType(position)
                return if(viewType == FOOTER_VIEW_TYPE) 1
                else 2
            }

        }
        rvShows.setHasFixedSize(true)
        rvShows.layoutManager = gridLayoutManager
        rvShows.adapter = moviesAdapter.withLoadStateFooter(
            footer = LoadStatusAdapter{moviesAdapter.retry()}
        )
    }

    private fun setUpRecyclerViewAdapter(){
        moviesAdapter.addLoadStateListener { loadState ->
            progressBarMovies.isVisible = loadState.source.refresh is LoadState.Loading
            rvShows.isVisible = loadState.source.refresh is LoadState.NotLoading

            if(loadState.source.refresh is LoadState.Error){
                tvError.isVisible = true
                networkAnimation.isVisible = true
                networkAnimation.playAnimation()
            }
            else{
                tvError.isVisible = false
                networkAnimation.isVisible = false
                networkAnimation.pauseAnimation()
            }

            if(loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached && moviesAdapter.itemCount < 1){

                rvShows.isVisible = false
                tvNoResultsMovies.isVisible = true
                searchAnimation.isVisible = true
                searchAnimation.playAnimation()

            }
            else{
                tvNoResultsMovies.isVisible = false
                searchAnimation.isVisible = false
                searchAnimation.pauseAnimation()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpObservers(){
        moviesViewModel.latestMovies.observe(viewLifecycleOwner){ movies ->
            moviesAdapter.submitData(viewLifecycleOwner.lifecycle,movies)
        }

        moviesViewModel.currentQuery.observe(viewLifecycleOwner){
            tvNoResultsMovies.text = "No results found for \"${it}\""
        }
    }

    private fun refreshMovies(){
        CoroutineScope(Dispatchers.Main).launch {
            moviesViewModel.searchKeyWord("")
            swipeToRefreshMovies.isRefreshing = false
        }
    }

}