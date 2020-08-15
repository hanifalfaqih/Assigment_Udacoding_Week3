package com.allana.assigmentudacodingweek3.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigNetwork {

    companion object{
        fun getRetrofit() : NewsService{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val newsService = retrofit.create(NewsService::class.java)
            return newsService
        }
    }

}