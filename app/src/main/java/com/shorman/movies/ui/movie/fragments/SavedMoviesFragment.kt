package com.shorman.movies.ui.movie.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.shorman.movies.R
import com.shorman.movies.ui.movie.adapters.SavedMoviesAdapter
import com.shorman.movies.viewModels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.saved_movies_fragment.*

@AndroidEntryPoint
class SavedMoviesFragment : Fragment(R.layout.saved_movies_fragment) {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var savedMoviesAdapter: SavedMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        savedMoviesAdapter = SavedMoviesAdapter(
            { movieID ->

            }, { movieModel ->
                moviesViewModel.deleteMovie(movieModel)
                Snackbar.make(requireView(), "${movieModel.original_title} removed successfully", 2500)
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.medium_orange))
                    .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .setAction("Undo") {
                        moviesViewModel.insertMovie(movieModel)
                    }
                    .show()

            })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpObservers()

    }

    private fun setUpRecyclerView() {
        rvSavedMovies.setHasFixedSize(true)
        rvSavedMovies.adapter = savedMoviesAdapter
    }

    private fun setUpObservers() {
        moviesViewModel.savedMovies.observe(viewLifecycleOwner) {
            savedMoviesAdapter.differ.submitList(it)
        }
    }
}