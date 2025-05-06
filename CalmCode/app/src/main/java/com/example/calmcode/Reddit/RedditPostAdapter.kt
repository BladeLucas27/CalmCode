package com.example.calmcode.Reddit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.calmcode.R
import com.example.calmcode.utils.launchCustomTab
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContentProviderCompat.requireContext


class RedditPostAdapter : ListAdapter<RedditPost, RedditPostAdapter.ViewHolder>(DIFF) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img = view.findViewById<ImageView>(R.id.featuredImagecard)
        private val title = view.findViewById<TextView>(R.id.featuredTextcard)
        private val btn = view.findViewById<Button>(R.id.featuredButtoncard)

        fun bind(post: RedditPost) {
            title.text = post.title
            val imgUrl = if (post.thumbnail.startsWith("http")) post.thumbnail else null
            Glide.with(img.context)
                .load(imgUrl ?: R.mipmap.reddit_foreground)
                .into(img)

            val context = itemView.context
            val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            val useReddit = prefs.getBoolean("useRedditForArticles", false)

            val url = "https://www.reddit.com${post.permalink}"

            btn.setOnClickListener {
                if (useReddit) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                } else {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.intent.setPackage("com.android.chrome")
                    customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    try {
                        customTabsIntent.launchUrl(context, Uri.parse(url))
                    } catch (e: Exception) {
                        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(fallbackIntent)
                    }
                }
            }
        }

//        fun bind(post: RedditPost) {
//            title.text = post.title
//            val imgUrl = if (post.thumbnail.startsWith("http")) post.thumbnail else null
//            Glide.with(img.context)
//                .load(imgUrl ?: R.drawable.ic_launcher_foreground)
//                .into(img)
//
//            val prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
//            val useReddit = prefs.getBoolean("useReddit", false)
//
//            if (useReddit) {
//                btn.setOnClickListener {
//                    val url = "https://www.reddit.com${post.permalink}"
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    btn.context.startActivity(intent)
//                }
//            } else {
//                btn.setOnClickListener {
//                    val url = "https://www.reddit.com${post.permalink}"
//                    val builder = CustomTabsIntent.Builder()
//
//                    val customTabsIntent = builder.build()
//
//                    val packageName = "com.android.chrome"
//                    val context = btn.context
//
//                    customTabsIntent.intent.setPackage(packageName)
//                    customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//
//                    try {
//                        customTabsIntent.launchUrl(context, Uri.parse(url))
//                    } catch (e: Exception) {
//                        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                        context.startActivity(fallbackIntent)
//                    }
//                }
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reddit_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<RedditPost>() {
            override fun areItemsTheSame(old: RedditPost, new: RedditPost) = old.permalink == new.permalink
            override fun areContentsTheSame(old: RedditPost, new: RedditPost) = old == new
        }
    }
}
