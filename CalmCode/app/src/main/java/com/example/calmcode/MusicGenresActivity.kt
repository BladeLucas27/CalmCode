package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MusicGenresActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_genres)

        val lvGenres = findViewById<ListView>(R.id.lvGenres)

        val genreList = mutableListOf("Calming", "Groovy", "Relaxing", "Uplifting", "Your Favorites")

        lvGenres.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genreList)

        lvGenres.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Selected ${genreList[position]} soundtrack!", Toast.LENGTH_LONG).show()
//            startActivity(Intent(this,CalmingMusicActivity::class.java))
            when (position) {
                0 -> startActivity(Intent(this,CalmingMusicActivity::class.java))
                1 -> startActivity(Intent(this,GroovyMusicActivity::class.java))
                2 -> startActivity(Intent(this,RelaxingMusicActivity::class.java))
                3 -> startActivity(Intent(this,UpliftingMusicActivity::class.java))
                4 -> startActivity(Intent(this,MusicFavoritesActivity::class.java))
            }
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}