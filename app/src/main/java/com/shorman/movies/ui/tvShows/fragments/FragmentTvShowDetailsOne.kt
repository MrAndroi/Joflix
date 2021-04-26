package com.shorman.movies.ui.tvShows.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.shorman.movies.R
import com.shorman.movies.api.models.others.Genre
import com.shorman.movies.api.models.tvshows.TvShowDetails
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import com.shorman.movies.utils.Status
import com.shorman.movies.viewModels.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_one.*
import kotlinx.android.synthetic.main.movie_details_two.*
import kotlinx.android.synthetic.main.show_item.*
import kotlinx.android.synthetic.main.tv_details_one.*
import kotlinx.android.synthetic.main.tv_details_one.tvShowHomePage
import kotlinx.android.synthetic.main.tv_details_one.view.*
import java.util.*

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class FragmentTvShowDetailsOne(private val tvShowID: Int):Fragment(R.layout.tv_details_one) {

    private lateinit var tvShowViewModel:TvShowsViewModel
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var showVideoID = ""

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
        tvShowViewModel.getTvShowDetails(tvShowID)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(showTrailerVideo!= null){
                    if (showTrailerVideo.isFullScreen()) {
                        showTrailerVideo.exitFullScreen()
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

        setUpObservers()
        playShowTrailerBtn.setOnClickListener{
            initYouTubePlayerView(showVideoID)
        }

    }

    private fun setUpObservers(){
        tvShowViewModel.currentShowDetails.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    progressBarTvDetailsOne.isVisible = true
                }
                Status.SUCCESS -> {
                    it.data?.let { tvShowDetails ->
                        setUpViews(tvShowDetails)
                        progressBarTvDetailsOne.isVisible = false
                    }
                }
                Status.ERROR -> {
                    Snackbar.make(requireView(), "Error:${it.message}", 2000).show()
                    progressBarTvDetailsOne.isVisible = false
                }
            }
        }

        tvShowViewModel.currentShowVideos.observe(viewLifecycleOwner){
            if(it.results.isNotEmpty()){
                showVideoID = it.results[0].key
            }
        }
    }

    private fun setUpViews(tvShowDetails: TvShowDetails){
        tvShowImage.load(IMAGES_BASE_URL + tvShowDetails.poster_path){
            placeholder(R.drawable.loading)
            error(R.drawable.place_holder)
            crossfade(true)
            crossfade(500)
        }
        if(tvShowDetails.number_of_seasons > 1){
            tvSeriesSeasons.text = "${tvShowDetails.number_of_seasons} Seasons"
        }
        else{
            tvSeriesSeasons.text = "${tvShowDetails.number_of_seasons} Season"
        }
        tvShowStatus.text = "Status: ${tvShowDetails.status}"
        if(tvShowDetails.genres.size > 5){
            tvShowDetails.genres.subList(0,5).forEach { genre ->
                addGenreChips(genre)
            }
        }
        else{
            tvShowDetails.genres.forEach { genre ->
                addGenreChips(genre)
            }
        }

        tvShowRating.text = "${tvShowDetails.vote_average}"
        tvFromVotes.text = "(from ${tvShowDetails.vote_count} votes)"
        tvShowTitle.text = tvShowDetails.original_name
        tvShowOverView.text = tvShowDetails.overview
        tvShowReleaseDate.text = tvShowDetails.first_air_date
        tvShowHomePage.apply {
            paintFlags = tvShowHomePage.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            text = tvShowDetails.homepage
            setOnClickListener {
                val url = tvShowDetails.homepage
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }
    }

    private fun addGenreChips(genre: Genre){
        tvShowTypeChips.removeAllViews()
        val chip = layoutInflater.inflate(R.layout.company_item, companiesChips, false) as Chip
        chip.text = genre.name
        val rnd = Random()
        val color: Int = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        chip.chipBackgroundColor = ColorStateList.valueOf(color)
        chip.setOnClickListener {
            val url = "https://www.google.com/search?q=${genre.name}+series"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        tvShowTypeChips.addView(chip)
    }

    private fun initYouTubePlayerView(videoId: String) {
        showTrailerVideo.visibility = View.VISIBLE
        progressBarShowVideo.visibility = View.VISIBLE
        tvShowImage.visibility = View.INVISIBLE
        lifecycle.addObserver(showTrailerVideo)

        showTrailerVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
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
                    progressBarShowVideo.visibility = View.VISIBLE
                    return
                }
                progressBarShowVideo.visibility = View.INVISIBLE
            }
        })
    }

    private fun addFullScreenListenerToPlayer() {
        showTrailerVideo.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                tvShowReleaseDate.isVisible = false
                tvFromVotes.isVisible = false
            }

            override fun onYouTubePlayerExitFullScreen() {
                tvShowReleaseDate.isVisible = true
                tvFromVotes.isVisible = true
            }
        })
    }
}