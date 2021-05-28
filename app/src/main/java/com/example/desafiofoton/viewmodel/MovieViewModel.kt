package com.example.desafiofoton.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.repository.MovieRepositoryInterface
import kotlinx.coroutines.launch

class MovieViewModel(id : Int, repository: MovieRepositoryInterface) : ViewModel() {
    private val _id = id
    private val _repository = repository
    private val _movie = MutableLiveData<Movie>()
    val movie : MutableLiveData<Movie> = _movie

    init {
        viewModelScope.launch {
            fetchMovie()
        }
    }

    fun fetchMovie() {
        _repository.get(_id) {
            movie.value = it.result.results[0]
        }
    }
}

class MovieViewModelFactory(
    private val id : Int,
    private val repository: MovieRepositoryInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(id, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}