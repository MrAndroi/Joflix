package com.shorman.movies.ui.activties

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
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
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var moviesViewModel:MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Movies)
        setContentView(R.layout.activity_main)

        startNetworkCallback()

        bottomNavigationView.setupWithNavController(nav_host_fragment.findNavController())

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        nav_host_fragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.findMoviesFragment,R.id.watchToNightFragment,R.id.savedFragment,R.id.settingsFragment ->{
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        val prefs = newBase!!.getSharedPreferences(
            "language",
            MODE_PRIVATE
        )
        val localeString:String? =
            prefs.getString("lang", "en")
        val myLocale = Locale(localeString!!)
        Locale.setDefault(myLocale)
        val config = newBase.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(myLocale)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                val newContext = newBase.createConfigurationContext(config)
                super.attachBaseContext(newContext)
                return
            }
        } else {
            config.locale = myLocale
        }
        super.attachBaseContext(newBase)
        resources.updateConfiguration(config, resources.displayMetrics)
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