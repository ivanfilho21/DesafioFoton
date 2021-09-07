package com.example.desafiofoton.di.module

import com.example.desafiofoton.presenter.HomePresenter
import com.example.desafiofoton.presenter.contract.IHomeContract
import com.example.desafiofoton.service.HomeService
import com.example.desafiofoton.service.IHomeService
import dagger.Module
import dagger.Provides

@Module
class HomeModule(private val view: IHomeContract.IHomeView) {
    @Provides
    fun provideView(): IHomeContract.IHomeView = view

    @Provides
    fun providePresenter(view: IHomeContract.IHomeView, service: IHomeService): IHomeContract.IHomePresenter {
        return HomePresenter(view, service)
    }

    @Provides
    fun provideService(): IHomeService = HomeService()
}