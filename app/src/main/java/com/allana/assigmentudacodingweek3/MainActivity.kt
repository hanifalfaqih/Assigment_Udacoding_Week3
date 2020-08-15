package com.allana.assigmentudacodingweek3

import android.accounts.NetworkErrorException
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.allana.assigmentudacodingweek3.adapter.NewsAdapter
import com.allana.assigmentudacodingweek3.model.ArticlesNews
import com.allana.assigmentudacodingweek3.model.ResponseServer
import com.allana.assigmentudacodingweek3.network.ConfigNetwork
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var context = this
    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isConnected()){
            loadDataFromRetrofit()
        } else {
            try {
                loadDataFromRetrofit()
            } catch (e: NetworkErrorException){
                Log.e("Error!", e.message.toString())
            } finally {
                Toast.makeText(this@MainActivity, "Check your connection and try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadDataFromRetrofit(){
        ConfigNetwork.getRetrofit().getDataNews().enqueue(object : Callback<ResponseServer>{
            override fun onFailure(call: Call<ResponseServer>, t: Throwable) {
                progressBar.visibility = View.VISIBLE
                loadDataFromRetrofit()
            }
            override fun onResponse(
                call: Call<ResponseServer>,
                response: Response<ResponseServer>
            ) {
                if (response.isSuccessful){
                    progressBar.visibility = View.GONE
                    val status = response.body()?.status
                    if (status == "ok"){
                        response.body()?.articles?.let { showArticles(it) }
                    }
                }
            }
        })
    }

    fun isConnected(): Boolean{
        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null){
            info = connectivity!!.activeNetworkInfo
            if (info != null){
                if (info!!.state == NetworkInfo.State.CONNECTED){
                    return true
                }
            }
        }
        return false
    }

    private fun showArticles(articles: ArrayList<ArticlesNews>) {
        val newsAdapter = NewsAdapter(articles)
        rv_news.layoutManager = LinearLayoutManager(this)
        rv_news.adapter = newsAdapter

        newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
            override fun onItemClicked(news: ArticlesNews) {
                showSelectedNews(news)
            }
        })
    }

    fun showSelectedNews(news: ArticlesNews){
        val moveToSelectedNews = Intent(this@MainActivity, DetailNewsActivity::class.java)
        moveToSelectedNews.apply {
            putExtra(DetailNewsActivity.EXTRA_SOURCE_NAME, news.source?.name)
            putExtra(DetailNewsActivity.EXTRA_TITLE, news.title)
            putExtra(DetailNewsActivity.EXTRA_AUTHOR, news.author)
            putExtra(DetailNewsActivity.EXTRA_URL_TO_IMAGE, news.urlToImage)
            putExtra(DetailNewsActivity.EXTRA_DESCRIPTION, news.description)
            putExtra(DetailNewsActivity.EXTRA_PUBLISHED_AT, news.publishedAt)
            putExtra(DetailNewsActivity.EXTRA_URL, news.url)
        }
        startActivity(moveToSelectedNews)
    }
}