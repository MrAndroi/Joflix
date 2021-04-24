package com.shorman.movies.ui.movie.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.shorman.movies.R
import com.shorman.movies.adapters.CommentsAdapter
import com.shorman.movies.adapters.LoadStatusAdapter
import com.shorman.movies.ui.movie.adapters.MovieImageAdapter
import com.shorman.movies.api.models.movie.MovieDetails
import com.shorman.movies.utils.Status
import com.shorman.movies.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_two.*


@AndroidEntryPoint
class MovieDetailsTwoFragment(private val movieId: Int):Fragment(R.layout.movie_details_two) {

    private lateinit var mainViewModel: MainViewModel
    lateinit var movieImageAdapter: MovieImageAdapter
    lateinit var commentsAdapter: CommentsAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.getMovieImages(movieId)
        movieImageAdapter = MovieImageAdapter()
        commentsAdapter = CommentsAdapter()
        linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setUpObservers()
        setUpCommentsRecyclerViewAdapter()

    }

    private fun setupRecyclerViews(){
        rvBackdrops.setHasFixedSize(true)
        rvReviews.setHasFixedSize(true)
        ViewCompat.setNestedScrollingEnabled(rvReviews,true)
        rvReviews.layoutManager = linearLayoutManager
        rvBackdrops.adapter = movieImageAdapter
        rvReviews.adapter = commentsAdapter.withLoadStateFooter(
            footer = LoadStatusAdapter { commentsAdapter.retry() }
        )
    }

    private fun setUpCommentsRecyclerViewAdapter(){
        commentsAdapter.addLoadStateListener { loadState ->
            progressBarMovieDetailsTwo.isVisible = loadState.source.refresh is LoadState.Loading
            rvReviews.isVisible = loadState.source.refresh is LoadState.NotLoading

            if(loadState.source.refresh is LoadState.Error){
                tvErrorMovieDetailsTwo.isVisible = true
                tvErrorMovieDetailsTwo.text = "Something went wrong!"
            }
            else{
                tvErrorMovieDetailsTwo.isVisible = false
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

    private fun setUpObservers(){
        mainViewModel.currentMovieDetails.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    progressBarMovieDetailsTwo.isVisible = true
                }
                Status.SUCCESS -> {
                    it.data?.let { movieDetails ->
                        getProductionCompanies(movieDetails)
                        progressBarMovieDetailsTwo.isVisible = false
                    }
                }
                Status.ERROR -> {
                    tvErrorMovieDetailsTwo.isVisible = true
                    tvErrorMovieDetailsTwo.text = it.message
                    progressBarMovieDetailsTwo.isVisible = false
                }
            }
        }

        mainViewModel.currentMovieImages.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    progressBarMovieDetailsTwo.isVisible = true
                }
                Status.SUCCESS -> {
                    it.data?.let { movieImagesList ->
                        movieImageAdapter.differ.submitList(movieImagesList.backdrops)
                        progressBarMovieDetailsTwo.isVisible = false
                    }
                }
                Status.ERROR -> {
                    tvErrorMovieDetailsTwo.isVisible = true
                    tvErrorMovieDetailsTwo.text = it.message
                    progressBarMovieDetailsTwo.isVisible = false
                }
            }
        }

        mainViewModel.movieComments.observe(viewLifecycleOwner){comments ->
            commentsAdapter.submitData(viewLifecycleOwner.lifecycle,comments)
        }
    }

    private fun getProductionCompanies(movieDetails:MovieDetails){
        if(movieDetails.production_companies.size > 2){
            movieDetails.production_companies.subList(0,2).forEach {companies ->
                val chip = layoutInflater.inflate(
                    R.layout.company_item,
                    companiesChips,
                    false
                ) as Chip
                chip.text = companies.name
                chip.setOnClickListener {
                    val url = "https://www.google.com/search?q=${companies.name}+production company"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
                companiesChips.addView(chip)
            }
        }
        else{
            movieDetails.production_companies.forEach { companies ->
                val chip = layoutInflater.inflate(
                    R.layout.company_item,
                    companiesChips,
                    false
                ) as Chip
                chip.text = companies.name
                chip.setOnClickListener {
                    val url = "https://www.google.com/search?q=${companies.name}+production company"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
                companiesChips.addView(chip)
            }
        }
    }

    private fun showNoComments(){
        noCommentsAnimation.isVisible = true
        noComments.isVisible = true
        rvReviews.isVisible = false
        noCommentsAnimation.playAnimation()
    }

    private fun hideNoComments(){
        noCommentsAnimation.isVisible = false
        noComments.isVisible = false
        rvReviews.isVisible = true
        noCommentsAnimation.pauseAnimation()
    }
}