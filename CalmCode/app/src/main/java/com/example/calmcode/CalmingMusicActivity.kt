package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.calmcode.app.myApplication
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
            (application as myApplication).calmingMusicList,
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
            }
        )
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MusicGenresActivity::class.java))
            finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
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

                updateStreakCounter(this)

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
}