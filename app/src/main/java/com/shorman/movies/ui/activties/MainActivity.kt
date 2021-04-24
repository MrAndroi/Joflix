package com.shorman.movies.ui.activties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.shorman.movies.R
import com.shorman.movies.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        nav_host_fragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.findMoviesFragment ->{
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

    }
}