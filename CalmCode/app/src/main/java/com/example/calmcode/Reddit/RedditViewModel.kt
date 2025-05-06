package com.example.calmcode.Reddit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calmcode.utils.RedditRetrofitInstance
import retrofit2.Call
import retrofit2.Response

class RedditViewModel : ViewModel() {
    private val _posts = MutableLiveData<List<RedditPost>>()
    val posts: LiveData<List<RedditPost>> get() = _posts

    private var after: String? = null
    private val currentList = mutableListOf<RedditPost>()

    fun loadNextPage() {
        RedditRetrofitInstance.api.getTopMeditationPosts(limit = 10, time = "month", after = after)
            .enqueue(object : retrofit2.Callback<RedditResponse> {
                override fun onResponse(call: Call<RedditResponse>, response: Response<RedditResponse>) {
                    val newPosts = response.body()?.data?.children
                        ?.map { it.data }
                        ?.filter { !it.over_18 } ?: return

                    after = response.body()?.data?.after
                    currentList.addAll(newPosts)
                    _posts.postValue(currentList)
                }

                override fun onFailure(call: Call<RedditResponse>, t: Throwable) {}
            })
    }
}
