package com.example.calmcode

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ListView
import com.example.calmcode.app.myApplication
import com.example.calmcode.data.MusicTrack
import com.example.calmcode.helper.MusicTracksCustomListViewAdapter
import com.example.calmcode.utils.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class MusicDownloadsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloads)

        val listView = findViewById<ListView>(R.id.DownloadsListView)

        listView.adapter = MusicTracksCustomListViewAdapter(
            this,
            (application as myApplication).downloadList,
            onClick = {
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
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
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
        (application as myApplication).downloadList.remove(track)
    }
}