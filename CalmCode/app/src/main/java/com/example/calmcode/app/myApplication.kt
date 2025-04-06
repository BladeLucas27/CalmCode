package com.example.calmcode.app

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import com.example.calmcode.R
import com.example.calmcode.data.MusicTrack
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class myApplication : Application(){
    private var username : String = ""
    private var password : String = ""
    private var email : String = ""
    var isSongPlaying : Int = 0
    var mediaPlayer : MediaPlayer? = null

    val calmingMusicList = listOf(
        MusicTrack("Kricketune", 0.minutes + 2.seconds, R.raw.kricketune),
        MusicTrack("Vibing Over Venus", 6.minutes + 51.seconds, R.raw.kricketune),
        MusicTrack("Morning", 2.minutes + 33.seconds, R.raw.morning),
        MusicTrack("Evening", 3.minutes + 6.seconds, R.raw.evening),
        MusicTrack("Late Night Radio", 4.minutes + 24.seconds, R.raw.kricketune),
        MusicTrack("A Very Brady Special", 6.minutes + 28.seconds, R.raw.kricketune),
        MusicTrack("Deep Relaxation", 51.minutes + 8.seconds, R.raw.kricketune),
        MusicTrack("Wholesome", 6.minutes + 4.seconds, R.raw.kricketune),
        MusicTrack("Past Sadness", 3.minutes + 33.seconds, R.raw.kricketune),
        MusicTrack("Smooth Lovin", 4.minutes + 19.seconds, R.raw.kricketune)
    )
    val groovyMusicList = listOf(
        MusicTrack("Galactic Rap", 2.minutes + 22.seconds, R.raw.kricketune),
        MusicTrack("Vibing Over Venus", 6.minutes + 51.seconds, R.raw.kricketune),
        MusicTrack("Paradise Found", 3.minutes + 7.seconds, R.raw.paradise_found),
        MusicTrack("Space Jazz", 6.minutes + 10.seconds, R.raw.kricketune),
    )
    val relaxingMusicList = listOf(
        MusicTrack("Galactic Rap", 2.minutes + 22.seconds, R.raw.kricketune)
    )
    val upliftingMusicList = listOf(
        MusicTrack("Adding the Sun", 2.minutes + 56.seconds, R.raw.kricketune)
    )
    val completeMusicList = listOf(
        calmingMusicList, groovyMusicList, relaxingMusicList, upliftingMusicList
    )

    fun getUsername() : String = this.username
    fun setUsername(username : String) {this.username = username}
    fun getPassword() : String = this.password
    fun setPassword(password : String) {this.password = password}
    fun getEmail() : String = this.email
    fun setEmail(email : String) {this.email = email}



    override fun onCreate() {
        super.onCreate()
        Log.e("Application Class Practice","MyApplication is called")
    }
}