package com.example.calmcode.utils

data class RedditResponse(val data: RedditData)
data class RedditData(val children: List<RedditPostWrapper>)
data class RedditPostWrapper(val data: RedditPost)
data class RedditPost(
    val title: String,
    val url: String,
    val thumbnail: String, // Not always reliable
    val permalink: String
)
