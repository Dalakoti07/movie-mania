package com.dalakoti07.android.moviemania.ui.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.dalakoti07.android.moviemania.data.models.Movie
import com.dalakoti07.android.moviemania.data.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(
    app: Application,
    val movieRepository: MovieRepository
) : AndroidViewModel(app){

    private var _isDataLoading=MutableLiveData<Boolean>(true)
    private var moviesList:MutableLiveData<List<Movie>>?=null
    private var _sortType=0

    var sortType:Int=0
        get() = _sortType

    var isLoading:LiveData<Boolean> = MutableLiveData()
        get() = _isDataLoading

    init {
        viewModelScope.launch {
            movieRepository.getMoviesFromFile(app.applicationContext)
        }
    }

    fun getMoviesList():LiveData<List<Movie>>{
        if(moviesList!=null)
            return moviesList as LiveData<List<Movie>>

        moviesList= MovieRepository.movieList as MutableLiveData<List<Movie>>
        return moviesList as LiveData<List<Movie>>
    }

    fun sortMoviesByYear() {
        viewModelScope.launch {
            _isDataLoading.postValue(true)
            _sortType=1
            movieRepository.sortMoviesByYear()
        }
    }

    fun sortMoviesAsPerName() {
        viewModelScope.launch {
            _isDataLoading.postValue(true)
            _sortType=2
            movieRepository.sortMoviesByName()
        }
    }

    fun readReadTheData() {
        viewModelScope.launch {
            _isDataLoading.postValue(true)
            _sortType=0
            movieRepository.readFileAgain()
        }
    }

}