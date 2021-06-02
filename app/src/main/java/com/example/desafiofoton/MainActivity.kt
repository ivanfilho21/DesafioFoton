package com.example.desafiofoton

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafiofoton.adapters.MovieAdapter
import com.example.desafiofoton.databinding.ActivityMainBinding
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.repository.MovieRepository
import com.example.desafiofoton.viewmodel.MovieResultsViewModel
import com.example.desafiofoton.viewmodel.MovieResultsViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
//            this.viewModel = viewModel
        }
    }
    private lateinit var recyclerAdapter: MovieAdapter

    private val movieList = ArrayList<Movie?>()
    private lateinit var viewModel : MovieResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loadMore.visibility = View.GONE

        initRecyclerView()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                intent = Intent(applicationContext, SearchableActivity::class.java).apply {
                    action = Intent.ACTION_SEARCH
                    putExtra(SearchManager.QUERY, query)
                }
                startActivity(intent)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        viewModel = ViewModelProvider(
            this,
            MovieResultsViewModelFactory(MovieRepository())
        ).get(MovieResultsViewModel::class.java)

        viewModel.movies.observe(this, { list ->
            binding.progressbar.visibility = View.GONE
            binding.loadMore.visibility = View.VISIBLE
            binding.loadMore.isEnabled = true

            list.forEach {
                movieList.add(it)
            }

            binding.movieRv.adapter?.notifyDataSetChanged()
        })

        binding.loadMore.setOnClickListener {
            loadMore()
        }
    }

    private fun initRecyclerView() {
        // Sets grid layout
        binding.movieRv.layoutManager = GridLayoutManager(this, 3)
        binding.movieRv.setHasFixedSize(true)

        // provides basic animations on remove, add, and move events
        binding.movieRv.itemAnimator = DefaultItemAnimator()

        //
        recyclerAdapter = MovieAdapter(movieList)

        // now adding the adapter to recyclerview
        binding.movieRv.adapter = recyclerAdapter
    }

    fun loadMore() {
        binding.loadMore.isEnabled = false
        viewModel.incrementPage()
        viewModel.updateMovies()
    }

}