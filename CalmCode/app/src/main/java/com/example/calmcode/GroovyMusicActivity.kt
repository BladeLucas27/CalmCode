package com.example.calmcode

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcode.app.myApplication
import com.example.calmcode.data.MusicTrack
import com.example.calmcode.helper.MusicTracksCustomListViewAdapter
import com.example.calmcode.utils.toast

class GroovyMusicActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groovy_music)

        val listView = findViewById<ListView>(R.id.GroovyListView)

        listView.adapter = MusicTracksCustomListViewAdapter(
            this,
            (application as myApplication).groovyMusicList,
            onClick = {
                    musicTrack ->
//                Toast.makeText(this, musicTrack.trackName, Toast.LENGTH_SHORT).show()

                if(musicTrack.currentStatus == R.drawable.baseline_play_circle_24){
                    toast("Playing Music")
                    for(m in (application as myApplication).completeMusicList){
                        for(c in m){
                            if(c.currentStatus == R.drawable.baseline_pause_circle_24 && c != musicTrack){
                                onStop(c)
                            }
                        }
                    }
                    playMusic(musicTrack)
                } else{
                    (application as myApplication).isSongPlaying = 0
                    toast("Stopping Music")
                    onStop(musicTrack)
                }
            },
            onLongClick = {
                    musicTrack ->
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Add to Downloads")
                builder.setMessage("Would you like to add this track to the downloads tab?")

                builder.setPositiveButton("Add") { dialog, which ->
                    addToDownloads(musicTrack)
                    dialog.dismiss()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }

        )
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MusicGenresActivity::class.java))
            finish()
        }
    }
    private fun playMusic(track: MusicTrack) {
        val selectedMusic = track
        (application as myApplication).mediaPlayer?.release()
        (application as myApplication).mediaPlayer = null
        try{
            (application as myApplication).mediaPlayer = MediaPlayer.create(this, selectedMusic.music)
            (application as myApplication).mediaPlayer?.setOnPreparedListener {
                (application as myApplication).mediaPlayer?.start()
                selectedMusic.currentStatus = R.drawable.baseline_pause_circle_24
                recreate()
                (application as myApplication).isSongPlaying = 1
            }
            (application as myApplication).mediaPlayer?.setOnCompletionListener {
                toast("${selectedMusic.trackName} finished")
                (application as myApplication).mediaPlayer = null
                track.currentStatus = R.drawable.baseline_play_circle_24
                recreate()
                (application as myApplication).isSongPlaying = 0
            }
            (application as myApplication).mediaPlayer?.setOnErrorListener { mp, what, extra ->
                toast("Erorr playing audio")
                false
            }
            (application as myApplication).mediaPlayer?.let {
                it.setVolume(500.0f, 500.0f)//not working yet :((
            }
        } catch (e: Exception){
            e.printStackTrace()
            toast("Error loading audio")
        }

    }
    fun onStop(track: MusicTrack) {
        super.onStop()
        (application as myApplication).mediaPlayer?.stop()
        (application as myApplication).mediaPlayer?.release()
        (application as myApplication).mediaPlayer = null
        track.currentStatus = R.drawable.baseline_play_circle_24
        recreate()
    }
    fun addToDownloads(track: MusicTrack){
        (application as myApplication).downloadList.add(track)
    }
}