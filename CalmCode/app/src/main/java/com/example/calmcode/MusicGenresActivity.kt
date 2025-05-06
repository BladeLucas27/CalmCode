package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.calmcode.app.calmcodeApplication
import com.example.calmcode.helper.MusicGenresCustomListViewAdapter

class MusicGenresActivity : Activity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_genres)

        val lvGenres = findViewById<ListView>(R.id.lvGenres)

        lvGenres.adapter = MusicGenresCustomListViewAdapter(
            this,
            (application as calmcodeApplication).genreList,
            onPromptClick = {
                genre ->
//                Toast.makeText(this, genre.genreName, Toast.LENGTH_SHORT).show()
                when (genre.genreName) {
                    "Calming" -> startActivity(Intent(this,CalmingMusicActivity::class.java))
                    "Groovy" -> startActivity(Intent(this,GroovyMusicActivity::class.java))
                    "Relaxing" -> startActivity(Intent(this,RelaxingMusicActivity::class.java))
                    "Uplifting" -> startActivity(Intent(this,UpliftingMusicActivity::class.java))
                    "Your Favorites" -> startActivity(Intent(this,MusicFavoritesActivity::class.java))
                    else -> {recreate()}
                }
            }
        )
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener{
//            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}