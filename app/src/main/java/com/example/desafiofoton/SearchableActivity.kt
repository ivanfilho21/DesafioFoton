package com.example.desafiofoton

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofoton.adapters.MovieAdapter
import com.example.desafiofoton.interfaces.Endpoint
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.models.MovieResults
import com.example.desafiofoton.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchableActivity : AppCompatActivity() {
    private val tag = "SearchableActivity"
    private lateinit var recyclerView : RecyclerView
    private lateinit var movieList : ArrayList<Movie?>
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        movieList = ArrayList()
        recyclerView = findViewById(R.id.movie_list)
        progressBar = findViewById(R.id.progressbar)

        initRecyclerView()

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                findViewById<TextView>(R.id.search_query).text = getString(R.string.quotes, query)
                search(query)
            }
        } else {
            finish()
        }

        findViewById<View>(R.id.back_button).setOnClickListener {
            finish()
        }
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

    private fun search(query: String) {
        val client = NetworkUtils.getRetrofitInstance("https://api.themoviedb.org")
        val endpoint = client.create(Endpoint::class.java)
        val callback = endpoint.search(query)

        progressBar.visibility = View.VISIBLE

        callback.enqueue(object : Callback<MovieResults> {
            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                Log.e(tag, "${t.message}")
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                response.body()?.results?.forEach {
                    Log.d(tag, it.title)
                    progressBar.visibility = View.GONE

                    movieList.add(it)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        })
    }
}