package com.shorman.movies.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.shorman.movies.api.models.movie.MovieSearchModel
import com.shorman.movies.api.models.movie.MoviesResponse
import com.shorman.movies.api.models.others.ReviewsResponse
import com.shorman.movies.api.models.others.VideosResponse
import com.shorman.movies.api.models.tvshows.TvShowDetails
import com.shorman.movies.api.models.tvshows.TvShowsResponse
import com.shorman.movies.repo.Repository
import com.shorman.movies.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.http.Query

class TvShowsViewModel @ViewModelInject constructor(private val repo:Repository):ViewModel() {

    val currentQuery = MutableLiveData("")
    val currentShowID = MutableLiveData(1)
    val movieSearchModel = MutableLiveData(MovieSearchModel())

    private val _currentShowDetails = MutableLiveData<Resource<TvShowDetails>>()
    val currentShowDetails:LiveData<Resource<TvShowDetails>>
    get() = _currentShowDetails

    private val _currentShowVideos = MutableLiveData<VideosResponse>()
    val currentShowVideos:LiveData<VideosResponse>
        get() = _currentShowVideos

    private val _randomTvShowList = MutableLiveData<Resource<TvShowsResponse>>()
    val randomTvShowList:LiveData<Resource<TvShowsResponse>>
        get() = _randomTvShowList


    val tvShowsList = currentQuery.switchMap { searchString ->
        repo.searchForTvShows(searchString).cachedIn(viewModelScope)
    }

    val commentsList = currentShowID.switchMap { showID ->
        repo.getTvShowReviews(showID).cachedIn(viewModelScope)
    }

    fun getTvShowDetails(tvShowID:Int) = viewModelScope.launch {
        _currentShowDetails.postValue(Resource.loading(null))

        val result = repo.getTvShowDetails(tvShowID)
        if(result.isSuccessful){
            val videoResponse = repo.getTvShowVideos(tvShowID)
            val reviewsResponse = repo.getTvShowReviews(tvShowID)
            if(videoResponse.isSuccessful) {
                _currentShowVideos.postValue(videoResponse.body())
            }
            _currentShowDetails.postValue(Resource.success(result.body()))
        }
        else{
            _currentShowDetails.postValue(Resource.error(result.message(),null))
        }
    }


    fun getRandomShows(
        language: String ="",
        genres:String ="",
        watchType:String ="",
        minimumRealseDate:String="",
        minimumRating:Float=0f,
    ) = viewModelScope.launch {
        _randomTvShowList.postValue(Resource.loading(null))

        val result = repo.getRandomTvShow(language, genres, watchType, minimumRealseDate, minimumRating)
        if(result.isSuccessful){
            _randomTvShowList.postValue(Resource.success(result.body()))
        }
        else{
            _randomTvShowList.postValue(Resource.error(result.message(),null))
        }
    }

    fun updateSearchQuery(query: String){
        currentQuery.postValue(query)
    }

    fun updateCurrentShowID(newID:Int){
        currentShowID.postValue(newID)
    }

    fun clearRandomsList(){
        _randomTvShowList.postValue(Resource.loading(null))
    }

}