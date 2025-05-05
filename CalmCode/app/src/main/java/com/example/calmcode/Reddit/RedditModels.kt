package com.example.calmcode.Reddit

data class RedditResponse(val data: RedditData)
//data class RedditData(val children: List<RedditPostWrapper>)
data class RedditPostWrapper(val data: RedditPost)
data class RedditPost(
    val title: String,
    val url: String,
    val thumbnail: String, //sometimes doesn't get a picture from reddit so use a place holder for that
    val permalink: String,
    val over_18: Boolean
)


data class RedditData(
    val children: List<RedditChild>,
    val after: String?
)

data class RedditChild(
    val data: RedditPost
)

