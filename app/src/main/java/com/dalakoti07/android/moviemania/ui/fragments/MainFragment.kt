package com.dalakoti07.android.moviemania.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dalakoti07.android.moviemania.R
import com.dalakoti07.android.moviemania.data.models.Movie
import com.dalakoti07.android.moviemania.data.repositories.MovieRepository
import com.dalakoti07.android.moviemania.ui.adapters.MovieAdapter
import com.dalakoti07.android.moviemania.ui.viewModels.MovieListViewModel
import com.dalakoti07.android.moviemania.ui.viewModels.MovieViewModelFactory
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
    lateinit var viewModel:MovieListViewModel

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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory= activity?.application?.let {
            MovieViewModelFactory(MovieRepository,
                it
            )
        }
        viewModelFactory?.let {
            viewModel= ViewModelProvider(this,it).get(MovieListViewModel::class.java)
        }
        viewModel.getMoviesList().observe(viewLifecycleOwner, Observer {
            progress_bar.visibility=View.GONE
            movieAdapter.differ.submitList(it)
        })
        navController=NavHostFragment.findNavController(this)
        setUpRecyclerView()
        toolbar.titleMarginStart=getValueInPxs(16f)
        toolbar.setTitle("Movie Mania")
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
}