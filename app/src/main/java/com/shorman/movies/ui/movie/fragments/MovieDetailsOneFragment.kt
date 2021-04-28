package com.shorman.movies.ui.movie.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import coil.load
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import com.shorman.movies.R
import com.shorman.movies.adapters.CastAdapter
import com.shorman.movies.api.models.others.Cast
import com.shorman.movies.api.models.movie.MovieDetails
import com.shorman.movies.utils.Status
import com.shorman.movies.viewModels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_one.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class MovieDetailsOneFragment(private val movieId: Int):Fragment(R.layout.movie_details_one) {

    private lateinit var moviesViewModel:MoviesViewModel
    private lateinit var castAdapter:CastAdapter
    private var movieVideoCode = ""
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var currentActorView:View
    private lateinit var onBackPressedCallback:OnBackPressedCallback

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        getAllMovieData()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(trailerVideo!= null){
                    if (trailerVideo.isFullScreen()) {
                        trailerVideo.exitFullScreen()
                    }
                    else{
                        findNavController().navigateUp()
                    }
                }
                else{
                    findNavController().navigateUp()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)

        castAdapter = CastAdapter{ actor, actorView ->
            currentActorView = actorView
            openActorDetailsDialog(actorView, actor)
        }

        subscribeToObservers()
        setUpActorsRecyclerView()

        playTrailerBtn.setOnClickListener {
            initYouTubePlayerView(movieVideoCode)
        }

        moviesDetailsOneBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        btnSaveMovie.setOnClickListener{
            moviesViewModel.toggleSave()
        }
    }

    private fun openActorDetailsDialog(item: View, actor: Cast){
        val transform = MaterialContainerTransform().apply {
            startView = item
            endView  = actorDetailsDialog
            duration = 500
            scrimColor = Color.TRANSPARENT
            addTarget(actorDetailsDialog)
        }
        actorDetailsDialog.visibility = View.VISIBLE
        moviesViewModel.changeActorsDialogVisiblity(true)
        tvActorName.text = actor.name
        if(actor.gender == 1){
            tvActorGender.text = "Gender: female"
        }
        else{
            tvActorGender.text = "Gender: male"
        }

        tvActorCharacher.text = "Character: ${actor.character}"
        actorImageDialog.load(IMAGES_BASE_URL + actor.profile_path){
            placeholder(R.drawable.actor_loading)
        }
        TransitionManager.beginDelayedTransition(movieDetailsOneContainer, transform)

    }

    private fun exitActorDetailsDialog(item: View){
        val transform = MaterialContainerTransform().apply {
            startView = actorDetailsDialog
            endView  = item
            duration = 500
            scrimColor = Color.TRANSPARENT
            addTarget(item)
        }
        actorDetailsDialog.visibility = View.INVISIBLE
        moviesViewModel.changeActorsDialogVisiblity(false)

        TransitionManager.beginDelayedTransition(movieDetailsOneContainer, transform)

    }

    private fun getAllMovieData(){
        moviesViewModel.getMovieActors(movieId)
        moviesViewModel.getMovieVideos(movieId)
    }

    private fun setUpActorsRecyclerView(){
        rvActors.setHasFixedSize(true)
        rvActors.layoutManager = linearLayoutManager
        rvActors.adapter = castAdapter
    }

    private fun subscribeToObservers(){
        moviesViewModel.currentMovieDetails.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    progressBarMovieDetailsOne.isVisible = true
                }
                Status.SUCCESS -> {
                    it.data?.let { movieDetails ->
                        setUpViews(movieDetails)
                        progressBarMovieDetailsOne.isVisible = false
                    }
                }
                Status.ERROR -> {
                    Snackbar.make(requireView(),"Error:${it.message}",2000).show()
                    progressBarMovieDetailsOne.isVisible = false
                }
            }
        }

        moviesViewModel.currentMovieActors.observe(viewLifecycleOwner){
            if (it.status == Status.SUCCESS){
                it.data?.let { actorsResponse ->
                    CoroutineScope(Dispatchers.Main).launch {
                        castAdapter.differ.submitList(actorsResponse.cast)
                        delay(100)
                        linearLayoutManager.smoothScrollToPosition(
                            rvActors,
                            RecyclerView.State(),
                            0
                        )
                    }
                }
            }
        }

        moviesViewModel.currentMovieVideos.observe(viewLifecycleOwner){
            if(it.status == Status.SUCCESS){
                it.data?.let { videosResponse ->

                    if(videosResponse.results.isNotEmpty()){
                        movieVideoCode = videosResponse.results[0].key
                    }
                    else{
                        Toast.makeText(requireContext(), "Video not found", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }

        moviesViewModel.actorsDialogVisible.observe(viewLifecycleOwner){
            if(it){
                movieDetailsOneContainer.setOnClickListener {
                    exitActorDetailsDialog(currentActorView)
                }
            }
            else{
                movieDetailsOneContainer.setOnClickListener(null)
            }
        }

        moviesViewModel.saveState.observe(viewLifecycleOwner){
            if(it){
                btnSaveMovie.setMaxProgress(0.5f)
                btnSaveMovie.speed = -1f
                btnSaveMovie.repeatMode = LottieDrawable.REVERSE
                btnSaveMovie.playAnimation()
            }
            else{
                btnSaveMovie.progress = 0.0f
                btnSaveMovie.speed = 1f
                btnSaveMovie.repeatMode = LottieDrawable.RESTART
                btnSaveMovie.playAnimation()
            }

        }
    }

    private fun setUpViews(movieDetails: MovieDetails){
        tvRating.text = "${movieDetails.vote_average}"
        tvTitle.text = movieDetails.title
        tvOverView.text = movieDetails.overview
        tvMovieTime.text = formatTime(movieDetails.runtime)
        tvReleaseDate.text = movieDetails.release_date
        movieImage.load(IMAGES_BASE_URL + movieDetails.poster_path){
            placeholder(R.drawable.loading)
            crossfade(true)
            crossfade(1000)
        }
        if(movieDetails.adult){
            tvAdult.text = "18+"
        }
        else{
            tvAdult.text = "13+"
        }
        if(movieDetails.genres.isNotEmpty()){
            tvMovieType.text = movieDetails.genres[0].name
        }
    }

    private fun formatTime(timeDuration: Int):String{
        val hours = timeDuration / 60
        val minutes = timeDuration / 60
        return "${hours}h ${minutes}m"
    }

    private fun initYouTubePlayerView(videoId: String) {
        trailerVideo.visibility = View.VISIBLE
        progressBarVideo.visibility = View.VISIBLE
        movieImage.visibility = View.INVISIBLE
        lifecycle.addObserver(trailerVideo)

        trailerVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {

                youTubePlayer.loadVideo(videoId, 0f)
                addFullScreenListenerToPlayer()
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                Snackbar.make(requireView(), "Try again!", 1000).show()
                youTubePlayer.cueVideo(videoId, 0f)
                addFullScreenListenerToPlayer()
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                if (state == PlayerConstants.PlayerState.BUFFERING) {
                    progressBarVideo.visibility = View.VISIBLE
                    return
                }
                progressBarVideo.visibility = View.INVISIBLE
            }
        })
    }

    private fun addFullScreenListenerToPlayer() {
        trailerVideo.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                tvReleaseDate.isVisible = false
            }

            override fun onYouTubePlayerExitFullScreen() {
                tvReleaseDate.isVisible = true
            }
        })
    }

    private fun makeSaveAnimation(){
        if(btnSaveMovie.progress == 0.8f){
            btnSaveMovie.progress = 0.07f
            btnSaveMovie.speed = -1f
            btnSaveMovie.repeatMode = LottieDrawable.REVERSE
            btnSaveMovie.playAnimation()

        }
        else{
            btnSaveMovie.progress = 0.8f
            btnSaveMovie.speed = 1f
            btnSaveMovie.repeatMode = LottieDrawable.RESTART
            btnSaveMovie.playAnimation()
        }
    }

}