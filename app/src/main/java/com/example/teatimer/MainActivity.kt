package com.example.teatimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    // Kotlin's version of static
    companion object {
        const val TIME_EXTRA = "TIME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button1);
        val button2 = findViewById<Button>(R.id.button2);
        val button3 = findViewById<Button>(R.id.button3);
        val button4 = findViewById<Button>(R.id.button4);
        val button5 = findViewById<Button>(R.id.button5);

        val buttons = mapOf<Button, Int>(
            button1 to 1,    // minutes for each button
            button2 to 2,
            button3 to 3,
            button4 to 4,
            button5 to 5);

        buttons.forEach {
            val time = it.value
            it.key.setOnClickListener {
                startTimer(time)
            }
        }
    }

    private fun startTimer(time: Int) {

        val intent = Intent(this, TimerActivity::class.java).apply {
            putExtra(TIME_EXTRA, time)
        }
        startActivity(intent)
    }

}