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

class MusicTracksCustomListViewAdapter(private val context: Context, private val musicList: List<MusicTrack>,
    private val onClick: (MusicTrack) -> Unit) : BaseAdapter() {
    override fun getCount(): Int = musicList.size
    override fun getItem(position: Int): Any = musicList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.template_custom_musictracks
            , parent, false)

        val musicTrack = musicList[position]

        val buttonPic = view.findViewById<ImageButton>(R.id.ivMusicButton)
        val musicName = view.findViewById<TextView>(R.id.tvMusicTrackName)
        val musicLength = view.findViewById<TextView>(R.id.tvMusicTrackLength)
        buttonPic.setImageResource(musicTrack.currentStatus)
        musicName.setText(musicTrack.trackName)
        musicLength.setText(musicTrack.trackLength?.toComponents{ _, minutes, seconds, _ ->
            if(seconds > 10)"${minutes}:${seconds}"
            else "${minutes}:0${seconds}" })

        view.setOnClickListener {
            onClick(musicTrack)
            if(musicTrack.currentStatus == R.drawable.baseline_play_circle_24){
                musicTrack.currentStatus = R.drawable.baseline_pause_circle_24
            } else{ musicTrack.currentStatus = R.drawable.baseline_play_circle_24 }
            buttonPic.setImageResource(musicTrack.currentStatus)
        }
        return view
    }
}