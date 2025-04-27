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

class MusicDownloadsCustomListViewAdapter(private val context: Context, private val musicList: List<MusicTrack>,
                                          private val onPromptClick: (MusicTrack) -> Unit, private val onDownloadClick: (MusicTrack) -> Unit, private val onLongClick: (MusicTrack) -> Unit) : BaseAdapter() {
        private var countDownTimer: CountDownTimer? = null
        private var mediaPlayer: MediaPlayer? = null
        private val handler = Handler(Looper.getMainLooper())
        private var isTracking = false
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
//        val progressbarTrack = view.findViewById<ProgressBar>(R.id.progressbarTrack)
//        val musicLengthCurrent = view.findViewById<TextView>(R.id.tvMusicTrackCurrent)
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