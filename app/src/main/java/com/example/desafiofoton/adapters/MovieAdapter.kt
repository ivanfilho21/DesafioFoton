package com.example.desafiofoton.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiofoton.DetailsActivity
import com.example.desafiofoton.R
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.utils.NetworkUtils

class MovieAdapter(private val movieList: ArrayList<Movie?>) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
    class MyViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val cover: ImageView = view.findViewById(R.id.cover)

        fun bindItems(item: Movie?) {
            title.text = item?.title

            Glide.with(context)
                .load(NetworkUtils.getImageUrl(item?.posterPath, "185"))
                .centerCrop()
                .into(cover)

            itemView.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java).apply {
                    putExtra("movieId", item?.id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
        return MyViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}