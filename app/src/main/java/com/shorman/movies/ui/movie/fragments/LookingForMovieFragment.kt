package com.shorman.movies.ui.movie.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
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
import com.shorman.movies.viewModels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.looking_for_movie_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LookingForMovieFragment ():Fragment(R.layout.looking_for_movie_fragment) {


    lateinit var moviesViewModel: MoviesViewModel
    lateinit var userRequirments: MovieSearchModel
    lateinit var moviesList:ArrayList<MovieModel>
    lateinit var randomMovie:MovieModel

    @Inject
    lateinit var moviesApi:MoviesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        moviesList = ArrayList()
        userRequirments = MovieSearchModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRandomMovie()

        foundShowImage.setOnClickListener {
            moviesViewModel.changeMovieID(randomMovie.id)
            val direction = LookingForMovieFragmentDirections.actionLookingForMovieFragmentToMovieDetailsFragment(randomMovie.id)
            findNavController().navigate(direction)
        }

        lookingForMovieBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        getRandomMovies()

        btnRetrySearch.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                 hideFoundMovieWithAnimation()
                 selectOneMovie(moviesList)
            }
        }
    }

    private fun getRandomMovies(){
        moviesViewModel.getRandomMovies(
            userRequirments.language,
            userRequirments.genres,
            userRequirments.watchType,
            userRequirments.minimumReleaseDate,
            userRequirments.minimumRating
        )
    }

    private fun observeRandomMovie(){
        moviesViewModel.randomMovieList.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING->{}
                Status.SUCCESS -> {
                    it.data?.let { moviesResponse ->
                        CoroutineScope(Dispatchers.Main).launch {
                            moviesList.clear()
                            moviesList.addAll(moviesResponse.results)
                            addMoreMovies(moviesResponse.total_pages)
                        }
                    }
                }
                Status.ERROR ->{}
            }
        }
        moviesViewModel.movieSearchModel.observe(viewLifecycleOwner){
            userRequirments = it
        }
    }

    private suspend fun addMoreMovies(pageNumbers:Int){
        if(pageNumbers > 30){
                for (i in 0..30) {
                    val randomMoviesPage = moviesApi.getRandomMovie(
                        userRequirments.language,
                        userRequirments.genres,
                        userRequirments.watchType,
                        userRequirments.minimumReleaseDate,
                        userRequirments.minimumRating,
                        API_KEY,
                        i
                    )
                    if (randomMoviesPage.isSuccessful) {
                        moviesList.addAll(randomMoviesPage.body()?.results!!)
                    }

                }
                selectOneMovie(moviesList)
            }
        else{
                for (i in 0..pageNumbers){
                    val randomMoviesPage = moviesApi.getRandomMovie(
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
                selectOneMovie(moviesList)
            }
    }

    private suspend fun selectOneMovie(list:ArrayList<MovieModel>){
        try {
            list.forEach {
                delay(5)
                if(tvRandomNames != null){
                    tvRandomNames.text = it.original_title
                }
            }

            val randomNumber = (0 until list.size).random()
            randomMovie = list[randomNumber]
            showFoundMovieWithAnimation(randomMovie)

        }catch (e:java.util.ConcurrentModificationException){
            Log.e("for loop error",e.toString())
        }
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

    private fun showSearchWithAnimation(){
        searchingForShow.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(1f)
            scaleX(1f)
            scaleY(1f)
            startDelay = 500
            start()
        }

        tvSearching.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(1f)
            scaleX(1f)
            scaleY(1f)
            startDelay = 500
            start()
        }
    }

    private fun showFoundMovieWithAnimation(movieModel: MovieModel){
        if(foundShowImage != null && foundMovieName != null && spark1 != null &&  spark2 != null && spark3 != null && spark4 != null
            && btnRetrySearch  != null && tvRandomNames != null && searchingForShow != null && tvSearching != null &&
            progressBarSearchingForMovie != null )
        {

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
            showSparks()
            hideRetryBtnWithAnimation()
            tvRandomNames.text = ""
            hideSearchWithAnimation()
            progressBarSearchingForMovie.visibility = View.INVISIBLE
            showRetryBtnWithAnimation()
        }


    }

    private suspend fun hideFoundMovieWithAnimation(){
        foundShowImage.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(0f)
            scaleX(0f)
            scaleY(0f)
            startDelay = 500
            start()
        }
        foundMovieName.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(0f)
            scaleX(0f)
            scaleY(0f)
            startDelay = 500
            start()
        }
        showSearchWithAnimation()
        delay(20)
        hideSparks()
        delay(20)
        hideRetryBtnWithAnimation()
        delay(20)
        progressBarSearchingForMovie.isVisible = true
    }

    private fun showSparks(){
        spark1.isVisible = true
        spark2.isVisible = true
        spark3.isVisible = true
        spark4.isVisible = true
        spark1.playAnimation()
        spark2.playAnimation()
        spark3.playAnimation()
        spark4.playAnimation()
    }

    private fun hideSparks(){
        spark1.isVisible = false
        spark2.isVisible = false
        spark3.isVisible = false
        spark4.isVisible = false
        spark1.pauseAnimation()
        spark2.pauseAnimation()
        spark3.pauseAnimation()
        spark4.pauseAnimation()
    }

    private fun showRetryBtnWithAnimation(){
        btnRetrySearch.animate().apply {
            interpolator = LinearInterpolator()
            alpha(1f)
            startDelay = 500
            start()
        }
    }

    private fun hideRetryBtnWithAnimation(){
        btnRetrySearch.animate().apply {
            interpolator = LinearInterpolator()
            alpha(0f)
            startDelay = 500
            start()
        }
    }

    override fun onPause() {
        super.onPause()
        tvRandomNames.text = ""
    }

}