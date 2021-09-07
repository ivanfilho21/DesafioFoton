package com.example.desafiofoton.service

import com.example.desafiofoton.models.MovieResults
import retrofit2.Call

interface IHomeService {
    fun getPopular(page: Int): Call<MovieResults>
    fun search(query: String): Call<MovieResults>
}