package com.shorman.movies.ui.watchTonight

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shorman.movies.R
import com.shorman.movies.adapters.GenresAdapter
import com.shorman.movies.adapters.LanguagesAdapter
import com.shorman.movies.api.models.others.Genre
import com.shorman.movies.api.models.others.Language
import com.shorman.movies.others.Constans.LANGUAGE_CODES
import com.shorman.movies.utils.Status
import com.shorman.movies.utils.getAllLanguages
import com.shorman.movies.utils.mutation
import com.shorman.movies.viewModels.MoviesViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.user_requirments_fragment.*
import java.util.*


@SuppressLint("SetTextI18n")
class UserRequirmentsFragment:Fragment(R.layout.user_requirments_fragment),DatePickerDialog.OnDateSetListener {

    lateinit var languagesAdapter: LanguagesAdapter
    lateinit var languages:Array<Language>
    lateinit var genresList:Array<Genre>
    lateinit var watchTypeAdapter:ArrayAdapter<String>
    lateinit var watchTypeList:Array<String>

    lateinit var moviesViewModel:MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)

        watchTypeList = resources.getStringArray(R.array.watchTypes)
        genresList = emptyArray()
        languages = getAllLanguages(LANGUAGE_CODES)
        languagesAdapter = LanguagesAdapter(requireContext(), R.layout.language_item, languages)
        watchTypeAdapter = ArrayAdapter(requireContext(), R.layout.watch_type_item, watchTypeList)

        getAllData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAllTexts()

    }

    private fun setUpAllTexts(){
        //languages input
        etLanguage.setAdapter(languagesAdapter)
        etLanguage.setOnItemClickListener { _, _, position, _ ->
            etLanguage.setText(languages[position].name)
            moviesViewModel.movieSearchModel.mutation {
                it.value?.language = languages[position].code
            }

        }

        //genres input
        moviesViewModel.movieGenresList.observe(viewLifecycleOwner){ genresResponse ->
            when(genresResponse.status){
                Status.LOADING -> {
                    tvStatus.text = "Loading please wait ..."
                }
                Status.SUCCESS -> {
                    tvStatus.text = "You can skip all that information and try your  luck :)."
                    genresResponse.data?.let { genresResponses ->
                        genresList = genresResponses.genres.toTypedArray()
                        val genresAdapter = GenresAdapter(
                            requireContext(),
                            R.layout.genre_item,
                            genresList
                        )
                        etGenres.setAdapter(genresAdapter)
                    }

                }
                Status.ERROR -> {
                    tvStatus.text = "${genresResponse.message}"
                }
            }

        }
        etGenres.setOnItemClickListener { _, _, position, _ ->
            etGenres.setText(genresList[position].name)
            moviesViewModel.movieSearchModel.mutation {
                it.value?.genres = genresList[position].id.toString()
            }
        }

        //watch type input
        etWatchType.setAdapter(watchTypeAdapter)
        etWatchType.setOnItemClickListener { _, _, position, _ ->
            etWatchType.setText(watchTypeList[position])
            moviesViewModel.movieSearchModel.mutation {
                it.value?.watchType = watchTypeList[position]
            }
        }

        //release date input
        etReleaseDate.setOnClickListener {
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
        ratingSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                moviesViewModel.movieSearchModel.mutation {
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
        etReleaseDate.setText("$year-$monthOfYear-$dayOfMonth")
        moviesViewModel.movieSearchModel.mutation {
            it.value?.minimumReleaseDate = "$year-$monthOfYear-$dayOfMonth"
        }
    }

}