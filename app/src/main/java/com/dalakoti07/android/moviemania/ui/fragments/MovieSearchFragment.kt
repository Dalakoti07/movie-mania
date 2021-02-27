package com.dalakoti07.android.moviemania.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dalakoti07.android.moviemania.R
import com.dalakoti07.android.moviemania.data.repositories.MovieRepository
import com.dalakoti07.android.moviemania.ui.activities.MainActivity
import com.dalakoti07.android.moviemania.ui.adapters.MovieAdapter
import com.dalakoti07.android.moviemania.ui.viewModels.MovieListViewModel
import com.dalakoti07.android.moviemania.ui.viewModels.MovieSearchViewModel
import com.dalakoti07.android.moviemania.ui.viewModels.MovieViewModelFactory
import com.dalakoti07.android.moviemania.utils.Constants
import kotlinx.android.synthetic.main.fragment_movie_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MovieSearchFragment : Fragment() {
    private var lastCalled=-1L
    private val TAG = "MovieSearchFragment"
    lateinit var navController: NavController
    lateinit var moviesAdapter:MovieAdapter
    lateinit var viewModel:MovieSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory= activity?.application?.let {
            MovieViewModelFactory(MovieRepository,
                    it
            )
        }
        viewModelFactory?.let {
            viewModel= ViewModelProvider(this, it).get(MovieSearchViewModel::class.java)
        }

        navController= NavHostFragment.findNavController(this)
        toolbar.setTitle("Search Movies")
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
        moviesAdapter= MovieAdapter()
        moviesAdapter.setOnItemClickListener {
            val bundle=Bundle()
            bundle.putSerializable(Constants.movieObject, it)
            navController.navigate(R.id.action_movieSearchFragment_to_movieDetailFragment, bundle)
        }
        rv_search_movies.adapter=moviesAdapter

        setUpObservables()
    }

    private fun setUpObservables() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if(it)
                progress_bar.visibility=View.VISIBLE
        })
        chip_genre.setOnClickListener { viewModel.searchType= 0}
        chip_actor.setOnClickListener { viewModel.searchType= 1}
        chip_rating.setOnClickListener { viewModel.searchType= 2}
        chip_director.setOnClickListener { viewModel.searchType= 3}
        viewModel.getMoviesList().observe(viewLifecycleOwner, Observer {
            moviesAdapter.addData(it)
            progress_bar.visibility=View.GONE
        })
        viewModel.getErrorMessage().observe(viewLifecycleOwner, Observer { errorText->
            tv_error.text=errorText
        })
        search_view.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                moviesAdapter.clearData()
                viewModel.searchMoviesOfType(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(System.currentTimeMillis()>=lastCalled+100){
                    moviesAdapter.clearData()
                    lastCalled=System.currentTimeMillis()
                    viewModel.searchMoviesOfType(newText.toString())
                }
                return true
            }
        })
    }
}