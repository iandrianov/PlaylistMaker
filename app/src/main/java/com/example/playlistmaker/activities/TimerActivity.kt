package com.example.playlistmaker.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.playlistmaker.R

class TimerActivity : AppCompatActivity() {

    private lateinit var enterTimeField: EditText
    private lateinit var startButton: Button
    private lateinit var timerField: TextView

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        initViews()
        initListeners()
    }

    private fun initViews() {
        enterTimeField = findViewById(R.id.enterTime)
        startButton = findViewById(R.id.btnStart)
        timerField = findViewById(R.id.timer)
    }

    private fun initListeners() {
        startButton.setOnClickListener {

            val seconds = getSeconds() ?: return@setOnClickListener

            startButton.isEnabled = false

            startButton.setBackgroundColor(
                ContextCompat.getColor(this, R.color.icon_search_field)
            )

            timer(seconds)
        }
    }

    private fun getSeconds(): Int? {
        return enterTimeField.text.toString().toIntOrNull()
    }

    private fun timer(seconds: Int) {

        Thread {

            for (timeLeft in seconds downTo 1) {

                handler.post {
                    timerField.text = timeLeft.toString()
                }

                Thread.sleep(1000)
            }

            handler.post {
                timerField.text = "Done!"
                startButton.isEnabled = true
            }

        }.start()
    }
}