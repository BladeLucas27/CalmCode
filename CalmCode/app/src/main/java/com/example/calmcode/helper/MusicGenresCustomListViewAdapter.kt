package com.example.calmcode.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.calmcode.R
import com.example.calmcode.data.Genre
class MusicGenresCustomListViewAdapter(private val context: Context, private val genreList: List<Genre>,
                                       private val onPromptClick: (Genre) -> Unit) : BaseAdapter() {
    override fun getCount(): Int = genreList.size

    override fun getItem(position: Int): Any = genreList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.template_custom_genres
            , parent, false)
        val genre = genreList[position]

        val genreName = view.findViewById<TextView>(R.id.tvGenreName)
        val genreIcon = view.findViewById<ImageView>(R.id.ibGenreIcon)
        val faveCount = view.findViewById<TextView>(R.id.tvFavoriteCount)
        genreName.setText(genre.genreName)
        genreIcon.setImageResource(genre.icon)
        faveCount.setText(genre.favoriteCount.toString())

        view.setOnClickListener {
            onPromptClick(genre)
        }

        return view
    }
}