package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

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

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_playlist)
    }

    private fun openActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}