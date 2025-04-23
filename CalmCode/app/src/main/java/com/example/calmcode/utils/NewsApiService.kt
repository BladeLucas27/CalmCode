package com.example.calmcode.utils

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    fun getMeditationArticles(
        @Query("q")         query: String = "Meditation+Mindfulness",
        @Query("sortBy")    sortBy: String = "publishedAt",
//        @Query("sortBy")    sortBy: String = "popularity",
//        @Query("sortBy")    sortBy: String = "relevancy",
        @Query("pageSize")  pageSize: Int    = 3,
        @Query("apiKey")    apiKey: String
    ): Call<NewsResponse>
}
