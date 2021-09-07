package com.example.desafiofoton.presenter.contract

import com.example.desafiofoton.models.Movie

interface IHomeContract {
    interface IHomeView {
        fun onSucess(movies: List<Movie>?)
        fun onError()
    }

    interface IHomePresenter {
        fun getPopular()
        fun search(query: String)
    }
}