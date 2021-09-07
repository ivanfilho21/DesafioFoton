package com.example.desafiofoton

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafiofoton.adapters.MovieAdapter
import com.example.desafiofoton.databinding.ActivityMainBinding
import com.example.desafiofoton.di.component.DaggerIHomeComponent
import com.example.desafiofoton.di.module.HomeModule
import com.example.desafiofoton.models.Movie
import com.example.desafiofoton.presenter.contract.IHomeContract
import com.example.desafiofoton.utils.DialogUtils
import javax.inject.Inject

@SuppressLint("NonConstantResourceId")
class MainActivity : AppCompatActivity(), IHomeContract.IHomeView {
    @Inject
    lateinit var presenter: IHomeContract.IHomePresenter

    private lateinit var binding : ActivityMainBinding
    private lateinit var recyclerAdapter: MovieAdapter

    private val movieList = ArrayList<Movie?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerIHomeComponent.builder()
            .homeModule(HomeModule(this))
            .build()
            .inject(this)

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

        loadMore()

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

    private fun loadMore() {
        binding.loadMore.isEnabled = false
        presenter.getPopular()
    }

    private fun preencherLista() {
        binding.progressbar.visibility = View.GONE
        binding.loadMore.visibility = View.VISIBLE
        binding.loadMore.isEnabled = true

        binding.movieRv.adapter?.notifyDataSetChanged()
    }

    override fun onSucess(movies: List<Movie>?) {
        movies?.let { list ->
            list.forEach {
                movieList.add(it)
            }
        }

        runOnUiThread {
            preencherLista()
        }
    }

    override fun onError() {
        DialogUtils.alertMessage(this, "Ocorreu um problema ao recuperar a lista de filmes. Tente novamente mais tarde.")
    }

}