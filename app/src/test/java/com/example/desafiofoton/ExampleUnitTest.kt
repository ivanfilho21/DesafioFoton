package com.example.desafiofoton

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.repository.MovieRepositoryInterface
import com.example.desafiofoton.repository.MovieRepositoryResult
import com.example.desafiofoton.viewmodel.MovieResultsViewModel
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
        val vm = getViewModel()
        val pageBefore = vm.page
        val pageAfter = pageBefore + 1
        println("Initial Page: $pageBefore")

        // When
        getViewModel().incrementPage()

        // Then
        Assert.assertEquals(pageAfter, vm.page)
    }

    @Test
    fun shouldLoadDataFromRepository() {
        //
        val vm = getViewModel()

        vm.updateMovies()

        vm.movies.observeForever {
            Assert.assertNotNull(it)
        }
    }

    private fun getViewModel(): MovieResultsViewModel {
        return MovieResultsViewModel(MockRepo())
    }

    class MockRepo : MovieRepositoryInterface {
        override fun getPopular(page: Int, callback: (result: MovieRepositoryResult) -> Unit) {
            callback(MovieRepositoryResult.Success(
                MovieResults(page + 1, page, listOf(
                    Movie(1, "Movie 1", "", "", 62, "", listOf()),
                    Movie(2, "Movie 2", "", "", 78, "", listOf()),
                ))
            ))
        }
    }
}