package com.dalakoti07.android.moviemania.data.repositories

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dalakoti07.android.moviemania.data.models.Movie
import com.dalakoti07.android.moviemania.utils.ReadDataFromJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

object MovieRepository {
    private val TAG = "MovieRepository"

    private val _movieList=MutableLiveData<List<Movie>>()

    var movieList:LiveData<List<Movie>>
        get() = _movieList

    init {
        movieList= MutableLiveData()
    }

    suspend fun getMoviesFromFile(context: Context){
        GlobalScope.launch (Dispatchers.IO){
            measureTimeMillis {
                val response= ReadDataFromJson.getJsonDataFromAsset(context, "moviesDB.json").await()
                val gson = Gson()
                val listPersonType = object : TypeToken<List<Movie>>() {}.type

                var movies: List<Movie> = gson.fromJson(response, listPersonType)
                _movieList.postValue(movies)
            }.also {
                Log.d(TAG, "completed in $it ms")
            }
        }
    }

}