package com.example.desafiofoton.repository

import com.example.desafiofoton.interfaces.Endpoint
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository : MovieRepositoryInterface {
    private val client = NetworkUtils.getRetrofitInstance("https://api.themoviedb.org")
    private val endpoint = client.create(Endpoint::class.java)

    override fun get(page: Int, callback: (result: MovieRepositoryResult) -> Unit) {
        endpoint.getMovie(page).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val movie = response.body() ?: return
                callback(MovieRepositoryResult.Success(MovieResults(0, 0, listOf(movie))))
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback(MovieRepositoryResult.Error())
            }
        })
    }

    override fun getPopular(page: Int, callback: (result: MovieRepositoryResult) -> Unit) {
        endpoint.getPopularMovies(page).enqueue(object : Callback<MovieResults> {
            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                val results = response.body() ?: return
                callback(MovieRepositoryResult.Success(results))
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                callback(MovieRepositoryResult.Error())
            }
        })
    }
}