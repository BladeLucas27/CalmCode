package com.example.calmcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calmcode.Reddit.RedditPostAdapter
import com.example.calmcode.Reddit.RedditViewModel
import com.example.calmcode.Reddit.ShimmerAdapterReddit

class CommunityActivity : AppCompatActivity() {

    private lateinit var redditViewModel: RedditViewModel
    private lateinit var redditAdapter: RedditPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val recyclerView = findViewById<RecyclerView>(R.id.redditRecyclerView)

        val shimmerAdapterReddit = ShimmerAdapterReddit() // placeholder adapter
        recyclerView.adapter = shimmerAdapterReddit

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        redditAdapter = RedditPostAdapter()

//        recyclerView.adapter = redditAdapter

        redditViewModel = ViewModelProvider(this)[RedditViewModel::class.java]

//        redditViewModel.posts.observe(this) { posts ->
//            recyclerView.adapter = redditAdapter
//            redditAdapter.submitList(posts.toList())
////            redditAdapter.submitList(posts.toList()) // or notifyDataSetChanged if using a mutable list
//        }

        redditViewModel.posts.observe(this) { posts ->
            if (!posts.isNullOrEmpty()) {
                redditAdapter.submitList(posts.toList())
                recyclerView.adapter = redditAdapter
            }
        }


        redditViewModel.loadNextPage() // Load the first page

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem >= totalItemCount - 4) { // Load more when 2 from bottom
                    redditViewModel.loadNextPage()
                }
            }
        })
    }
}


