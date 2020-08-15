package com.allana.assigmentudacodingweek3.network

import com.allana.assigmentudacodingweek3.model.ResponseServer
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @GET("v2/top-headlines?country=us&category=technology&apiKey=67e68b9a90cb401599681cff1d19e922")
    fun getDataNews():Call<ResponseServer>

}