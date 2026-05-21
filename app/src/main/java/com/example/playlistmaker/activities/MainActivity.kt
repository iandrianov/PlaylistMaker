package com.example.playlistmaker.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MaterialButton>(R.id.search).setOnClickListener {
            openActivity(SearchActivity::class.java)
        }

        findViewById<MaterialButton>(R.id.media).setOnClickListener {
            openActivity(MediaActivity::class.java)
        }

        findViewById<MaterialButton>(R.id.engine).setOnClickListener {
            openActivity(SettingsActivity::class.java)
        }

        this.findViewById<MaterialButton>(R.id.filmSearch).setOnClickListener {
            openActivity(FilmsSearchActivity::class.java)
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_playlist)
    }

    private fun openActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}