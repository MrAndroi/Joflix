package com.shorman.movies.ui.tvShows.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shorman.movies.R
import com.shorman.movies.adapters.GenresAdapter
import com.shorman.movies.adapters.LanguagesAdapter
import com.shorman.movies.api.models.others.Genre
import com.shorman.movies.api.models.others.Language
import com.shorman.movies.others.Constans
import com.shorman.movies.ui.movie.fragments.MovieRequirmentsFragmentDirections
import com.shorman.movies.utils.Status
import com.shorman.movies.utils.getAllLanguages
import com.shorman.movies.utils.mutation
import com.shorman.movies.viewModels.MoviesViewModel
import com.shorman.movies.viewModels.TvShowsViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_requirments_fragment.*
import kotlinx.android.synthetic.main.movie_requirments_fragment.etLanguage
import kotlinx.android.synthetic.main.tv_requirments_fragment.*
import java.util.*

@AndroidEntryPoint
class TvRequirmentsFragment:Fragment(R.layout.tv_requirments_fragment),DatePickerDialog.OnDateSetListener {

    lateinit var languagesAdapter: LanguagesAdapter
    lateinit var languages:Array<Language>
    lateinit var genresList:Array<Genre>
    lateinit var watchTypeAdapter: ArrayAdapter<String>
    lateinit var watchTypeList:Array<String>

    lateinit var tvShowsViewModel:TvShowsViewModel
    lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowsViewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)

        watchTypeList = resources.getStringArray(R.array.watchTypes)
        genresList = emptyArray()
        languages = getAllLanguages(Constans.LANGUAGE_CODES)
        languagesAdapter = LanguagesAdapter(requireContext(), R.layout.language_item, languages)
        watchTypeAdapter = ArrayAdapter(requireContext(), R.layout.watch_type_item, watchTypeList)

        getAllData()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAllTexts()
        tvRequirmentsBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        tvGoTv.setOnClickListener {
            val direction = TvRequirmentsFragmentDirections.actionTvRequirmentsFragmentToLookingForTvShowFragment()
            findNavController().navigate(direction)
        }
    }

    private fun setUpAllTexts(){
        //languages input
        etLanguageTv.setAdapter(languagesAdapter)
        etLanguageTv.setOnItemClickListener { _, _, position, _ ->
            etLanguageTv.setText(languages[position].name)
            tvShowsViewModel.movieSearchModel.mutation {
                it.value?.language = languages[position].code
            }

        }

        //genres input
        moviesViewModel.movieGenresList.observe(viewLifecycleOwner){ genresResponse ->
            when(genresResponse.status){
                Status.LOADING -> {
                    tvStatusTv.text = "Loading please wait ..."
                }
                Status.SUCCESS -> {
                    tvStatusTv.text = "You can skip all that information and try your  luck :)."
                    genresResponse.data?.let { genresResponses ->
                        genresList = genresResponses.genres.toTypedArray()
                        val genresAdapter = GenresAdapter(
                            requireContext(),
                            R.layout.genre_item,
                            genresList
                        )
                        etGenresTv.setAdapter(genresAdapter)
                    }

                }
                Status.ERROR -> {
                    tvStatusTv.text = "${genresResponse.message}"
                }
            }

        }
        etGenresTv.setOnItemClickListener { _, _, position, _ ->
            etGenresTv.setText(genresList[position].name)
            tvShowsViewModel.movieSearchModel.mutation {
                it.value?.genres = genresList[position].id.toString()
            }
        }

        //watch type input
        etWatchTypeTv.setAdapter(watchTypeAdapter)
        etWatchTypeTv.setOnItemClickListener { _, _, position, _ ->
            etWatchTypeTv.setText(watchTypeList[position])
            tvShowsViewModel.movieSearchModel.mutation {
                it.value?.watchType = watchTypeList[position]
            }
        }

        //release date input
        etReleaseDateTv.setOnClickListener {
            val now: Calendar = Calendar.getInstance()
            val dpd: DatePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),  // Initial year selection
                now.get(Calendar.MONTH),  // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            )

            dpd.show(activity?.supportFragmentManager!!, "Datepickerdialog")
        }

        //rating input
        ratingSeekBarTv.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvShowsViewModel.movieSearchModel.mutation {
                    it.value?.minimumRating = progress.toFloat()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?)  = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

        })
    }

    private fun getAllData(){
        moviesViewModel.getMovieGenres()
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        etReleaseDateTv.setText("$year-$monthOfYear-$dayOfMonth")
        tvShowsViewModel.movieSearchModel.mutation {
            it.value?.minimumReleaseDate = "$year-$monthOfYear-$dayOfMonth"
        }
    }
}