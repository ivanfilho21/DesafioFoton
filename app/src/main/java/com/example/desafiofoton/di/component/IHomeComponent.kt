package com.example.desafiofoton.di.component

import com.example.desafiofoton.MainActivity
import com.example.desafiofoton.di.module.HomeModule
import com.example.desafiofoton.di.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [HomeModule::class])
interface IHomeComponent {
    fun inject(activity: MainActivity)
}