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

    private var moviesList:MutableLiveData<List<Movie>>?=null

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

}