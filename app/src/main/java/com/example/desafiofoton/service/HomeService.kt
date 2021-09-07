package com.example.desafiofoton.service

import com.example.desafiofoton.interfaces.Endpoint
import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.utils.NetworkUtils
import retrofit2.Call

class HomeService : IHomeService {
    private val client = NetworkUtils.getRetrofitInstance("https://api.themoviedb.org")
    private val endpoint = client.create(Endpoint::class.java)

    override fun getPopular(page: Int): Call<MovieResults> {
        return endpoint.getPopularMovies(page)
    }

    override fun search(query: String): Call<MovieResults> {
        return endpoint.search(query)
    }
}