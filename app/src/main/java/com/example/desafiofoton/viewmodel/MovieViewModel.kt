package com.example.desafiofoton.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(id : Int, repository: MovieRepository) : ViewModel() {
    private val tag = "MovieViewModel"
    private val _id = id
    private val _repository = repository
    private val _movie = MutableLiveData<Movie>()
    val movie : MutableLiveData<Movie> = _movie

    init {
        viewModelScope.launch {
            updateMovie()
        }
    }

    fun updateMovie() {
        _repository.get(_id).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                movie.value = response.body() ?: return
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(tag, "Error loading movie.")
            }
        })
    }
}

class MovieViewModelFactory(
    private val id : Int,
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(id, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}