package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.search).setOnClickListener {
            openActivity(SearchActivity::class.java)
        }

        findViewById<ImageView>(R.id.media).setOnClickListener {
            openActivity(MediaActivity::class.java)
        }

        findViewById<ImageView>(R.id.engine).setOnClickListener {
            openActivity(SettingsActivity::class.java)
        }
    }

    private fun openActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}