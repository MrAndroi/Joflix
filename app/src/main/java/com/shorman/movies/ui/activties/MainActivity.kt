package com.shorman.movies.ui.activties

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shorman.movies.R
import com.shorman.movies.viewModels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var moviesViewModel:MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startNetworkCallback()

        bottomNavigationView.setupWithNavController(nav_host_fragment.findNavController())

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        nav_host_fragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.findMoviesFragment,R.id.watchToNightFragment,R.id.savedFragment ->{
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

    }

    private fun startNetworkCallback() {
        val cm: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)  as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        cm.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    CoroutineScope(Dispatchers.Main).launch {
                        moviesViewModel.isNetworkAvailable.postValue(true)
                    }

                }

                override fun onLost(network: Network) {
                    CoroutineScope(Dispatchers.Main).launch {
                        moviesViewModel.isNetworkAvailable.postValue(false)
                    }
                }

                override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                    CoroutineScope(Dispatchers.Main).launch {
                        moviesViewModel.isNetworkAvailable.postValue(true)
                    }
                }

            })
    }
}