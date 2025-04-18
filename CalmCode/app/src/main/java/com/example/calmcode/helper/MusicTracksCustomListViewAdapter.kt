package com.example.calmcode.helper

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.calmcode.R
import com.example.calmcode.data.MusicTrack
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit

class MusicTracksCustomListViewAdapter(private val context: Context, private val musicList: List<MusicTrack>,
    private val onClick: (MusicTrack) -> Unit) : BaseAdapter() {
        private var countDownTimer: CountDownTimer? = null
        private var mediaPlayer: MediaPlayer? = null
        private val handler = Handler(Looper.getMainLooper())
        private var isTracking = false
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
//        val progressbarTrack = view.findViewById<ProgressBar>(R.id.progressbarTrack)
//        val musicLengthCurrent = view.findViewById<TextView>(R.id.tvMusicTrackCurrent)
        buttonPic.setImageResource(musicTrack.currentStatus)
        musicName.setText(musicTrack.trackName)
        musicLength.setText(musicTrack.trackLength?.toComponents{ _, minutes, seconds, _ ->
            if(seconds > 10)"${minutes}:${seconds}"
            else "${minutes}:0${seconds}" })

        view.setOnClickListener {
            onClick(musicTrack)
//            if(musicTrack.currentStatus == R.drawable.baseline_play_circle_24){
//                println("Music Played")
////                musicTrack.currentStatus = R.drawable.baseline_play_circle_24
//                startTimer(musicTrack, buttonPic)
////                playMusic(context, musicTrack, progressbarTrack, musicLengthCurrent)
//            } else if(musicTrack.currentStatus == R.drawable.baseline_pause_circle_24){
//                println("Music Paused")
//                musicTrack.currentStatus = R.drawable.baseline_play_circle_24
//                startTimer(musicTrack, buttonPic)
////                countDownTimer?.cancel()
//            }
        }
        return view
    }
    private fun startTimer(track: MusicTrack, currentStatus: ImageButton){
        currentStatus.setImageResource(R.drawable.baseline_play_circle_24)
        var timeLeftInMillis = track.trackLength?.inWholeMilliseconds
        countDownTimer = object : CountDownTimer(timeLeftInMillis!!, 1000){
            override fun onTick(millisUntilFinished: Long) {
                currentStatus.setImageResource(R.drawable.baseline_pause_circle_24)
                timeLeftInMillis = millisUntilFinished
            }
            override fun onFinish() {
                currentStatus.setImageResource(R.drawable.baseline_play_circle_24)
            }
        }.start()
    }
    private fun playMusic(context: Context, track: MusicTrack, progressBar: ProgressBar, musicTrackLengthCurrent: TextView) {
        try{
            mediaPlayer = MediaPlayer.create(context, track.music)
            mediaPlayer?.setOnPreparedListener { mp ->
                val totalDuration = mp.duration
                progressBar.max = totalDuration

                mp.start()
                updateProgress(progressBar, musicTrackLengthCurrent)
            }
            mediaPlayer?.setOnCompletionListener {
                stopProgressTracking()
                progressBar.progress = 0
                musicTrackLengthCurrent.setText("0:00")
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun stopProgressTracking() {
//        handler.removeCallbacks(this::updateProgress)
        isTracking = false
    }

    private fun updateProgress(progressBar: ProgressBar, musicTrackLengthCurrent: TextView) {
        if(mediaPlayer != null && mediaPlayer!!.isPlaying){
            val currentPosition = mediaPlayer!!.currentPosition
            progressBar.progress = currentPosition
            musicTrackLengthCurrent.text = formatDuration(currentPosition.toLong())
//            handler.postDelayed(this::updateProgress, 1000)
            isTracking = true
        } else{
            isTracking = false
        }
    }
    private fun formatDuration(milliseconds: Long): String{
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%d:%02d", minutes, seconds)
    }
}