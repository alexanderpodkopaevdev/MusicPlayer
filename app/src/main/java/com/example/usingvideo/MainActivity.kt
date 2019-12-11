package com.example.usingvideo


import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    lateinit var audioManager: AudioManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        sbVolume.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        sbVolume.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.stuff)
        sbProcess.max = mediaPlayer.duration

        Timer().scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                sbProcess.progress = mediaPlayer.currentPosition
            }

        },0,1000)
        ivPlay.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                ivPlay.setImageResource(R.drawable.ic_play_arrow_orange_24dp)
            } else {
                mediaPlayer.start()
                ivPlay.setImageResource(R.drawable.ic_pause_orange_24dp)
            }
        }
        ivPrevious.setOnClickListener {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
            sbProcess.progress = 0
            ivPlay.setImageResource(R.drawable.ic_play_arrow_orange_24dp)
        }
        ivNext.setOnClickListener {
            mediaPlayer.pause()
            mediaPlayer.seekTo(mediaPlayer.duration)
            sbProcess.progress = mediaPlayer.duration
            ivPlay.setImageResource(R.drawable.ic_play_arrow_orange_24dp)

        }

        sbVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(
                    this@MainActivity,
                    "Volume is ${seekBar!!.progress}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        sbProcess.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

}
