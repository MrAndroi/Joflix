package com.shorman.movies.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shorman.movies.moviesApi.models.movie.*
import com.shorman.movies.repo.Repository
import com.shorman.movies.utils.Resource
import kotlinx.coroutines.launch


class MainViewModel @ViewModelInject constructor(private val repo:Repository):ViewModel() {

    val currentQuery = MutableLiveData("")
    val currentMovieID = MutableLiveData(1)

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

    fun searchKeyWord(query: String) {
        currentQuery.postValue(query)
    }

    fun changeMovieID(movieID: Int) {
        currentMovieID.postValue(movieID)
    }

    fun changeActorsDialogVisiblity(visible:Boolean) {
        actorsDialogVisible.postValue(visible)
    }


}