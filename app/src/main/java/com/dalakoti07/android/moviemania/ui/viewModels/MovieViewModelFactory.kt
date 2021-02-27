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
        return MovieListViewModel(application,movieRepository) as T
    }
}