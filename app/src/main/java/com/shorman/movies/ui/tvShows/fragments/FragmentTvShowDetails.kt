package com.shorman.movies.ui.tvShows.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shorman.movies.R
import com.shorman.movies.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.tv_details_fragment.*

@AndroidEntryPoint
class FragmentTvShowDetails:Fragment(R.layout.tv_details_fragment) {

    private val args:FragmentTvShowDetailsArgs by navArgs()
    private val fragmentArrayList :ArrayList<Fragment> by lazy {
        arrayListOf(FragmentTvShowDetailsOne(args.tvShowID),FragmentTvShowDetailsTwo())
    }
    private lateinit var tvShowPagerAdapter:ViewPagerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvShowPagerAdapter = ViewPagerAdapter(childFragmentManager,lifecycle,fragmentArrayList)
        detailsPagerTv.adapter = tvShowPagerAdapter

        tvShowDetailsBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }

    }


}