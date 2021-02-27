package com.dalakoti07.android.moviemania.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dalakoti07.android.moviemania.data.models.Movie
import com.dalakoti07.android.moviemania.data.repositories.MovieRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieSearchViewModel (
        app: Application,
        val movieRepository: MovieRepository
) : AndroidViewModel(app){
    private val TAG = "MovieSearchViewModel"
    private var _isDataLoading= MutableLiveData<Boolean>(false)
    private var moviesList: MutableLiveData<List<Movie>>?=null
    private var errorMessage=MutableLiveData<String>("")

    fun getErrorMessage():LiveData<String>{
        return errorMessage
    }

    /**
     * Search type are 0-> genre, 1-> actor, 2-> rating, 3->director
     */
    var searchType:Int=-1

    var isLoading: LiveData<Boolean> = MutableLiveData()
        get() = _isDataLoading

    fun getMoviesList():LiveData<List<Movie>>{
        if(moviesList!=null)
            return moviesList as LiveData<List<Movie>>;
        moviesList= MovieRepository.getSearchedList() as MutableLiveData<List<Movie>>
        return moviesList as LiveData<List<Movie>>
    }

    fun searchMoviesOfType(query:String){
        Log.d(TAG,"viewmodel search query $query")
        viewModelScope.launch {
            when(searchType){
                0-> {
                    _isDataLoading.value=true
                    movieRepository.searchMovieWithQuery("genre",query)
                    errorMessage.value=""
                }
                1-> {
                    _isDataLoading.value=true
                    movieRepository.searchMovieWithQuery("actor",query)
                    errorMessage.value=""
                }
                2-> {
                    try {
                        val fVal= query.toFloat()
                        movieRepository.searchMovieWithQuery("rating",query)
                        _isDataLoading.value=true
                        errorMessage.value=""
                    }catch (e:Exception){
                        errorMessage.value="Incorrect numerical value"
                    }
                }
                3-> {
                    movieRepository.searchMovieWithQuery("name",query)
                    _isDataLoading.value=true
                    errorMessage.value=""
                }
                else -> {
                    //nothing
                    errorMessage.value="Select a type of search"
                }
            }
        }
    }
}