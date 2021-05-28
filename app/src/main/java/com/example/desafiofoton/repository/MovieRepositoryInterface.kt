package com.example.desafiofoton.repository

interface MovieRepositoryInterface {
    fun get(page: Int, callback: (result: MovieRepositoryResult) -> Unit)
    fun getPopular(page: Int, callback: (result: MovieRepositoryResult) -> Unit)
    fun search(query: String, callback: (result: MovieRepositoryResult) -> Unit)
}