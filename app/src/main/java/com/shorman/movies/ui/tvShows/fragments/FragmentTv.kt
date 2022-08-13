package com.shorman.movies.ui.tvShows.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.LoadStates
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shorman.movies.R
import com.shorman.movies.adapters.LoadStatusAdapter
import com.shorman.movies.others.Constans.FOOTER_VIEW_TYPE
import com.shorman.movies.ui.fragments.FindMoviesFragmentDirections
import com.shorman.movies.ui.tvShows.adapters.TvShowsAdapter
import com.shorman.movies.utils.hideKeyboard
import com.shorman.movies.viewModels.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.find_movies_fragment.*
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.tv_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentTv:Fragment(R.layout.tv_fragment) {

    private lateinit var tvShowsViewModel:TvShowsViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var tvShowsAdapter: TvShowsAdapter
    private lateinit var onScrollListener: RecyclerView.OnScrollListener
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowsViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
        gridLayoutManager = GridLayoutManager(requireContext(),2)
        tvShowsAdapter = TvShowsAdapter {
            val direction = FindMoviesFragmentDirections.actionFindMoviesFragmentToFragmentTvShowDetails(it)
            findNavController().navigate(direction)
        }
        onScrollListener = object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    hideKeyboard()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(gridLayoutManager.findFirstVisibleItemPosition() != 0){
                    gridLayoutManager.smoothScrollToPosition(rvTvShows,RecyclerView.State(),0)
                }
                else{
                    activity?.finish()
                }
            }
        }
        activity?.onBackPressedDispatcher!!.addCallback(onBackPressedCallback)

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
        rvTvShows.addOnScrollListener(onScrollListener)

    }

    private fun setUpTvShowsAdapter(){
        tvShowsAdapter.addLoadStateListener { loadState ->
            progressBarTv.isVisible = loadState.source.refresh is LoadState.Loading
            rvTvShows.isVisible = loadState.source.refresh is LoadState.NotLoading

            if(loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached && tvShowsAdapter.itemCount < 1){

                    rvTvShows.isVisible = false
                    tvNoResultsTv.isVisible = true
                    searchAnimationTv.isVisible = true
                    searchAnimationTv.playAnimation()
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
            swipeToRefreshTv.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvTvShows.removeOnScrollListener(onScrollListener)
        onBackPressedCallback.remove()
        onBackPressedCallback.isEnabled= false
    }
}