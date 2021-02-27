package com.dalakoti07.android.moviemania.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.dalakoti07.android.moviemania.R
import com.dalakoti07.android.moviemania.data.models.Movie
import com.dalakoti07.android.moviemania.ui.adapters.ActorsAdapter
import com.dalakoti07.android.moviemania.utils.Constants
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.content_movie_detail_content.*
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {
    lateinit var movie:Movie
    lateinit var actorAdapter:ActorsAdapter
    lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=NavHostFragment.findNavController(this)
        movie=arguments?.getSerializable(Constants.movieObject) as Movie
        toolbar.setTitle(movie.title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
        actorAdapter= ActorsAdapter(movie.info.actors)
        rv_actors.apply {
            adapter=actorAdapter
        }
        proliferateTheData()
    }

    @SuppressLint("SetTextI18n")
    private fun proliferateTheData() {
        Glide.with(requireContext())
                .load(movie.info.image_url).centerCrop()
                .into(iv_movie_cover)
        tv_movie_name.text=movie.title
        tv_release_date.append(movie.year.toString())
        tv_rating.text=movie.info.rating.toString()+"/10"
        tv_summary.text=movie.info.plot

        tv_director_val.text=movie.info.directors[0]

        //chips
        for(i in 0..movie.info.genres.size-1){
            val chip=Chip(context)
            chip.setText(movie.info.genres[i])
            chip_group.addView(chip)
        }
    }
}