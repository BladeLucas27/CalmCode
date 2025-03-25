package com.example.calmcode.app

import android.app.Application
import android.util.Log
import com.example.calmcode.data.MusicTrack
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class myApplication : Application(){
    private var username : String = ""
    private var password : String = ""
    private var email : String = ""

    val calmingMusicList = listOf(
        MusicTrack("Vibing Over Venus", 6.minutes + 51.seconds),
        MusicTrack("Morning", 2.minutes + 33.seconds),
        MusicTrack("Evening", 3.minutes + 6.seconds),
        MusicTrack("Late Night Radio", 4.minutes + 24.seconds),
        MusicTrack("A Very Brady Special", 6.minutes + 28.seconds),
        MusicTrack("Deep Relaxation", 51.minutes + 8.seconds),
        MusicTrack("Wholesome", 6.minutes + 4.seconds),
        MusicTrack("Past Sadness", 3.minutes + 33.seconds),
        MusicTrack("Smooth Lovin", 4.minutes + 19.seconds)
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