package com.example.desafiofoton.repository

interface MovieRepositoryInterface {
    fun getPopular(page: Int, callback: (result: MovieRepositoryResult) -> Unit)
}