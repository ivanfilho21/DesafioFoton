package com.example.desafiofoton

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.repository.MovieRepositoryInterface
import com.example.desafiofoton.repository.MovieRepositoryResult
import com.example.desafiofoton.viewmodel.MovieResultsViewModel
import com.example.desafiofoton.viewmodel.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ExampleUnitTest {
    @get: Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun shouldIncrementPageNumber() {
        // Given
        val vm = getMovieResultsViewModel()
        val pageBefore = vm.page
        val pageAfter = pageBefore + 1
        println("Initial Page: $pageBefore")

        // When
        vm.incrementPage()

        // Then
        Assert.assertEquals(pageAfter, vm.page)
    }

    @Test
    fun shouldLoadMovieFromRepository() {
        // Given
        val id = 1
        val vm = getMovieViewModel(id)

        // When
        vm.fetchMovie()

        // Then
        vm.movie.observeForever {
            Assert.assertEquals(id, it.id)
        }
    }

    @Test
    fun shouldLoadMovieListFromRepository() {
        // Given
        val vm = getMovieResultsViewModel()

        // When
        vm.fetchPopularMovies()

        // Then
        vm.movies.observeForever {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun shouldSearchMoviesWhenQueryIsNotEmpty() {
        // Given
        val vm = getMovieResultsViewModel()

        // When
        vm.searchMovies("test")

        // Then
        vm.movies.observeForever {
            Assert.assertNotNull(it)
        }
    }

    private fun getMovieViewModel(id: Int): MovieViewModel = MovieViewModel(id, MockRepo())

    private fun getMovieResultsViewModel(): MovieResultsViewModel = MovieResultsViewModel(MockRepo())

    class MockRepo : MovieRepositoryInterface {
        override fun get(page: Int, callback: (result: MovieRepositoryResult) -> Unit) {
            callback(MovieRepositoryResult.Success(
                MovieResults(0, 0, listOf(
                    Movie(1, "Movie 1", "", "", 62, "", listOf())
                ))
            ))

//            callback(MovieRepositoryResult.Error())
        }

        override fun getPopular(page: Int, callback: (result: MovieRepositoryResult) -> Unit) {
            callback(MovieRepositoryResult.Success(
                MovieResults(page + 1, page, listOf(
                    Movie(1, "Movie 1", "", "", 62, "", listOf()),
                    Movie(2, "Movie 2", "", "", 78, "", listOf()),
                ))
            ))
        }

        override fun search(query: String, callback: (result: MovieRepositoryResult) -> Unit) {
            callback(MovieRepositoryResult.Success(
                MovieResults(1, 1, listOf(
                    Movie(3, "Movie 3", "", "", 90, "", listOf())
                ))
            ))
        }
    }
}