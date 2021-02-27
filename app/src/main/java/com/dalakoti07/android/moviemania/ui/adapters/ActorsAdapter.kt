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
import kotlinx.android.synthetic.main.rv_actor_item.view.*

class ActorsAdapter(val actorsList:List<String>) : RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {

    inner class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_actor_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(this).load("https://akns-images.eonline.com/eol_images/Entire_Site/20191112/rs_600x600-191212115145-600x600-bradpitt-gj-12-12-19.jpg?fit=around%7C1200:1200&output-quality=90&crop=1200:1200;center,top")
                .into(iv_artist_avatar)
            tv_actor_name.text=actorsList[position]
        }
    }

    override fun getItemCount(): Int {
        return actorsList.size
    };

}