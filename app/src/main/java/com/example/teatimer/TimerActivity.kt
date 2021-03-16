package com.example.teatimer

import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TimerActivity : AppCompatActivity() {

    lateinit var timeText: TextView
    lateinit var cancelFab: FloatingActionButton

    lateinit var timer: CountDownTimer

    val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val ringtone = RingtoneManager.getRingtone(this, alarmUri)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

         timeText = findViewById<TextView>(R.id.countdown)
         cancelFab = findViewById<FloatingActionButton>(R.id.cancelFab)

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
            shutUp()
        }
    }

    private fun shutUp() {
        timer.cancel()
        ringtone?.stop()
        this.finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        shutUp()
    }

    fun displayTimeLeft(milliseconds: Long) {

        // convert to mins & secs
        val totalSecs = milliseconds / 1000
        val mins = totalSecs / 60
        val seconds = totalSecs % 60

        val secString = seconds.toString().padStart(2, '0')
        timeText.text = "$mins:$secString"
    }

    fun alarm() {
        ringtone?.play()
    }

}