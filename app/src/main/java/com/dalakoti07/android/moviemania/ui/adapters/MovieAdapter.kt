package com.dalakoti07.android.moviemania.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.dalakoti07.android.moviemania.R
import com.dalakoti07.android.moviemania.data.models.Movie
import kotlinx.android.synthetic.main.rv_movie_item.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private lateinit var context: Context
    private val moviesList= arrayListOf<Movie>()
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleBg=itemView.findViewById<View>(R.id.title_background)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        context=parent.context
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_movie_item, parent, false
            )
        )
    }

    fun addData(list: List<Movie>){
        moviesList.clear()
        moviesList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData(){
        moviesList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie= moviesList[position]
        holder.itemView.apply {
            val options = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH)
            Glide.with(context)
                .asBitmap()
                .load(movie.info.image_url)
                .apply(options)
                .into(object : BitmapImageViewTarget(movie_poster) {
                    override fun onResourceReady(
                        bitmap: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        super.onResourceReady(bitmap, transition)
                        Palette.from(bitmap).generate { palette: Palette? ->
                            setTitleBackgroundColor(palette!!, holder)
                        }
                    }
                })
            movie_name.text=movie.title

            setOnClickListener{
                onItemClickListener?.let { it(movie) }
            }
        }
    }

    private fun setTitleBackgroundColor(
        palette: Palette,
        holder: MovieViewHolder
    ) {
        holder.titleBg.setBackgroundColor(
            palette.getVibrantColor(
                context.getResources().getColor(R.color.black_translucent_60)
            )
        )
    }

    private var onItemClickListener:((Movie)->Unit)?=null

    fun setOnItemClickListener(listener: (Movie) -> Unit){
        onItemClickListener=listener
    }

    override fun getItemCount(): Int {
        return moviesList.size
    };


}