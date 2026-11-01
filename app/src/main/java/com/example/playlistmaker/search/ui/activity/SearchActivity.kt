package com.example.playlistmaker.search.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.player.ui.activity.PlayerActivity
import com.example.playlistmaker.adapters.TracksAdapter
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.search.ui.view_model.SearchResultState
import com.example.playlistmaker.search.ui.view_model.SearchViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tracksAdapter: TracksAdapter

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyAdapter: TracksAdapter

    private lateinit var placeholderContainer: LinearLayout
    private lateinit var placeholderImage: ImageView
    private lateinit var placeholderText: TextView
    private lateinit var retryButton: Button

    private lateinit var clearButton: ImageView
    private lateinit var backButton: ImageView
    private lateinit var searchInput: EditText
    private lateinit var historyBlock: ConstraintLayout

    private lateinit var progressBar: CircularProgressIndicator
    private lateinit var clearHistoryButton: Button

    private val searchDebounceDelay = 2000L

    private val handler = Handler(Looper.getMainLooper())

    private var isClickAllowed = true

    private val CLICK_DEBOUNCE_DELAY = 1000L

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.Companion.getFactory(
            Creator.provideTracksInteractor(),
            Creator.provideSearchHistoryInteractor(
                getSharedPreferences("search_history", MODE_PRIVATE)
            )
        )
    }

    private fun observeViewModel() {
        viewModel.screenStateLiveData.observe(this) { state ->

            when (val resultState = state.searchResultState) {
                SearchResultState.Loading -> showLoading()

                is SearchResultState.Content -> {
                    tracksAdapter.updateTracks(resultState.tracks)
                    showContent()
                }

                SearchResultState.Empty -> showEmpty()

                SearchResultState.Error -> showError()

                SearchResultState.Idle -> {
                    tracksAdapter.updateTracks(emptyList())
                    showContent()
                }
            }

            historyAdapter.updateTracks(state.searchHistory)
            showHistory()
        }
    }


    private val searchRunnable = Runnable {
        val query = searchInput.text.toString()

        if (query.isNotBlank()) {
            searchTracks(query)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        initViews()
        initRecyclerViews()
        initListeners()
        observeViewModel()
        viewModel.loadSearchHistory()
    }

    private fun initViews() {

        recyclerView = findViewById(R.id.recyclerViewTracks)

        historyRecyclerView = findViewById(R.id.historyRecyclerView)

        placeholderContainer = findViewById(R.id.placeholderContainer)
        placeholderImage = findViewById(R.id.placeholderImage)
        placeholderText = findViewById(R.id.placeholderText)

        retryButton = findViewById(R.id.retryButton)

        clearButton = findViewById(R.id.clearButton)
        backButton = findViewById(R.id.backButton)

        searchInput = findViewById(R.id.searchInput)

        historyBlock = findViewById(R.id.historyBlock)

        clearHistoryButton = findViewById(R.id.clearHistoryButton)

        progressBar = findViewById(R.id.progressBar)
    }

    private fun initRecyclerViews() {

        tracksAdapter = TracksAdapter(emptyList()) { track ->
            if (!clickDebounce()) return@TracksAdapter

            viewModel.addTrackToHistory(track)
            startActivity(
                PlayerActivity.Companion.createIntent(this, track)
            )
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = tracksAdapter

        historyAdapter = TracksAdapter(emptyList()) { track ->
            if (!clickDebounce()) return@TracksAdapter
            viewModel.addTrackToHistory(track)
            startActivity(
                PlayerActivity.Companion.createIntent(this, track)
            )
        }

        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initListeners() {

        backButton.setOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            handler.removeCallbacks(searchRunnable)
            searchInput.setText("")
            searchInput.clearFocus()
            hideKeyboard()
            viewModel.clearSearchResults()
        }

        clearHistoryButton.setOnClickListener {
            viewModel.clearSearchHistory()
        }

        retryButton.setOnClickListener {
            handler.removeCallbacks(searchRunnable)
            viewModel.retrySearch()
        }

        searchInput.setOnFocusChangeListener { _, _ ->
            showHistory()
        }

        searchInput.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                clearButton.isVisible = !s.isNullOrEmpty()

                showHistory()

                handler.removeCallbacks(searchRunnable)

                if (!s.isNullOrBlank()) {
                    handler.postDelayed(
                        searchRunnable,
                        searchDebounceDelay
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        searchInput.setOnEditorActionListener { v, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {

                val query = v.text.toString()

                if (query.isNotBlank()) {
                    handler.removeCallbacks(searchRunnable)
                    searchTracks(query)
                }

                true

            } else {
                false
            }
        }
    }

    private fun searchTracks(query: String) {
        historyBlock.visibility = View.GONE
        viewModel.searchTracks(query)

    }

    private fun showHistory() {
        val shouldShow =
            searchInput.hasFocus() &&
                    searchInput.text.isEmpty() &&
                    historyAdapter.itemCount > 0

        historyBlock.visibility =
            if (shouldShow) View.VISIBLE else View.GONE
    }

    private fun showPlaceholder(
        imageRes: Int,
        textRes: Int,
        showRetryButton: Boolean
    ) {

        recyclerView.visibility = View.GONE

        placeholderContainer.visibility = View.VISIBLE

        placeholderImage.setImageResource(imageRes)

        placeholderText.setText(textRes)

        retryButton.visibility =
            if (showRetryButton) View.VISIBLE else View.GONE
    }

    private fun showEmpty() {
        progressBar.visibility = View.GONE
        placeholderContainer.visibility = View.VISIBLE
        showPlaceholder(
            R.drawable.ic_search_failure,
            R.string.nothing_found_music,
            false
        )
    }

    private fun showError() {
        progressBar.visibility = View.GONE
        placeholderContainer.visibility = View.VISIBLE
        showPlaceholder(
            R.drawable.ic_search_error,
            R.string.network_error_music,
            true
        )
    }

    private fun showContent() {

        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        placeholderContainer.visibility = View.GONE
    }

    private fun hideKeyboard() {

        val imm =
            getSystemService(INPUT_METHOD_SERVICE)
                    as InputMethodManager

        imm.hideSoftInputFromWindow(
            searchInput.windowToken,
            0
        )
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed

        if (isClickAllowed) {
            isClickAllowed = false

            handler.postDelayed(
                { isClickAllowed = true },
                CLICK_DEBOUNCE_DELAY
            )
        }

        return current
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        placeholderContainer.visibility = View.GONE
        retryButton.visibility = View.GONE
    }
}