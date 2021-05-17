package com.example.desafiofoton

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.repository.MovieRepository
import com.example.desafiofoton.utils.NetworkUtils
import com.example.desafiofoton.viewmodel.MovieViewModel
import com.example.desafiofoton.viewmodel.MovieViewModelFactory

class DetailsActivity : AppCompatActivity() {
    private lateinit var viewModel : MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        findViewById<View>(R.id.back_button).setOnClickListener {
            finish()
        }

        if (intent == null) {
            finish()
        }

        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(
                intent.getIntExtra("movieId", 0), MovieRepository()
            )
        ).get(MovieViewModel::class.java)

        viewModel.movie.observe(this, { movie ->
            updateUI(movie)
        })
    }

    private fun updateUI(movie: Movie) {
        val title : TextView = findViewById(R.id.title)
        val poster : ImageView = findViewById(R.id.poster)
        val overview : TextView = findViewById(R.id.overview)
        val genre : TextView = findViewById(R.id.genre)
        val runtime : TextView = findViewById(R.id.runtime)

        setTitle(movie.title)

        title.text = movie.title
        overview.text = movie.overview

        if (movie.genres.isNotEmpty()) {
            genre.text = movie.genres[0]?.name
        }

        runtime.text = getString(R.string.minutes, movie.runtime)

        Glide.with(this@DetailsActivity)
            .load(NetworkUtils.getImageUrl(movie.backdropPath, "500"))
            .centerCrop()
            .into(poster)
    }
}