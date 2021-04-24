package com.shorman.movies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManger:FragmentManager,lifeCycle:Lifecycle,private val fragmentList:ArrayList<Fragment>)
    : FragmentStateAdapter(fragmentManger,lifeCycle){

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}