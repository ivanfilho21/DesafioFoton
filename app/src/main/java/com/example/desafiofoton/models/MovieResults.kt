package com.example.desafiofoton.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResults (
    @SerializedName("total_pages")
    @Expose
    var pages : Int,

    @SerializedName("page")
    @Expose
    var page : Int,

    @SerializedName("results")
    @Expose
    var results : List<Movie>,
)