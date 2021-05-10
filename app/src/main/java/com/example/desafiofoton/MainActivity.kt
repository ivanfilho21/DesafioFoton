package com.example.desafiofoton

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
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

class MainActivity : AppCompatActivity() {
    private val movieList = ArrayList<Movie?>()
    private val tag = "MainActivity"

    private var currentPage = 1
    private lateinit var loadMore : Button
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMore = findViewById(R.id.load_more)
        progressBar = findViewById(R.id.progressbar)
        recyclerView = findViewById(R.id.movie_list)
        val searchView = findViewById<SearchView>(R.id.search_view)

        loadMore.visibility = View.GONE

        initRecyclerView()
        getMovies()

        loadMore.setOnClickListener {
            currentPage++
            getMovies()
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
//                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initRecyclerView() {
        // Sets grid layout
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.setHasFixedSize(true)

        // provides basic animations on remove, add, and move events
        recyclerView.itemAnimator = DefaultItemAnimator()


        recyclerAdapter = MovieAdapter(movieList)

        // now adding the adapter to recyclerview
        recyclerView.adapter = recyclerAdapter
    }

    private fun getMovies() {
        val client = NetworkUtils.getRetrofitInstance("https://api.themoviedb.org")
        val endpoint = client.create(Endpoint::class.java)

        println("CURRENT PAGE $currentPage")
        val listCallback = endpoint.getPopularMovies(currentPage)

        loadMore.isEnabled = false
        progressBar.visibility = View.VISIBLE

        listCallback.enqueue(object : Callback<MovieResults> {
            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                Log.e(tag, "${t.message}")

                progressBar.visibility = View.GONE
                loadMore.visibility = View.VISIBLE
                loadMore.isEnabled = true
            }

            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                response.body()?.results?.forEach {
                    movieList.add(it)
                }

                recyclerAdapter.notifyDataSetChanged()

                progressBar.visibility = View.GONE
                loadMore.visibility = View.VISIBLE
                loadMore.isEnabled = true
            }
        })
    }

}