package com.example.desafiofoton.presenter

import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.presenter.contract.IHomeContract
import com.example.desafiofoton.service.IHomeService
import org.androidannotations.annotations.res.MovieRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(private val view: IHomeContract.IHomeView?, private val service: IHomeService) : IHomeContract.IHomePresenter {
    private var page = 1

    override fun getPopular() {
        service.getPopular(page).enqueue(object : Callback<MovieResults> {
            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                page++
                view?.onSucess(response.body()?.results)
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                view?.onError()
            }
        })
    }

    override fun search(query: String) {
        service.search(query).enqueue(object : Callback<MovieResults> {
            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                view?.onSucess(response.body()?.results)
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                view?.onError()
            }
        })
    }
}