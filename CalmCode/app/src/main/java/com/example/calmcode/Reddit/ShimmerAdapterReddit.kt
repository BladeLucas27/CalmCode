package com.example.calmcode.Reddit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calmcode.R

class ShimmerAdapterReddit(private val itemCount: Int = 6) : RecyclerView.Adapter<ShimmerAdapterReddit.ShimmerViewHolder>() {

    class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shimmer_placeholder_reddit, parent, false)
        return ShimmerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {
        // No binding needed for placeholders
    }

    override fun getItemCount(): Int = itemCount
}
