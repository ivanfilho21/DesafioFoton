package com.example.desafiofoton

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofoton.adapters.MovieAdapter
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.repository.MovieRepository
import com.example.desafiofoton.viewmodel.MovieViewModel
import com.example.desafiofoton.viewmodel.MovieViewModelFactory

class MainActivity : AppCompatActivity() {
    private val movieList = ArrayList<Movie?>()
    private lateinit var viewModel : MovieViewModel

    private lateinit var loadMore : Button
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        loadMore = findViewById(R.id.load_more)
        progressBar = findViewById(R.id.progressbar)
        recyclerView = findViewById(R.id.movie_list)
        val searchView = findViewById<SearchView>(R.id.search_view)

        loadMore.visibility = View.GONE

        initRecyclerView()

        loadMore.setOnClickListener { view ->
            view.isEnabled = false
            viewModel.incrementPage()
            viewModel.updateMovies()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            MovieViewModelFactory(MovieRepository())).get(MovieViewModel::class.java
        )

        viewModel.movies.observe(this, Observer { list ->
            progressBar.visibility = View.GONE
            loadMore.visibility = View.VISIBLE
            loadMore.isEnabled = true

            list.forEach {
                movieList.add(it)
            }

            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        // Sets grid layout
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.setHasFixedSize(true)

        // provides basic animations on remove, add, and move events
        recyclerView.itemAnimator = DefaultItemAnimator()

        //
        recyclerAdapter = MovieAdapter(movieList)

        // now adding the adapter to recyclerview
        recyclerView.adapter = recyclerAdapter
    }

}