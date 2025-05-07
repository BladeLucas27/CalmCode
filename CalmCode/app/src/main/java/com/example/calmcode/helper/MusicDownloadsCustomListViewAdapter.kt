package com.example.calmcode.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.calmcode.R
import com.example.calmcode.data.MusicTrack
class MusicDownloadsCustomListViewAdapter(private val context: Context, private val musicList: List<MusicTrack>,
                                          private val onPromptClick: (MusicTrack) -> Unit, private val onDownloadClick: (MusicTrack) -> Unit, private val onLongClick: (MusicTrack) -> Unit) : BaseAdapter() {
    override fun getCount(): Int = musicList.size
    override fun getItem(position: Int): Any = musicList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.template_custom_musicdownloads
            , parent, false)
        val musicTrack = musicList[position]

        val playPic = view.findViewById<ImageButton>(R.id.ivMusicButton)
        val musicName = view.findViewById<TextView>(R.id.tvMusicTrackName)
        val musicLength = view.findViewById<TextView>(R.id.tvMusicTrackLength)
        val downloadPic = view.findViewById<ImageButton>(R.id.ivDownloadButton)
        playPic.setImageResource(musicTrack.currentStatus)
        musicName.setText(musicTrack.trackName)
        downloadPic.setImageResource(R.drawable.baseline_download_for_offline_24)
        musicLength.setText(musicTrack.trackLength?.toComponents{ _, minutes, seconds, _ ->
            if(seconds >= 10)"${minutes}:${seconds}"
            else "${minutes}:0${seconds}" })

        playPic.setOnClickListener {
            onPromptClick(musicTrack)
        }
        downloadPic.setOnClickListener {
            onDownloadClick(musicTrack)
        }
        view.setOnLongClickListener {
            onLongClick(musicTrack)
            true
        }
        return view
    }
}