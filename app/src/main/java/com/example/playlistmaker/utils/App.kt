package com.example.playlistmaker.utils

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"
const val DARK_THEME_KEY = "dark_theme"
class App : Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = getSharedPreferences(
            PLAYLIST_MAKER_PREFERENCES,
            MODE_PRIVATE
        )

        darkTheme = sharedPrefs.getBoolean(DARK_THEME_KEY, false)

        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {

        darkTheme = darkThemeEnabled

        val sharedPrefs = getSharedPreferences(
            PLAYLIST_MAKER_PREFERENCES,
            MODE_PRIVATE
        )

        sharedPrefs.edit()
            .putBoolean(DARK_THEME_KEY, darkThemeEnabled)
            .apply()

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}