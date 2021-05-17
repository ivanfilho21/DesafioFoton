package com.example.desafiofoton.repository

import com.example.desafiofoton.interfaces.Endpoint
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.utils.NetworkUtils
import retrofit2.Call

class MovieRepository {
    private val client = NetworkUtils.getRetrofitInstance("https://api.themoviedb.org")
    private val endpoint = client.create(Endpoint::class.java)

    fun get(id : Int): Call<Movie> = endpoint.getMovie(id)

    fun getPopular(page: Int): Call<MovieResults> = endpoint.getPopularMovies(page)

    fun getSearch(query: String, page: Int): Call<MovieResults> = endpoint.search(query)
}