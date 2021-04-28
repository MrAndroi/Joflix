package com.shorman.movies.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.shorman.movies.api.models.movie.*
import com.shorman.movies.api.models.others.ActorsResponse
import com.shorman.movies.api.models.others.GenresResponse
import com.shorman.movies.api.models.others.VideosResponse
import com.shorman.movies.repo.Repository
import com.shorman.movies.utils.Resource
import kotlinx.coroutines.launch


class MoviesViewModel @ViewModelInject constructor(private val repo:Repository):ViewModel() {

    val currentQuery = MutableLiveData("")
    val movieSearchModel = MutableLiveData(MovieSearchModel())
    private val currentMovieID = MutableLiveData(1)
    val saveState = MutableLiveData(false)

    val actorsDialogVisible = MutableLiveData(false)

    private val _currentMovieDetails = MutableLiveData<Resource<MovieDetails>>()
    val currentMovieDetails :LiveData<Resource<MovieDetails>>
        get() = _currentMovieDetails

    private val _currentMovieActors = MutableLiveData<Resource<ActorsResponse>>()
    val currentMovieActors :LiveData<Resource<ActorsResponse>>
        get() = _currentMovieActors

    private val _currentMovieVideos = MutableLiveData<Resource<VideosResponse>>()
    val currentMovieVideos:LiveData<Resource<VideosResponse>>
    get() = _currentMovieVideos

    private val _currentMovieImages = MutableLiveData<Resource<MovieImageList>>()
    val currentMovieImages:LiveData<Resource<MovieImageList>>
        get() = _currentMovieImages

    private val _movieGenresList = MutableLiveData<Resource<GenresResponse>>()
    val movieGenresList:LiveData<Resource<GenresResponse>>
        get() = _movieGenresList

    private val _randomMovieList = MutableLiveData<Resource<MoviesResponse>>()
    val randomMovieList:LiveData<Resource<MoviesResponse>>
        get() = _randomMovieList


    @ExperimentalPagingApi
    val latestMovies = currentQuery.switchMap { queryString ->
        repo.searchForMovies(queryString).cachedIn(viewModelScope)
    }

    val movieComments = currentMovieID.switchMap { id ->
        repo.getMovieComments(id).cachedIn(viewModelScope)
    }

    fun getMovieDetails(movieID:Int) = viewModelScope.launch {
        _currentMovieDetails.postValue(Resource.loading(null))

        val result = repo.getMovieDetails(movieID)
        if(result.isSuccessful){
            _currentMovieDetails.postValue(Resource.success(result.body()))
        }
        else{
            _currentMovieDetails.postValue(Resource.error(result.message(),null))
        }

    }

    fun getMovieActors(movieID: Int) = viewModelScope.launch {
        _currentMovieActors.postValue(Resource.loading(null))

        val result = repo.getMovieActors(movieID)
        if (result.isSuccessful){
            _currentMovieActors.postValue(Resource.success(result.body()))
        }
        else{
            _currentMovieActors.postValue(Resource.error(result.message(),null))
        }
    }

    fun getMovieVideos(movieID: Int) = viewModelScope.launch {
        _currentMovieVideos.postValue(Resource.loading(null))

        val result = repo.getMovieVideos(movieID)
        if(result.isSuccessful){
            _currentMovieVideos.postValue(Resource.success(result.body()))
        }
        else{
            _currentMovieVideos.postValue(Resource.error(result.message(),null))
        }
    }

    fun getMovieImages(movieID: Int) = viewModelScope.launch {
        _currentMovieImages.postValue(Resource.loading(null))

        val result = repo.getMovieImages(movieID)
        if(result.isSuccessful){
            _currentMovieImages.postValue(Resource.success(result.body()))
        }
        else{
            _currentMovieImages.postValue(Resource.error(result.message(),null))
        }
    }

    fun getMovieGenres() = viewModelScope.launch {
        _movieGenresList.postValue(Resource.loading(null))

        val result = repo.getMovieGenres()
        if(result.isSuccessful){
            _movieGenresList.postValue(Resource.success(result.body()))
        }
        else{
            _movieGenresList.postValue(Resource.error(result.message(),null))
        }
    }

    fun getRandomMovies(
        language: String ="",
        genres:String ="",
        watchType:String ="",
        minimumRealseDate:String="",
        minimumRating:Float=0f,
    ) = viewModelScope.launch {
        _randomMovieList.postValue(Resource.loading(null))

        val result = repo.getRandomMovie(language, genres, watchType, minimumRealseDate, minimumRating)
        if(result.isSuccessful){
            _randomMovieList.postValue(Resource.success(result.body()))
        }
        else{
            _randomMovieList.postValue(Resource.error(result.message(),null))
        }
    }




    fun searchKeyWord(query: String) {
        currentQuery.postValue(query)
    }

    fun changeMovieID(movieID: Int) {
        currentMovieID.postValue(movieID)
    }

    fun changeActorsDialogVisiblity(visible:Boolean) {
        actorsDialogVisible.postValue(visible)
    }

    fun toggleSave(){
        saveState.postValue(saveState.value?.not())
    }


}