package com.example.calmcode.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RedditRetrofitInstance {
    val api: RedditApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RedditApiService::class.java)
    }
}
