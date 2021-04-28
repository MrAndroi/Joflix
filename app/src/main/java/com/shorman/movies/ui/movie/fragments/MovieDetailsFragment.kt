package com.shorman.movies.ui.movie.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.shorman.movies.R
import com.shorman.movies.adapters.ViewPagerAdapter
import com.shorman.movies.viewModels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment:Fragment(R.layout.movie_details_fragment) {

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val  fragmentList:ArrayList<Fragment> by lazy {
        arrayListOf(MovieDetailsOneFragment(args.movieId), MovieDetailsTwoFragment(args.movieId))
    }
    private lateinit var pagerAdapter: ViewPagerAdapter
    private val animator = ValueAnimator()
    private var animFactor = 0
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        moviesViewModel.getMovieDetails(args.movieId)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            fragmentList
        )
        detailsPager.adapter = pagerAdapter

        CoroutineScope(Dispatchers.Main).launch {
            delay(15000)
            if(detailsPager != null){
                animateViewPager(detailsPager)
            }
        }



    }

    private fun animateViewPager(pager: ViewPager2) {
        if (!animator.isRunning) {
            animator.removeAllUpdateListeners()
            animator.removeAllListeners()
            //Set animation
            animator.setIntValues(0, -10)
            animator.duration = (500)
            animator.repeatCount = (1)
            animator.repeatMode = (ValueAnimator.RESTART)
            animator.addUpdateListener { animation ->
                val value: Int = animFactor * animation.animatedValue as Int
                if (!pager.isFakeDragging) {
                    pager.beginFakeDrag()
                }
                pager.fakeDragBy(value.toFloat())
            }
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    animFactor = 1
                }

                override fun onAnimationEnd(animation: Animator) {
                    pager.endFakeDrag()
                }

                override fun onAnimationRepeat(animation: Animator) {
                    animFactor = -1
                }
            })
            animator.start()
        }
    }

}