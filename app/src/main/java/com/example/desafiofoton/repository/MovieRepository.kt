package com.example.desafiofoton.repository

import com.example.desafiofoton.interfaces.Endpoint
import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.utils.NetworkUtils
import retrofit2.Call

class MovieRepository {
    private val client = NetworkUtils.getRetrofitInstance("https://api.themoviedb.org")
    private val endpoint = client.create(Endpoint::class.java)

    fun getPopular(page: Int): Call<MovieResults> {
        return endpoint.getPopularMovies(page)
    }
}