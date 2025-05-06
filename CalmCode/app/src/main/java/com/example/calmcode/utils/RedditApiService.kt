package com.example.calmcode.utils

import com.example.calmcode.Reddit.RedditResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApiService {
//    @GET("/r/meditation/top.json")
//    fun getTopMeditationPosts(
//        @Query("limit") limit: Int = 3,
//        @Query("t") time: String = "month"
//    ): Call<RedditResponse>

    @GET("/r/meditation/top.json")
    fun getTopMeditationPosts(
        @Query("limit") limit: Int = 10,
        @Query("t") time: String = "month",
        @Query("after") after: String? = null
    ): Call<RedditResponse>

}
