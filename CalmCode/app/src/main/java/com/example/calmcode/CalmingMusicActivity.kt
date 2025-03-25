package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcode.data.MusicTrack
import com.example.calmcode.helper.MusicTracksCustomListViewAdapter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class CalmingMusicActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calming_music)

        val listView = findViewById<ListView>(R.id.CalmingListView)

        val calmingMusicList = listOf(
            MusicTrack("Vibing Over Venus", 6.minutes + 51.seconds),
            MusicTrack("Morning", 2.minutes + 33.seconds),
            MusicTrack("Evening", 3.minutes + 6.seconds),
            MusicTrack("Late Night Radio", 4.minutes + 24.seconds),
            MusicTrack("A Very Brady Special", 6.minutes + 28.seconds),
            MusicTrack("Deep Relaxation", 51.minutes + 8.seconds),
            MusicTrack("Wholesome", 6.minutes + 4.seconds)
        )

        listView.adapter = MusicTracksCustomListViewAdapter(
            this,
            calmingMusicList,
            onClick = {
                musicTrack -> Toast.makeText(this, musicTrack.trackName,
                Toast.LENGTH_LONG).show()
            }
        )

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MusicGenresActivity::class.java))
        }
    }
}