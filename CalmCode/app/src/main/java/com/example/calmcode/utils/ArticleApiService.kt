package com.example.calmcode.utils

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.Call

data class Article(
    val title: String,
    val url: String,
    val imageUrl: String
)

// data/NewsResponse.kt
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>
)

// data/NewsArticle.kt
data class NewsArticle(
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("publishedAt") val publishedAt: String
)


interface ArticleApiService {
    @GET("articles.json")
    fun getArticles(): Call<List<Article>>
}

