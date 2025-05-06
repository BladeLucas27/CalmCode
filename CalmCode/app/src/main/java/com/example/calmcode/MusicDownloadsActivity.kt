package com.example.calmcode

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.calmcode.app.calmcodeApplication
import com.example.calmcode.data.MusicTrack
import com.example.calmcode.helper.MusicDownloadsCustomListViewAdapter
import com.example.calmcode.utils.toast
import com.example.calmcode.utils.updateStreakCounter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class MusicDownloadsActivity : Activity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloads)

        val listView = findViewById<ListView>(R.id.DownloadsListView)

        listView.adapter = MusicDownloadsCustomListViewAdapter(
            this,
            (application as calmcodeApplication).downloadList,
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
            onDownloadClick = {
                    musicTrack ->
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Download Track to Device")
                builder.setMessage("Would you like to download this track to your device?")

                builder.setPositiveButton("Download") { dialog, which ->
                    download(musicTrack)
                    dialog.dismiss()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            },
            onLongClick = {
                musicTrack ->
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Remove Track from Downloads")
                builder.setMessage("Would you like to remove this track from the download page?")

                builder.setPositiveButton("Remove") { dialog, which ->
                    removeFromDownloads(musicTrack)
                    dialog.dismiss()
                    recreate()
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
//            startActivity(Intent(this, HomeActivity::class.java))
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
    @RequiresApi(Build.VERSION_CODES.Q)
    fun download(track: MusicTrack){
        val inputStream: InputStream? = try {
            resources.openRawResource(track.music)
        } catch (e: Exception){
            toast("Cannot find raw resource")
            e.printStackTrace()
            return
        }
        if(inputStream != null){
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, track.trackName)
                put(MediaStore.MediaColumns.MIME_TYPE, "audio/mpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val resolver = contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let { outputUri ->
                try {
                    val outputStream: OutputStream? = resolver.openOutputStream(outputUri)
                    outputStream?.let {
                        val buffer = ByteArray(4 * 1024)
                        var bytesRead: Int
                        while(inputStream.read(buffer).also { bytesRead = it } != -1) {
                            it.write(buffer, 0, bytesRead)
                        }

                        it.close()
                        inputStream.close()
                        toast("File saved to Downloads")
                        return
                    }
                } catch (e: IOException){
                    resolver.delete(outputUri, null, null)
                    toast("Error downloading file")
                    e.printStackTrace()
                    return
                } finally {
                    inputStream.close()
                }
            }
            toast("Error creating download entry")
        }
    }
    fun removeFromDownloads(track: MusicTrack){
        (application as calmcodeApplication).downloadList.remove(track)
    }
}