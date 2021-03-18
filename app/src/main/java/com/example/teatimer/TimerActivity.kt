package com.example.teatimer

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TimerActivity : AppCompatActivity() {

    private lateinit var timeText: TextView
    private lateinit var cancelFab: FloatingActionButton

    private lateinit var timer: CountDownTimer

    private lateinit var alarmUri: Uri
    private lateinit var ringtone : Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        // For single notification sound
        // alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        ringtone = RingtoneManager.getRingtone(this, alarmUri)

        timeText = findViewById(R.id.countdown)
        cancelFab = findViewById(R.id.cancelFab)

        // keep screen on

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val minutes = intent.getIntExtra(MainActivity.TIME_EXTRA, 0)

        val ms = (minutes * 60 * 1000).toLong()

        timer = object: CountDownTimer(ms, 1000) {
            override fun onTick(msToFinish: Long) {
                displayTimeLeft(msToFinish)
            }

            override fun onFinish() {
                alarm()
            }
        }

        timer.start()

        cancelFab.setOnClickListener {
            endTimerActivity()
        }
    }

    private fun endTimerActivity() {
        timer.cancel()
        ringtone.stop()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        this.finish()

    }

    override fun onDestroy() {
        super.onDestroy()
        endTimerActivity()
    }

    private fun displayTimeLeft(milliseconds: Long) {

        // convert to mins & secs
        val totalSecs = milliseconds / 1000
        val mins = totalSecs / 60
        val seconds = totalSecs % 60

        val secString = seconds.toString().padStart(2, '0')
        timeText.text = "$mins:$secString"
    }

    fun alarm() {
        ringtone.play()
    }

}