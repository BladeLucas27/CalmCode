package com.example.calmcode.utils

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApiService {
    @GET("/r/meditation/top.json")
    fun getTopMeditationPosts(
        @Query("limit") limit: Int = 3,
        @Query("t") time: String = "month"
    ): Call<RedditResponse>
}
