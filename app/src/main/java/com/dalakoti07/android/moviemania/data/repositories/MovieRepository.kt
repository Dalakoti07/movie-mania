package com.dalakoti07.android.moviemania.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dalakoti07.android.moviemania.data.models.Movie
import com.dalakoti07.android.moviemania.utils.NameWiseDataComparators
import com.dalakoti07.android.moviemania.utils.YearWiseDataComparators
import com.dalakoti07.android.moviemania.utils.ReadDataFromJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.jar.Attributes
import kotlin.system.measureTimeMillis

/**
 * Repository of MVVM layer
 */
object MovieRepository {
    private val TAG = "MovieRepository"
    private lateinit var receiverContext:Context

    private val _movieList=MutableLiveData<List<Movie>>()

    var movieList:LiveData<List<Movie>>
        get() = _movieList

    init {
        movieList= MutableLiveData()
    }

    private val searchMovieList=MutableLiveData<List<Movie>>()

    fun getSearchedList():LiveData<List<Movie>>{
        return searchMovieList
    }

    suspend fun getMoviesFromFile(context: Context){
        receiverContext=context
        performAsyncReading()
    }

    private suspend fun performAsyncReading() {
        GlobalScope.launch (Dispatchers.IO){
            measureTimeMillis {
                val response= ReadDataFromJson.getJsonDataFromAsset(receiverContext, "moviesDB.json").await()
                val gson = Gson()
                val listPersonType = object : TypeToken<List<Movie>>() {}.type

                var movies: List<Movie> = gson.fromJson(response, listPersonType)
                _movieList.postValue(movies)
            }.also {
                Log.d(TAG, "data reading from file completed in $it ms")
            }
        }
    }

    suspend fun sortMoviesByYear() {
        GlobalScope.launch(Dispatchers.Default) {
            measureTimeMillis {
                //sort the movies by date and adding delay to simulate better working
                delay(1000L)
                val tempMovies= _movieList.value
                val sortedList= tempMovies?.sortedWith(YearWiseDataComparators)
                Log.d(TAG,"list sorted as per year")
                _movieList.postValue(sortedList)
            }.also {
                Log.d(TAG, "sorting list as per year completed in $it ms")
            }
        }
    }

    suspend fun sortMoviesByName() {
        GlobalScope.launch(Dispatchers.Default) {
            //sort the movies by name
            delay(1000L)
            val tempMovies= _movieList.value
            val sortedList= tempMovies?.sortedWith(NameWiseDataComparators)
            Log.d(TAG,"list sorted as per name and its size")
            _movieList.postValue(sortedList)
        }
    }

    suspend fun readFileAgain() {
        performAsyncReading()
    }

    suspend fun searchMovieWithQuery(searchType: String, query: String) {
        if(query.length==0)
            return
        Log.d(TAG,"searching $searchType with query $query")

        GlobalScope.launch(Dispatchers.IO) {
            //fetch all movies again
            val response= ReadDataFromJson.getJsonDataFromAsset(receiverContext, "moviesDB.json").await()
            val gson = Gson()
            val listPersonType = object : TypeToken<List<Movie>>() {}.type

            var allMovies: List<Movie> = gson.fromJson(response, listPersonType)
            Log.d(TAG, "all movies size ${allMovies.size}")

            when(searchType){
                "genre" -> {
                    withContext(Dispatchers.Default) {
                        val validData = arrayListOf<Movie>()
                        for (movie in allMovies) {
                            movie.info?.genres?.let {
                                if (query.toLowerCase() in movie.info.genres.map { it.toLowerCase() })
                                    validData.add(movie)
                            }
                        }
                        Log.d(TAG, "valid data size ${validData.size}")
                        searchMovieList.postValue(validData)
                    }
                }
                "actor"-> {
                    val validData = arrayListOf<Movie>()
                    for (movie in allMovies) {
                        movie.info?.actors?.let {
                            movie.info.actors.map {
                                if(it.toLowerCase().contains(
                                                query.toLowerCase()
                                ))
                                    validData.add(movie)
                            }
                        }
                    }
                    Log.d(TAG, "valid data size ${validData.size}")
                    searchMovieList.postValue(validData)
                }
                "rating"-> {
                    val tempList=allMovies.filter {
                        query.toDouble() <= it.info.rating
                    }
                    searchMovieList.postValue(tempList)
                }
                "name"-> {
                    val tempList=allMovies.filter {
                         it.title.toLowerCase().contains(query.toLowerCase())
                    }
                    searchMovieList.postValue(tempList)
                }
            }
        }

    }

}