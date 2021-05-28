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
    private val tag = "MovieResultsViewModel"
    private val _repository = repository
    private val _movies = MutableLiveData<List<Movie>>()
    private var _page: Int = 1

    val page get() = _page
    val movies : MutableLiveData<List<Movie>> = _movies

    fun fetchPopularMovies() {
        _repository.getPopular(_page) {
            handleResult(it)
        }
    }

    fun searchMovies(query: String) {
        _repository.search(query) {
            handleResult(it)
        }
    }

    fun incrementPage() {
        _page++
    }

    private fun handleResult(movieResults: MovieRepositoryResult) {
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