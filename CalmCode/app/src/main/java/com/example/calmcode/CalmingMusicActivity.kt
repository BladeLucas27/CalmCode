package com.example.calmcode

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.calmcode.app.calmcodeApplication
import com.example.calmcode.data.MusicTrack
import com.example.calmcode.helper.MusicTracksCustomListViewAdapter
import com.example.calmcode.utils.toast
import com.example.calmcode.utils.updateStreakCounter

class CalmingMusicActivity : Activity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calming_music)

        val listView = findViewById<ListView>(R.id.CalmingListView)

        listView.adapter = MusicTracksCustomListViewAdapter(
            this,
            (application as calmcodeApplication).calmingMusicList,
            onPromptClick = {
                musicTrack ->
//                Toast.makeText(this, musicTrack.trackName, Toast.LENGTH_SHORT).show()

                if(musicTrack.currentStatus == R.drawable.baseline_play_circle_24){
                    toast("Playing Music")
                    for(m in (application as calmcodeApplication).completeMusicList){
                        for(c in m){
                            if(c.currentStatus == R.drawable.baseline_pause_circle_24 && c != musicTrack){
                                onStop(c)
                            }
                        }
                    }
                    playMusic(musicTrack)
                } else{
                    (application as calmcodeApplication).isSongPlaying = 0
                    toast("Stopping Music")
                    onStop(musicTrack)
                }
            },
            onFaveClick = {
                musicTrack ->
                if(musicTrack.favorite == R.drawable.baseline_favorite_border_24){
                    toast("Added track to favorites")
                    addToFavorites(musicTrack)
                } else{
                    toast("Removed track from favorites")
                    removeFromFavorites(musicTrack)
                }
            }
        ) { musicTrack ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add to Downloads")
            builder.setMessage("Would you like to add this track to the downloads page?")

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
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MusicGenresActivity::class.java))
            finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun playMusic(track: MusicTrack) {
        val selectedMusic = track
        (application as calmcodeApplication).mediaPlayer?.release()
        (application as calmcodeApplication).mediaPlayer = null
        try{
            (application as calmcodeApplication).mediaPlayer = MediaPlayer.create(this, selectedMusic.music)
            (application as calmcodeApplication).mediaPlayer?.setOnPreparedListener {
                (application as calmcodeApplication).mediaPlayer?.start()
                selectedMusic.currentStatus = R.drawable.baseline_pause_circle_24
                recreate()
                (application as calmcodeApplication).isSongPlaying = 1
            }
            (application as calmcodeApplication).mediaPlayer?.setOnCompletionListener {
                toast("${selectedMusic.trackName} finished")

                updateStreakCounter(this)

                (application as calmcodeApplication).mediaPlayer = null
                track.currentStatus = R.drawable.baseline_play_circle_24
                recreate()
                (application as calmcodeApplication).isSongPlaying = 0
            }
            (application as calmcodeApplication).mediaPlayer?.setOnErrorListener { mp, what, extra ->
                toast("Erorr playing audio")
                false
            }
        } catch (e: Exception){
            e.printStackTrace()
            toast("Error loading audio")
        }
    }
    fun onStop(track: MusicTrack) {
        super.onStop()
        (application as calmcodeApplication).mediaPlayer?.stop()
        (application as calmcodeApplication).mediaPlayer?.release()
        (application as calmcodeApplication).mediaPlayer = null
        track.currentStatus = R.drawable.baseline_play_circle_24
        recreate()
    }
    fun addToDownloads(track: MusicTrack){
        (application as calmcodeApplication).downloadList.add(track)
    }
    fun addToFavorites(track: MusicTrack){
        track.favorite = R.drawable.baseline_favorite_24
        (application as calmcodeApplication).favoritesList.add(track)
        recreate()
    }
    fun removeFromFavorites(track: MusicTrack){
        track.favorite = R.drawable.baseline_favorite_border_24
        (application as calmcodeApplication).favoritesList.remove(track)
        recreate()
    }
}