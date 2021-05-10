package com.example.desafiofoton.interfaces

import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.models.MovieResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {
    @GET("3/movie/{id}?api_key=f0817e52182ab1b04d68c3b1c624d6f0&language=pt-BR")
    fun getMovie(@Path("id") id : Int) : Call<Movie>

    @GET("3/movie/popular?api_key=f0817e52182ab1b04d68c3b1c624d6f0&language=pt-BR")
    fun getPopularMovies(@Query("page") page : Int) : Call<MovieResults>

    @GET("3/search/movie?api_key=f0817e52182ab1b04d68c3b1c624d6f0&language=pt-BR")
    fun search(@Query("query") s: String) : Call<MovieResults>
}