package com.dalakoti07.android.moviemania.ui.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dalakoti07.android.moviemania.data.repositories.MovieRepository

class MovieViewModelFactory (
    val movieRepository: MovieRepository,
    val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java))
            return MovieListViewModel(application,movieRepository) as T
        if(modelClass.isAssignableFrom(MovieSearchViewModel::class.java))
            return MovieSearchViewModel(application,movieRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}