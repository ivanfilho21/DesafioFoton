package com.example.desafiofoton.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    @Expose
    var id : Int,

    @SerializedName("title")
    @Expose
    var title : String,

    @SerializedName("poster_path")
    @Expose
    var posterPath : String,

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath : String,

    @SerializedName("runtime")
    @Expose
    var runtime : Int,

    @SerializedName("overview")
    @Expose
    var overview : String,

    @SerializedName("genres")
    @Expose
    var genres : List<Genre?>
)