package com.example.calmcode.data

import com.example.calmcode.R
import kotlin.time.Duration.Companion.minutes

data class MusicTrack(
    var trackName: String = "",
    var trackLength: kotlin.time.Duration?,
    var currentStatus: Int = R.drawable.baseline_play_circle_24
)
