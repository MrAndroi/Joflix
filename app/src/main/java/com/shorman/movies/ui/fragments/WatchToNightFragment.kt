package com.shorman.movies.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shorman.movies.R
import com.shorman.movies.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.watch_tonight_fragment.*

@AndroidEntryPoint
class WatchToNightFragment():Fragment(R.layout.watch_tonight_fragment) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieBtn.setOnClickListener {
            findNavController().navigate(R.id.action_watchToNightFragment_to_movieRequirmentsFragment)
        }

    }

}