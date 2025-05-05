package com.example.calmcode.app

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.example.calmcode.R
import com.example.calmcode.data.Genre
import com.example.calmcode.data.MusicTrack
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class calmcodeApplication : Application(){
    private var username : String = ""
    private var password : String = ""
    private var email : String = ""
    var isSongPlaying : Int = 0
    var mediaPlayer : MediaPlayer? = null

    private val PREFS_NAME = "user_prefs"
    private val KEY_USERNAME = "username"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"

    val calmingMusicList = listOf(
        MusicTrack("  Kricketune", 0.minutes + 2.seconds, R.raw.kricketune, "Calming"),
        MusicTrack("  Vibing Over Venus", 6.minutes + 51.seconds, R.raw.kricketune, "Calming"),
        MusicTrack("  Morning", 2.minutes + 33.seconds, R.raw.morning, "Calming"),
        MusicTrack("  Evening", 3.minutes + 6.seconds, R.raw.evening, "Calming"),
        MusicTrack("  Late Night Radio", 4.minutes + 24.seconds, R.raw.kricketune, "Calming"),
        MusicTrack("  Very Brady Special", 6.minutes + 28.seconds, R.raw.kricketune, "Calming"),
        MusicTrack("  Sincerely", 6.minutes + 15.seconds, R.raw.kricketune, "Calming"),
        MusicTrack("  Wholesome", 6.minutes + 4.seconds, R.raw.kricketune, "Calming"),
        MusicTrack("  Past Sadness", 3.minutes + 33.seconds, R.raw.kricketune, "Calming"),
    )
    val groovyMusicList = listOf(
        MusicTrack("  Galactic Rap", 2.minutes + 22.seconds, R.raw.galactic_rap, "Groovy"),
        MusicTrack("  Vibing Over Venus", 6.minutes + 51.seconds, R.raw.kricketune, "Groovy"),
        MusicTrack("  Paradise Found", 3.minutes + 7.seconds, R.raw.paradise_found, "Groovy"),
        MusicTrack("  Space Jazz", 6.minutes + 10.seconds, R.raw.kricketune, "Groovy"),
        MusicTrack("  Smooth Lovin", 4.minutes + 19.seconds, R.raw.kricketune, "Groovy"),
        MusicTrack("  Bossa Antigua", 4.minutes + 43.seconds, R.raw.kricketune, "Groovy"),
        MusicTrack("  Bummin Tremelo", 3.minutes + 12.seconds, R.raw.kricketune, "Groovy"),
        MusicTrack("  Shaving Mirror", 3.minutes + 26.seconds, R.raw.kricketune, "Groovy"),
        MusicTrack("  Poppers & Prosecco", 3.minutes + 14.seconds, R.raw.kricketune, "Groovy")
    )
    val relaxingMusicList = listOf(
        MusicTrack("  Equatorial Complex", 3.minutes + 0.seconds, R.raw.equatorial_complex, "Relaxing"),
        MusicTrack("  Ethereal Relaxation", 28.minutes + 6.seconds, R.raw.kricketune, "Relaxing"),
        MusicTrack("  Space Jazz", 6.minutes + 10.seconds, R.raw.kricketune, "Relaxing"),
        MusicTrack("  Gymnopedie No 1", 3.minutes + 7.seconds, R.raw.gymnopedie_no_1, "Relaxing"),
        MusicTrack("  Pepper's Theme", 3.minutes + 34.seconds, R.raw.kricketune, "Relaxing"),
        MusicTrack("  Almost Bliss", 5.minutes + 17.seconds, R.raw.kricketune, "Relaxing"),
        MusicTrack("  Angel Share", 3.minutes + 21.seconds, R.raw.kricketune, "Relaxing"),
        MusicTrack("  Bittersweet", 3.minutes + 22.seconds, R.raw.kricketune, "Relaxing"),
        MusicTrack("  Dewdrop Fantasy", 34.minutes + 58.seconds, R.raw.kricketune, "Relaxing"),
    )
    val upliftingMusicList = listOf(
        MusicTrack("  Adding the Sun", 2.minutes + 56.seconds, R.raw.adding_the_sun, "Uplifting"),
        MusicTrack("  Crinoline Dreams", 4.minutes + 6.seconds, R.raw.kricketune, "Uplifting"),
        MusicTrack("  Cheery Monday", 1.minutes + 20.seconds, R.raw.cheery_monday, "Uplifting"),
        MusicTrack("  Airship Serenity", 4.minutes + 0.seconds, R.raw.kricketune, "Uplifting"),
        MusicTrack("  Fretless", 5.minutes + 36.seconds, R.raw.kricketune, "Uplifting"),
        MusicTrack("  Funky Chunk", 3.minutes + 59.seconds, R.raw.kricketune, "Uplifting"),
        MusicTrack("  Montauk Point", 3.minutes + 40.seconds, R.raw.kricketune, "Uplifting"),
        MusicTrack("  Gonna Start v2", 2.minutes + 35.seconds, R.raw.kricketune, "Uplifting"),
        MusicTrack("  Americana", 3.minutes + 22.seconds, R.raw.kricketune, "Uplifting")
    )
    val downloadList : MutableList<MusicTrack> = mutableListOf()
    val favoritesList : MutableList<MusicTrack> = mutableListOf()
    val completeMusicList = listOf(
        calmingMusicList, groovyMusicList, relaxingMusicList, upliftingMusicList, favoritesList
    )
    val genreList = listOf(
        Genre("Calming", R.drawable.baseline_self_improvement_24),
        Genre("Groovy", R.drawable.baseline_album_24),
        Genre("Relaxing", R.drawable.baseline_hotel_24),
        Genre("Uplifting", R.drawable.baseline_light_mode_24),
        Genre("Your Favorites", R.drawable.baseline_favorite_border_24)
    )
    fun setUsername(username: String) {
        this.username = username
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putString(KEY_USERNAME, username)
            .apply()
    }

    fun getUsername(): String = this.username

    fun setEmail(email: String) {
        this.email = email
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putString(KEY_EMAIL, email)
            .apply()
    }

    fun getEmail(): String = this.email

    fun setPassword(password: String) {
        this.password = password
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putString(KEY_PASSWORD, password)
            .apply()
    }

    fun getPassword(): String = this.password

    override fun onCreate() {
        super.onCreate()

        Log.e("Application Class Practice","MyApplication is called")
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        username = prefs.getString(KEY_USERNAME, "Username") ?: "Username"
        email = prefs.getString(KEY_EMAIL, "Email") ?: "Email"
        password = prefs.getString(KEY_PASSWORD, "") ?: ""
    }
}