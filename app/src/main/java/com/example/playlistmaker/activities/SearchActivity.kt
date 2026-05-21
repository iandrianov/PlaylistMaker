package com.example.playlistmaker.activities

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.adapters.TracksAdapter
import com.example.playlistmaker.model.TracksSearchResponse
import com.example.playlistmaker.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var traksAdapter: TracksAdapter
    private lateinit var emptyPlaceholder: LinearLayout
    private lateinit var placeholderContainer: LinearLayout
    private lateinit var placeholderImage: ImageView
    private lateinit var placeholderText: TextView
    private lateinit var retryButton: Button
    private var lastSearchQuery: String = ""

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
            clearButton.isVisible = !text.isNullOrEmpty()
        }

        traksAdapter = TracksAdapter(emptyList())

        searchInput.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                val query = v.text.toString()

                if (query.isNotBlank()) {
                    searchTracks(query)
                }

                true
            } else {
                false
            }
        }

        clearButton.setOnClickListener {
            searchInput.setText("")
            searchInput.clearFocus()

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchInput.windowToken, 0)
        }

        recyclerView = findViewById(R.id.recyclerViewTracks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        placeholderContainer = findViewById(R.id.placeholderContainer)
        placeholderImage = findViewById(R.id.placeholderImage)
        placeholderText = findViewById(R.id.placeholderText)
        retryButton = findViewById(R.id.retryButton)

        recyclerView.adapter = traksAdapter
    }

    private fun searchTracks(query: String) {
        lastSearchQuery = query
        RetrofitClient.musicApi.searchTracks(query)
            .enqueue(object : Callback<TracksSearchResponse> {
                override fun onResponse(
                    call: Call<TracksSearchResponse>,
                    response: Response<TracksSearchResponse>
                ) {

                    if (response.isSuccessful) {

                        val tracks = response.body()?.results ?: emptyList()

                        if (tracks.isNotEmpty()) {
                            showContent()
                            traksAdapter.updateTraks(tracks)

                        } else {
                            showEmpty()
                        }

                    } else {

                        showError()
                    }
                }

                override fun onFailure(
                    call: Call<TracksSearchResponse>,
                    t: Throwable
                ) {

                    showError()
                }
            })

        retryButton.setOnClickListener {

            if (lastSearchQuery.isNotEmpty()) {
                searchTracks(lastSearchQuery)
            }
        }
    }



    private fun showEmpty() {
        recyclerView.visibility = View.GONE
        placeholderContainer.visibility = View.VISIBLE
        placeholderText.setText((R.string.nothing_found_music))
        placeholderImage.setImageResource(R.drawable.ic_search_failure)

        retryButton.visibility = View.GONE
    }

    private fun showError() {
        recyclerView.visibility = View.GONE
        placeholderContainer.visibility = View.VISIBLE

        placeholderImage.setImageResource(R.drawable.ic_search_error)
        placeholderText.setText(R.string.network_error_music)

        retryButton.visibility = View.VISIBLE
    }

    private fun showContent() {
        recyclerView.visibility = View.VISIBLE
        placeholderContainer.visibility = View.GONE
    }
}

