package com.shorman.movies.ui.tvShows.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.shorman.movies.R
import com.shorman.movies.ui.fragments.SavedFragmentDirections
import com.shorman.movies.ui.tvShows.adapters.SavedTvShowsAdapter
import com.shorman.movies.viewModels.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.saved_movies_fragment.*
import kotlinx.android.synthetic.main.saved_tv_shows_fragment.*

@AndroidEntryPoint
class SavedTvShowsFragment:Fragment(R.layout.saved_tv_shows_fragment) {

    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var savedTvShowsAdapter: SavedTvShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowsViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
        savedTvShowsAdapter = SavedTvShowsAdapter({id ->
            val direction = SavedFragmentDirections.actionSavedFragmentToFragmentTvShowDetails(id)
            findNavController().navigate(direction)
        },
            {tvShow ->
            tvShowsViewModel.deleteTvShow(tvShow)
            Snackbar.make(requireView(),"${tvShow.original_name} removed successfully",2500)
                .setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                .setBackgroundTint(ContextCompat.getColor(requireContext(),R.color.medium_orange))
                .setActionTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                .setAction("Undo"){
                    tvShowsViewModel.insertTvShow(tvShow)
                }
                .show()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpObservers()
    }

    private fun setUpRecyclerView(){
        rvSavedTvShows.setHasFixedSize(true)
        rvSavedTvShows.adapter = savedTvShowsAdapter
    }

    private fun setUpObservers(){
        tvShowsViewModel.savedTvShows.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                noSavedTvShows.isVisible = true
                rvSavedTvShows.isVisible = false
            }
            else {
                noSavedTvShows.isVisible = false
                rvSavedTvShows.isVisible = true
                savedTvShowsAdapter.differ.submitList(it)
            }
        }
    }
}