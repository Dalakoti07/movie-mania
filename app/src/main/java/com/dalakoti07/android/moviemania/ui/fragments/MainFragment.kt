package com.dalakoti07.android.moviemania.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dalakoti07.android.moviemania.R
import com.dalakoti07.android.moviemania.data.models.Movie
import com.dalakoti07.android.moviemania.ui.adapters.MovieAdapter
import com.dalakoti07.android.moviemania.utils.Constants
import com.dalakoti07.android.moviemania.utils.ReadDataFromJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class MainFragment : Fragment() {
    private val TAG = "MainFragment"
    lateinit var navController:NavController
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=NavHostFragment.findNavController(this)
        setUpRecyclerView()
        toolbar.titleMarginStart=getValueInPxs(16f)
        toolbar.setTitle("Movie Mania")
        fetchTheData()
    }

    private fun getValueInPxs(dpVal: Float): Int {
        val r: Resources = resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal,
            r.displayMetrics
        )
        return px.toInt()
    }

    private fun fetchTheData() {
        GlobalScope.launch (Dispatchers.IO){
            measureTimeMillis {
                val response= ReadDataFromJson.getJsonDataFromAsset(requireContext(), "moviesDB.json").await()
                //change the thread to main thread
                withContext(Dispatchers.Main){
                    useData(response)
                }
            }.also {
                Log.d(TAG, "completed in $it ms")
            }
        }
    }

    private fun setUpRecyclerView() {
        movieAdapter= MovieAdapter()
        rv_movies.apply {
            adapter=movieAdapter
        }
        movieAdapter.setOnItemClickListener {
            val bundle=Bundle()
            bundle.putSerializable(Constants.movieObject,it)
            //todo make sure that when u come back to main-frag data is not fetched again
            navController.navigate(R.id.action_mainFragment_to_movieDetailFragment,bundle)
        }
    }

    private fun useData(jsonFileString: String?){
        progress_bar.visibility=View.GONE
        jsonFileString?.let { parsed->
            Log.i(TAG,parsed)
            val gson = Gson()
            val listPersonType = object : TypeToken<List<Movie>>() {}.type

            var movies: List<Movie> = gson.fromJson(jsonFileString, listPersonType)
            movieAdapter.differ.submitList(movies)
        }
    }
}