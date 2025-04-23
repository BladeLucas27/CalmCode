package com.example.calmcode.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//object RetrofitInstance {
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://brenttolentino.github.io/calmcode-articles/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val api: ArticleApiService by lazy {
//        retrofit.create(ArticleApiService::class.java)
//    }
//}

// network/RetrofitInstance.kt
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}


