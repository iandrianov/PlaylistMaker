package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val clearButton = findViewById<ImageView>(R.id.clearButton)
        val searchInput = findViewById<TextView>(R.id.searchInput)

        searchInput.doOnTextChanged { text, _, _, _ ->
            clearButton.visibility = if (text.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        clearButton.setOnClickListener {
            searchInput.setText("")
            searchInput.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchInput.windowToken, 0)
        }
    }


}