package com.dalakoti07.android.moviemania.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dalakoti07.android.moviemania.R
import com.dalakoti07.android.moviemania.data.repositories.MovieRepository
import com.dalakoti07.android.moviemania.ui.activities.MainActivity
import com.dalakoti07.android.moviemania.ui.adapters.MovieAdapter
import com.dalakoti07.android.moviemania.ui.viewModels.MovieListViewModel
import com.dalakoti07.android.moviemania.ui.viewModels.MovieViewModelFactory
import com.dalakoti07.android.moviemania.utils.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_main.*


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
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener{
            onOptionsItemSelected(it)
        }
        val viewModelFactory= activity?.application?.let {
            MovieViewModelFactory(MovieRepository,
                    it
            )
        }
        viewModelFactory?.let {
            viewModel= ViewModelProvider(this, it).get(MovieListViewModel::class.java)
        }
        setUpObservables()
    }

    private fun setUpObservables() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if(it)
                progress_bar.visibility=View.VISIBLE
        })
        viewModel.getMoviesList().observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"change in list ")
            progress_bar.visibility = View.GONE
            movieAdapter.addData(it)
        })
        navController=NavHostFragment.findNavController(this)
        setUpRecyclerView()
        toolbar.titleMarginStart=(activity as MainActivity).getValueInPxs(16f)
        toolbar.setTitle("Movie Mania")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.option_sort->{
                createSortDialog()
                true
            }
            R.id.option_filter->{
                navController.navigate(R.id.action_mainFragment_to_movieSearchFragment)
                true
            }
            else-> false
        }
    }

    private fun createSortDialog() {
        val singleItems = arrayOf("Default List","Sort by Year", "Sort by Title")

        MaterialAlertDialogBuilder(requireContext())
                .setTitle("Sort by?")
                .setSingleChoiceItems(singleItems, viewModel.sortType) { dialog, which ->
                    when(which){
                        0->{
                            // do nothing
                            movieAdapter.clearData()
                            viewModel.readReadTheData()
                            dialog.dismiss()
                        }
                        1-> {
                            //Toast.makeText(context,"Sorting as per Year",Toast.LENGTH_SHORT).show()
                            movieAdapter.clearData()
                            viewModel.sortMoviesByYear()
                            dialog.dismiss()
                        }
                        2-> {
                            viewModel.sortMoviesAsPerName()
                            //Toast.makeText(context,"Sorting as per Name 2",Toast.LENGTH_SHORT).show()
                            movieAdapter.clearData()
                            dialog.dismiss()
                        }
                    }
                }
                .show()
    }

    private fun setUpRecyclerView() {
        movieAdapter= MovieAdapter()
        rv_movies.apply {
            adapter=movieAdapter
        }
        movieAdapter.setOnItemClickListener {
            val bundle=Bundle()
            bundle.putSerializable(Constants.movieObject, it)
            navController.navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle)
        }
    }
}