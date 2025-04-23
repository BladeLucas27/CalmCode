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
        MusicTrack("*Vibing Over Venus", 6.minutes + 51.seconds, R.raw.kricketune),
        MusicTrack("Morning", 2.minutes + 33.seconds, R.raw.morning),
        MusicTrack("Evening", 3.minutes + 6.seconds, R.raw.evening),
        MusicTrack("*Late Night Radio", 4.minutes + 24.seconds, R.raw.kricketune),
        MusicTrack("*A Very Brady Special", 6.minutes + 28.seconds, R.raw.kricketune),
        MusicTrack("*Deep Relaxation", 51.minutes + 8.seconds, R.raw.kricketune),
        MusicTrack("*Wholesome", 6.minutes + 4.seconds, R.raw.kricketune),
        MusicTrack("*Past Sadness", 3.minutes + 33.seconds, R.raw.kricketune),
    )
    val groovyMusicList = listOf(
        MusicTrack("Galactic Rap", 2.minutes + 22.seconds, R.raw.galactic_rap),
        MusicTrack("*Vibing Over Venus", 6.minutes + 51.seconds, R.raw.kricketune),
        MusicTrack("Paradise Found", 3.minutes + 7.seconds, R.raw.paradise_found),
        MusicTrack("*Space Jazz", 6.minutes + 10.seconds, R.raw.kricketune),
        MusicTrack("*Smooth Lovin", 4.minutes + 19.seconds, R.raw.kricketune),
        MusicTrack("*Bossa Antigua", 4.minutes + 43.seconds, R.raw.kricketune),
        MusicTrack("Bummin on Tremelo", 3.minutes + 12.seconds, R.raw.kricketune),//7
        MusicTrack("*Shaving Mirror", 3.minutes + 26.seconds, R.raw.kricketune),
        MusicTrack("*Poppers and Prosecco", 3.minutes + 14.seconds, R.raw.kricketune)
    )
    val relaxingMusicList = listOf(
        MusicTrack("Equatorial Complex", 3.minutes + 0.seconds, R.raw.equatorial_complex),
        MusicTrack("*Ethereal Relaxation", 28.minutes + 6.seconds, R.raw.kricketune),
        MusicTrack("*Space Jazz", 6.minutes + 10.seconds, R.raw.kricketune),
        MusicTrack("Gymnopedie No 1", 3.minutes + 7.seconds, R.raw.gymnopedie_no_1),
        MusicTrack("*Sincerely", 6.minutes + 15.seconds, R.raw.kricketune),
        MusicTrack("*Almost Bliss", 5.minutes + 17.seconds, R.raw.kricketune),//6
        MusicTrack("*Angel Share", 3.minutes + 21.seconds, R.raw.kricketune),
        MusicTrack("*Bittersweet", 3.minutes + 22.seconds, R.raw.kricketune),
        MusicTrack("*Dewdrop Fantasy", 34.minutes + 58.seconds, R.raw.kricketune),
    )
    val upliftingMusicList = listOf(
        MusicTrack("Adding the Sun", 2.minutes + 56.seconds, R.raw.adding_the_sun),
        MusicTrack("*Crinoline Dreams", 4.minutes + 6.seconds, R.raw.kricketune),
        MusicTrack("Cheery Monday", 1.minutes + 20.seconds, R.raw.cheery_monday),
        MusicTrack("*Airship Serenity", 4.minutes + 0.seconds, R.raw.kricketune),
        MusicTrack("*Fretless", 5.minutes + 36.seconds, R.raw.kricketune),
        MusicTrack("*Funky Chunk", 3.minutes + 59.seconds, R.raw.kricketune),
        MusicTrack("*Montauk Point", 3.minutes + 40.seconds, R.raw.kricketune),
        MusicTrack("*Gonna Start v2", 2.minutes + 35.seconds, R.raw.kricketune),
        MusicTrack("*Americana", 3.minutes + 22.seconds, R.raw.kricketune)
    )
    val completeMusicList = listOf(
        calmingMusicList, groovyMusicList, relaxingMusicList, upliftingMusicList
    )
    val downloadList : MutableList<MusicTrack> = mutableListOf()
    val favoritesList : MutableList<MusicTrack> = mutableListOf()

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