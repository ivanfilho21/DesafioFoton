package com.example.desafiofoton

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofoton.adapters.MovieAdapter
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.repository.MovieRepository
import com.example.desafiofoton.viewmodel.MovieResultsViewModel
import com.example.desafiofoton.viewmodel.MovieResultsViewModelFactory

class SearchableActivity : AppCompatActivity() {
    private val movieList = ArrayList<Movie?>()
    private lateinit var viewModel: MovieResultsViewModel

    private lateinit var recyclerView : RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        recyclerView = findViewById(R.id.movie_list)
        progressBar = findViewById(R.id.progressbar)

        initRecyclerView()

        viewModel = ViewModelProvider(
            this,
            MovieResultsViewModelFactory(MovieRepository())
        ).get(MovieResultsViewModel::class.java)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                findViewById<TextView>(R.id.search_query).text = getString(R.string.quotes, query)
                progressBar.visibility = View.VISIBLE
                viewModel.updateMoviesSearch(query)
            }
        } else {
            finish()
        }

        findViewById<View>(R.id.back_button).setOnClickListener {
            finish()
        }

        viewModel.movies.observe(this, { list ->
            progressBar.visibility = View.GONE

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

        // now adding the adapter to recyclerview
        recyclerView.adapter = MovieAdapter(movieList)
    }
}