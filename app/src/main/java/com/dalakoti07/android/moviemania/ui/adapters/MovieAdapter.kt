package com.dalakoti07.android.moviemania.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dalakoti07.android.moviemania.R
import com.dalakoti07.android.moviemania.data.models.Movie
import kotlinx.android.synthetic.main.rv_movie_item.view.*

/**
 * Usage of diff utils for better performance
 */
//todo add color palette
class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * Assuming that no two movies of same name were release on same date, that would act as unique id
     */
    private val differCallBack = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title+oldItem.info.release_date == newItem.title+oldItem.info.release_date
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_movie_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie=differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(movie.info.image_url)
                .into(movie_poster)
            movie_name.text=movie.title

            setOnClickListener{
                onItemClickListener?.let { it(movie) }
            }
        }
    }

    private var onItemClickListener:((Movie)->Unit)?=null

    fun setOnItemClickListener(listener:(Movie)->Unit){
        onItemClickListener=listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    };


}