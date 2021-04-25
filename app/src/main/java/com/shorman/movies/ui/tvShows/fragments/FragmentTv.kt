package com.shorman.movies.ui.tvShows.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.LoadStates
import androidx.recyclerview.widget.GridLayoutManager
import com.shorman.movies.R
import com.shorman.movies.adapters.LoadStatusAdapter
import com.shorman.movies.others.Constans.FOOTER_VIEW_TYPE
import com.shorman.movies.ui.fragments.FindMoviesFragmentDirections
import com.shorman.movies.ui.tvShows.adapters.TvShowsAdapter
import com.shorman.movies.viewModels.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.tv_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentTv:Fragment(R.layout.tv_fragment) {

    lateinit var tvShowsViewModel:TvShowsViewModel
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var tvShowsAdapter: TvShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowsViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
        gridLayoutManager = GridLayoutManager(requireContext(),2)
        tvShowsAdapter = TvShowsAdapter {
            tvShowsViewModel.updateCurrentShowID(it)
            val direction = FindMoviesFragmentDirections.actionFindMoviesFragmentToFragmentTvShowDetails(it)
            findNavController().navigate(direction)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpTvShowsAdapter()
        setUpTvShowsRecyclerView()

        swipeToRefreshTv.setOnRefreshListener {
            refreshTvShows()
        }

    }

    private fun setUpObservers(){
        tvShowsViewModel.tvShowsList.observe(viewLifecycleOwner){
            tvShowsAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        tvShowsViewModel.currentQuery.observe(viewLifecycleOwner){
            tvNoResultsTv.text = "No results found for \"${it}\""
        }
    }

    private fun setUpTvShowsRecyclerView(){
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = tvShowsAdapter.getItemViewType(position)
                return if(viewType == FOOTER_VIEW_TYPE) 1
                else 2
            }
        }

        rvTvShows.setHasFixedSize(true)
        rvTvShows.layoutManager = gridLayoutManager
        rvTvShows.adapter = tvShowsAdapter.withLoadStateFooter(
            footer = LoadStatusAdapter{
                tvShowsAdapter.retry()
            }
        )

    }

    private fun setUpTvShowsAdapter(){
        tvShowsAdapter.addLoadStateListener { loadState ->
            progressBarTv.isVisible = loadState.source.refresh is LoadState.Loading
            rvTvShows.isVisible = loadState.source.refresh is LoadState.NotLoading

            if(loadState.source.refresh is LoadState.Error){
                tvErrorTvShows.isVisible = true
                networkAnimationTv.isVisible = true
                networkAnimationTv.playAnimation()
            }
            else{
                tvErrorTvShows.isVisible = false
                networkAnimationTv.isVisible = false
                networkAnimationTv.pauseAnimation()

            }

            if(loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached && tvShowsAdapter.itemCount < 1){

                rvShows.isVisible = false
                tvNoResultsMovies.isVisible = true
                searchAnimation.isVisible = true
                searchAnimation.playAnimation()

            }
            else{
                tvNoResultsTv.isVisible = false
                searchAnimationTv.isVisible = false
                searchAnimationTv.pauseAnimation()
            }

        }
    }

    private fun refreshTvShows(){
        CoroutineScope(Dispatchers.Main).launch {
            tvShowsViewModel.updateSearchQuery("")
            swipeToRefreshMovies.isRefreshing = false
        }
    }
}