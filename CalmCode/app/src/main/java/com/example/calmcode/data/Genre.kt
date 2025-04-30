package com.example.calmcode.data

import com.example.calmcode.R

data class Genre(
    var genreName: String = "",
    var icon: Int = R.drawable.baseline_play_circle_24,
    var favoriteCount: Int = 0,
)