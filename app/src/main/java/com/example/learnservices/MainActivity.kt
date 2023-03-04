package com.example.learnservices

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.learnservices.TimerService.TimerBinder


private const val TAG = "MAIN"
class MainActivity : AppCompatActivity() {
    private val mService: TimerService? = null
    private var mBound = false


    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // Cast the IBinder object to a TimerBinder object.
            val binder = service as TimerBinder

            // Get the TimerService object from the binder.
            val timerService = binder.getService()

            // Call methods on the TimerService object to interact with the service.
            timerService.startTimer(60000, 1000)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            // Handle the service disconnection.
            mBound = false;
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startButton)
        val stopButton = findViewById<Button>(R.id.stopButton)
        val serviceIntent = Intent(this@MainActivity, NewService::class.java)
        startButton.setOnClickListener {
            if (!isMyServiceRunning(NewService::class.java)) {
                startService(serviceIntent)
            }
        }

        stopButton.setOnClickListener {
            if (isMyServiceRunning(NewService::class.java)) {
                stopService(serviceIntent)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Bind to the service.
        val intent = Intent(this, TimerService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
        mBound = true
    }

    override fun onStop() {
        super.onStop()

        // Unbind from the service.
        if (mBound) {
            unbindService(connection)
            mBound = false
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Logging this onPause")
    }


    // Method to check if a service is running
    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}