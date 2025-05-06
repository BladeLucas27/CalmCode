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

    private val PREFS_NAME = "user_prefs"
    private val KEY_USERNAME = "username"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"

    private val calmingMusicList = mutableListOf(
        MusicTrack(" Vibing Over Venus", 6.minutes + 51.seconds, R.raw.vibing_over_venus, "Calming"),
        MusicTrack(" Morning", 2.minutes + 33.seconds, R.raw.morning, "Calming"),
        MusicTrack(" Evening", 3.minutes + 6.seconds, R.raw.evening, "Calming"),
        MusicTrack(" Late Night Radio", 4.minutes + 24.seconds, R.raw.late_night_radio, "Calming"),
        MusicTrack(" A Very Brady Special", 6.minutes + 28.seconds, R.raw.a_very_brady_pecial, "Calming"),
        MusicTrack(" Sincerely", 6.minutes + 15.seconds, R.raw.sincerely, "Calming"),
        MusicTrack(" Wholesome", 6.minutes + 4.seconds, R.raw.wholesome, "Calming"),
        MusicTrack("  Past Sadness", 3.minutes + 33.seconds, R.raw.past_sadness, "Calming"),
        MusicTrack(" Pokemon Sound", 0.minutes + 2.seconds, R.raw.kricketune, "Calming"),
    )
    private val groovyMusicList = mutableListOf(
        MusicTrack(" Galactic Rap", 2.minutes + 22.seconds, R.raw.galactic_rap, "Groovy"),
        MusicTrack(" Paradise Found", 3.minutes + 7.seconds, R.raw.paradise_found, "Groovy"),
        MusicTrack(" Space Jazz", 6.minutes + 10.seconds, R.raw.space_jazz, "Groovy"),
        MusicTrack(" Smooth Lovin", 4.minutes + 19.seconds, R.raw.smooth_lovin, "Groovy"),
        MusicTrack(" Bossa Antigua", 4.minutes + 43.seconds, R.raw.bossa_antigua, "Groovy"),
        MusicTrack(" Chill Wave", 3.minutes + 11.seconds, R.raw.chill_wave, "Groovy"),
        MusicTrack(" Bummin on Tremelo", 3.minutes + 12.seconds, R.raw.bummin_on_tremelo, "Groovy"),
        MusicTrack(" Shaving Mirror", 3.minutes + 26.seconds, R.raw.shaving_mirror, "Groovy"),
        MusicTrack(" Poppers and Prosecco", 3.minutes + 14.seconds, R.raw.poppers_and_prosecco, "Groovy")
    )
    private val relaxingMusicList = mutableListOf(
        MusicTrack(" Equatorial Complex", 3.minutes + 0.seconds, R.raw.equatorial_complex, "Relaxing"),
        MusicTrack(" Reawakening", 3.minutes + 34.seconds, R.raw.reawakening, "Relaxing"),
        MusicTrack(" Gymnopedie No 1", 3.minutes + 7.seconds, R.raw.gymnopedie_no_1, "Relaxing"),
        MusicTrack(" Pepper's Theme", 3.minutes + 54.seconds, R.raw.peppers_theme, "Relaxing"),
        MusicTrack(" Almost Bliss", 5.minutes + 17.seconds, R.raw.almost_bliss, "Relaxing"),
        MusicTrack(" Angel Share", 3.minutes + 21.seconds, R.raw.angel_share, "Relaxing"),
        MusicTrack(" Bittersweet", 3.minutes + 22.seconds, R.raw.bittersweet, "Relaxing"),
        MusicTrack(" In Your Arms", 2.minutes + 48.seconds, R.raw.in_your_arms, "Relaxing"),
        MusicTrack(" Fresh Air", 4.minutes + 54.seconds, R.raw.fresh_air, "Relaxing"),
    )
    private val upliftingMusicList = mutableListOf(
        MusicTrack(" Adding the Sun", 2.minutes + 56.seconds, R.raw.adding_the_sun, "Uplifting"),
        MusicTrack(" Crinoline Dreams", 4.minutes + 6.seconds, R.raw.crinoline_dreams, "Uplifting"),
        MusicTrack(" Cheery Monday", 1.minutes + 20.seconds, R.raw.cheery_monday, "Uplifting"),
        MusicTrack(" Airship Serenity", 4.minutes + 0.seconds, R.raw.airship_serenity, "Uplifting"),
        MusicTrack(" Fretless", 5.minutes + 36.seconds, R.raw.fretless, "Uplifting"),
        MusicTrack(" Funky Chunk", 3.minutes + 59.seconds, R.raw.funky_chunk, "Uplifting"),
        MusicTrack(" Montauk Point", 3.minutes + 40.seconds, R.raw.montauk_point, "Uplifting"),
        MusicTrack(" Gonna Start v2", 2.minutes + 35.seconds, R.raw.gonna_start, "Uplifting"),
        MusicTrack(" Americana", 3.minutes + 22.seconds, R.raw.americana, "Uplifting")
    )
    private val downloadList : MutableList<MusicTrack> = mutableListOf()
    private val favoritesList : MutableList<MusicTrack> = mutableListOf()
    var isSongPlaying : Int = 0
    var mediaPlayer : MediaPlayer? = null
    private val completeMusicList = listOf(
        calmingMusicList, groovyMusicList, relaxingMusicList, upliftingMusicList, favoritesList
    )
    private val genreList = listOf(
        Genre("Calming", R.drawable.baseline_self_improvement_24),
        Genre("Groovy", R.drawable.baseline_album_24),
        Genre("Relaxing", R.drawable.baseline_hotel_24),
        Genre("Uplifting", R.drawable.baseline_light_mode_24),
        Genre("Your Favorites", R.drawable.baseline_favorite_border_24)
    )
    fun getUsername() : String = this.username
    fun setUsername(username : String) {this.username = username}
    fun getPassword() : String = this.password
    fun setPassword(password : String) {this.password = password}
    fun getEmail() : String = this.email
    fun setEmail(email : String) {this.email = email}
    fun getCalm() : MutableList<MusicTrack> = calmingMusicList
    fun getGroovy() : MutableList<MusicTrack> = groovyMusicList
    fun getRelaxing() : MutableList<MusicTrack> = relaxingMusicList
    fun getUplifting() : MutableList<MusicTrack> = upliftingMusicList
    fun getFavorites() : MutableList<MusicTrack> = favoritesList
    fun getDownloads() : MutableList<MusicTrack> = downloadList
    fun getCompleteMusicList() : List<MutableList<MusicTrack>> = completeMusicList
    fun getGenres() : List<Genre> = genreList

    override fun onCreate() {
        super.onCreate()

        Log.e("Application Class Practice","MyApplication is called")
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        username = prefs.getString(KEY_USERNAME, "Username") ?: "Username"
        email = prefs.getString(KEY_EMAIL, "Email") ?: "Email"
        password = prefs.getString(KEY_PASSWORD, "") ?: ""
    }
}