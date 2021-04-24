package com.shorman.movies.ui.watchTonight

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.shorman.movies.R
import com.shorman.movies.api.MoviesApi
import com.shorman.movies.api.models.movie.MovieModel
import com.shorman.movies.api.models.movie.MovieSearchModel
import com.shorman.movies.others.Constans.API_KEY
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import com.shorman.movies.utils.Status
import com.shorman.movies.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.looking_for_movie_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LookingForMovieFragment ():Fragment(R.layout.looking_for_movie_fragment) {


    lateinit var mainViewModel: MainViewModel
    lateinit var userRequirments: MovieSearchModel
    lateinit var moviesList:ArrayList<MovieModel>
    lateinit var randomMovie:MovieModel

    @Inject
    lateinit var moviesApi:MoviesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        moviesList = ArrayList()
        userRequirments = MovieSearchModel()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarSearchingForMovie.visibility = View.VISIBLE

        mainViewModel.getRandomMovies(
            userRequirments.language,
            userRequirments.genres,
            userRequirments.watchType,
            userRequirments.minimumReleaseDate,
            userRequirments.minimumRating
        )

        foundShowImage.setOnClickListener {
            mainViewModel.changeMovieID(randomMovie.id)
            val direction = WatchToNightFragmentDirections.actionWatchToNightFragmentToMovieDetailsFragment(randomMovie.id)
            findNavController().navigate(direction)
        }

    }

    private fun getRandomMovie(){
        mainViewModel.randomMovieList.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING->{}
                Status.SUCCESS ->{
                    it.data?.let { moviesResponse ->
                        CoroutineScope(Dispatchers.Main).launch {
                            moviesList.clear()
                            moviesList.addAll(moviesResponse.results)
                            addMoreMovies(moviesResponse.total_pages)
                            randomMovie = selectOneMovie(moviesList)
                            tvRandomNames.text = ""
                            hideSearchWithAnimation()
                            showFoundMovie(randomMovie)
                            progressBarSearchingForMovie.visibility = View.INVISIBLE
                        }
                    }
                }
                Status.ERROR ->{}
            }
        }
        mainViewModel.movieSearchModel.observe(viewLifecycleOwner){
            userRequirments = it
        }
    }

    private suspend fun addMoreMovies(pageNumbers:Int){
        if(pageNumbers > 10){
                for (i in 0..10){
                    val randomMoviesPage = moviesApi.getRandomMovie2(
                        userRequirments.language,
                        userRequirments.genres,
                        userRequirments.watchType,
                        userRequirments.minimumReleaseDate,
                        userRequirments.minimumRating,
                        API_KEY,
                        i
                    )
                    if(randomMoviesPage.isSuccessful){
                        moviesList.addAll(randomMoviesPage.body()?.results!!)
                    }

                }
            }
        else{
                for (i in 0..pageNumbers){
                    val randomMoviesPage = moviesApi.getRandomMovie2(
                        userRequirments.language,
                        userRequirments.genres,
                        userRequirments.watchType,
                        userRequirments.minimumReleaseDate,
                        userRequirments.minimumRating,
                        API_KEY,
                        i
                    )
                    if(randomMoviesPage.isSuccessful){
                        moviesList.addAll(randomMoviesPage.body()?.results!!)
                    }
                }
            }
    }

    private suspend fun selectOneMovie(list:ArrayList<MovieModel>):MovieModel{
        try {
            list.forEach {
                delay(10)
                tvRandomNames.text = it.original_title
            }
        }catch (e:java.util.ConcurrentModificationException){
            Log.e("for loop error",e.toString())
        }
        val randomNumber = (0 until list.size).random()
        return list[randomNumber]
    }

    private fun hideSearchWithAnimation(){
        searchingForShow.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(0f)
            scaleX(0f)
            scaleY(0f)
            startDelay = 500
            start()
        }

        tvSearching.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(0f)
            scaleX(0f)
            scaleY(0f)
            startDelay = 500
            start()
        }
    }

    private fun showFoundMovie(movieModel: MovieModel){
        foundShowImage.load(IMAGES_BASE_URL+movieModel.poster_path){
            placeholder(R.drawable.place_holder)
            error(R.drawable.place_holder)
        }
        foundMovieName.text = movieModel.original_title
        foundShowImage.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(1f)
            scaleX(1f)
            scaleY(1f)
            startDelay = 500
            start()
        }
        foundMovieName.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(1f)
            scaleX(1f)
            scaleY(1f)
            startDelay = 500
            start()
        }
    }

    override fun onResume() {
        super.onResume()
        getRandomMovie()
    }

    override fun onPause() {
        super.onPause()
        tvRandomNames.text = ""
    }

}