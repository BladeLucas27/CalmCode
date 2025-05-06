package com.example.calmcode

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.calmcode.app.calmcodeApplication
import com.example.calmcode.helper.MusicGenresCustomListViewAdapter
import com.example.calmcode.utils.toast

class MusicGenresActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_genres)

        val lvGenres = findViewById<ListView>(R.id.lvGenres)

        lvGenres.adapter = MusicGenresCustomListViewAdapter(
            this,
            (application as calmcodeApplication).getGenres(),
            onPromptClick = {
                    genre ->
                toast("Going to " + genre.genreName + " tracks")
                when (genre.genreName) {
                    "Calming" -> { startActivity(Intent(this,CalmingMusicActivity::class.java))
//                        finish()
                    }
                    "Groovy" -> { startActivity(Intent(this,GroovyMusicActivity::class.java))
//                        finish()
                    }
                    "Relaxing" -> { startActivity(Intent(this,RelaxingMusicActivity::class.java))
//                        finish()
                    }
                    "Uplifting" -> { startActivity(Intent(this,UpliftingMusicActivity::class.java))
//                        finish()
                    }
                    "Your Favorites" -> {
                        val intent = Intent(this, MusicFavoritesActivity::class.java)
                        intent.putExtra("FROM_ACTIVITY", "GENRES")
                        startActivity(intent)
//                        finish()
                    }
                }
            }
        )
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}