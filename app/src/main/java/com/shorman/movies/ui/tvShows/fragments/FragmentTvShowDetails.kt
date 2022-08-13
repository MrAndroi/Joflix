package com.shorman.movies.ui.tvShows.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shorman.movies.R
import com.shorman.movies.adapters.ViewPagerAdapter
import com.shorman.movies.viewModels.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.tv_details_fragment.*
import kotlinx.android.synthetic.main.tv_details_one.*

@AndroidEntryPoint
class FragmentTvShowDetails:Fragment(R.layout.tv_details_fragment) {

    private val args:FragmentTvShowDetailsArgs by navArgs()
    private val fragmentArrayList :ArrayList<Fragment> by lazy {
        arrayListOf(FragmentTvShowDetailsOne(args.tvShowID),FragmentTvShowDetailsTwo())
    }
    private lateinit var tvShowPagerAdapter:ViewPagerAdapter
    private lateinit var tvShowViewModel: TvShowsViewModel
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)

        tvShowViewModel.getTvShowDetails(args.tvShowID)
        tvShowViewModel.updateCurrentShowID(args.tvShowID)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (showTrailerVideo.isFullScreen()) {
                    showTrailerVideo.exitFullScreen()
                }
                else{
                    findNavController().navigateUp()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)

        tvShowPagerAdapter = ViewPagerAdapter(childFragmentManager,lifecycle,fragmentArrayList)
        detailsPagerTv.adapter = tvShowPagerAdapter

    }


}