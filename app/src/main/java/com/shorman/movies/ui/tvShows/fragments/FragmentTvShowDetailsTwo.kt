package com.shorman.movies.ui.tvShows.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.shorman.movies.R
import com.shorman.movies.adapters.CommentsAdapter
import com.shorman.movies.adapters.LoadStatusAdapter
import com.shorman.movies.ui.tvShows.adapters.SeasonAdapter
import com.shorman.movies.utils.Status
import com.shorman.movies.viewModels.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.tv_details_two.*

@AndroidEntryPoint
class FragmentTvShowDetailsTwo:Fragment(R.layout.tv_details_two) {

    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowsViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
        seasonAdapter = SeasonAdapter()
        commentsAdapter = CommentsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSeasonsRecyclerView()
        setUpCommentsRecyclerView()
        setUpObservers()
        setUpCommentsRecyclerViewAdapter()

    }

    private fun setUpObservers(){
        tvShowsViewModel.currentShowDetails.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING ->{
                    progressBarTvDetailsTwo.isVisible = true
                }
                Status.SUCCESS ->{
                    it.data?.let { tvShowDetails ->
                        seasonAdapter.differ.submitList(tvShowDetails.seasons.filter { season ->
                            season.season_number != 0
                        })
                        progressBarTvDetailsTwo.isVisible = false
                    }
                }
                Status.ERROR -> {
                    Snackbar.make(requireView(),"Error:${it.message}",2000).show()
                    progressBarTvDetailsTwo.isVisible = false
                }
            }
        }

        tvShowsViewModel.commentsList.observe(viewLifecycleOwner){
            commentsAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

    private fun setUpSeasonsRecyclerView(){
        rvSeasons.setHasFixedSize(true)
        rvSeasons.adapter = seasonAdapter
    }

    private fun setUpCommentsRecyclerView(){
        rvTvShowReviews.setHasFixedSize(true)
        ViewCompat.setNestedScrollingEnabled(rvTvShowReviews,true)
        rvTvShowReviews.adapter = commentsAdapter.withLoadStateFooter(
            footer = LoadStatusAdapter{commentsAdapter.retry()}
        )
    }

    private fun setUpCommentsRecyclerViewAdapter(){
        commentsAdapter.addLoadStateListener { loadState ->
            progressBarTvDetailsTwo.isVisible = loadState.source.refresh is LoadState.Loading
            rvTvShowReviews.isVisible = loadState.source.refresh is LoadState.NotLoading

            if(loadState.source.refresh is LoadState.Error){
                Snackbar.make(requireView(),"Something went error!",2000).show()
            }
            if(loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached && commentsAdapter.itemCount < 1){
                showNoComments()
            }
            else{
                hideNoComments()
            }
        }
    }

    private fun showNoComments(){
        noCommentsAnimationTv.isVisible = true
        noCommentsTv.isVisible = true
        rvTvShowReviews.isVisible = false
        noCommentsAnimationTv.playAnimation()
    }

    private fun hideNoComments(){
        noCommentsAnimationTv.isVisible = false
        noCommentsTv.isVisible = false
        rvTvShowReviews.isVisible = true
        noCommentsAnimationTv.pauseAnimation()
    }

}