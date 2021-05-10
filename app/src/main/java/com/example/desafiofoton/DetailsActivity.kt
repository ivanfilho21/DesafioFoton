package com.example.desafiofoton

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.desafiofoton.interfaces.Endpoint
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private var movieId = 0
    private val tag = "DetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val title : TextView = findViewById(R.id.title)
        val poster : ImageView = findViewById(R.id.poster)
        val overview : TextView = findViewById(R.id.overview)
        val genre : TextView = findViewById(R.id.genre)
        val runtime : TextView = findViewById(R.id.runtime)
        findViewById<View>(R.id.back_button).setOnClickListener {
            finish()
        }

        if (intent == null) {
            finish()
        }

        movieId = intent.getIntExtra("movieId", 0)

        val client = NetworkUtils.getRetrofitInstance("https://api.themoviedb.org")
        val endpoint = client.create(Endpoint::class.java)
        val callback = endpoint.getMovie(movieId)

        callback.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful){
                    val movie = response.body() ?: return
                    Log.d(tag, "$movie.title")

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
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(tag, "${t.message}")
            }
        })
    }
}