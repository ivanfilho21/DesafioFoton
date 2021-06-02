package com.example.desafiofoton.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieResultsViewModel(repository: MovieRepository) : ViewModel() {
    private var _page = 1
    private val tag = "MovieResultsViewModel"
    private val _repository = repository
    private val _movies = MutableLiveData<List<Movie>>()
    val movies : MutableLiveData<List<Movie>> = _movies

    fun updateMovies() {
        _repository.getPopular(_page).enqueue(object : Callback<MovieResults> {
            override fun onResponse(
                call: Call<MovieResults>,
                response: Response<MovieResults>
            ) {
                val res = response.body() ?: return
                movies.value = res.results
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                Log.e(tag, "Error loading popular movies.")
            }
        })
    }

    fun searchMovies(query: String) {
        _repository.search(query).enqueue(object : Callback<MovieResults> {
            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                val res = response.body() ?: return
                movies.value = res.results
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                Log.e(tag, "Error searching movies.")
            }
        })
    }

    fun incrementPage() {
        _page++
    }
}

class MovieResultsViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieResultsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieResultsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}