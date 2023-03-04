package com.example.learnservices

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings

class NewService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    // This is a started service because we are overriding the onStartCommand()
    // Execution of service will start on calling this method
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

        // play the audio on loop
        mediaPlayer.isLooping = true

        // start playing the media
        mediaPlayer.start()

        return START_STICKY
    }


    // Called when a Component is bounded to the service
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // Execution stops when onDestroy() is called
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }
}