package com.example.desafiofoton.repository

import com.example.desafiofoton.models.MovieResults

sealed class MovieRepositoryResult {
    lateinit var result : MovieResults

    class Success(r: MovieResults) : MovieRepositoryResult() {
        init {
            result = r
        }
    }

    class Error : MovieRepositoryResult()
}