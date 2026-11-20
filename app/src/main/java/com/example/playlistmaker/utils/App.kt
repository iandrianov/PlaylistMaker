
package com.example.playlistmaker.utils

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"
const val DARK_THEME_KEY = "dark_theme"

class App : Application() {

    private val sharedPrefs by lazy {
        getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()

        val darkThemeEnabled = sharedPrefs.getBoolean(DARK_THEME_KEY, false)

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
