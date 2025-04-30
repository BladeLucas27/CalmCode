package com.example.calmcode.data

import com.example.calmcode.R
data class MusicTrack(
    var trackName: String = "",
    var trackLength: kotlin.time.Duration?,
    var music: Int,
    var genre: String = "",
    var currentStatus: Int = R.drawable.baseline_play_circle_24,
    var favorite: Int = R.drawable.baseline_favorite_border_24
)