package com.example.desafiofoton.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.repository.MovieRepositoryInterface
import com.example.desafiofoton.repository.MovieRepositoryResult
import kotlinx.coroutines.launch

class MovieResultsViewModel(repository: MovieRepositoryInterface) : ViewModel() {
    private var _page = 1
    private val tag = "MovieResultsViewModel"
    private val _repository = repository
    private val _movies = MutableLiveData<List<Movie>>()
    val page = _page
    val movies : MutableLiveData<List<Movie>> = _movies

    init {
        viewModelScope.launch {
            updateMovies()
        }
    }

    fun updateMovies() {
        _repository.getPopular(_page) { movieResults ->
            when (movieResults) {
                is MovieRepositoryResult.Success -> {
                    movies.value = movieResults.result.results
                }
                is MovieRepositoryResult.Error -> {
                    Log.e(tag, "Error loading popular movies.")
                }
            }
        }
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
    private val repository: MovieRepositoryInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieResultsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieResultsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}