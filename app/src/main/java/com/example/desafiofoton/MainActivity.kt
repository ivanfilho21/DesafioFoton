package com.example.desafiofoton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.desafio.Endpoint
import com.example.desafio.NetworkUtils
import com.example.desafio.Posts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPosts()
    }

    private fun getPosts() {
        val client = NetworkUtils.getRetrofitInstance("https://jsonplaceholder.typicode.com");
        val endpoint = client.create(Endpoint::class.java)
        val callback = endpoint.getPosts()

        callback.enqueue(object : Callback<List<Posts>> {
            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                response.body()?.forEach {
                    println(it.title)
//                    textView.text = textView.text.toString().plus(it.body)
                }
            }
        })
    }
}