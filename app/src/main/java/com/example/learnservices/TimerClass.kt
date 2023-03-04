package com.example.learnservices

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder

class TimerService : Service() {
    private val binder: IBinder = TimerBinder()
    private var countDownTimer: CountDownTimer? = null


    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun startTimer(duration: Long, interval: Long) {
        countDownTimer = object : CountDownTimer(duration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the UI with the remaining time.
            }

            override fun onFinish() {
                // Notify the client that the timer has finished.
            }
        }
        countDownTimer?.start()
    }

    fun stopTimer() {
        countDownTimer?.apply {
            this.cancel()
        }
        countDownTimer = null
    }

    fun resetTimer(duration: Long, interval: Long) {
        stopTimer()
        startTimer(duration, interval)
    }


    inner class TimerBinder : Binder() {
        fun getService(): TimerService {
            return this@TimerService
        }
    }
}