
package com.example.playlistmaker.settings.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.settings.domain.SettingsInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private val darkThemeEnabled = MutableLiveData<Boolean>()
    val darkThemeEnabledLiveData: LiveData<Boolean> = darkThemeEnabled

    init {
        loadTheme()
    }

    private fun loadTheme() {
        darkThemeEnabled.value = settingsInteractor.isDarkThemeEnabled()
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        settingsInteractor.setDarkThemeEnabled(enabled)
        darkThemeEnabled.value = enabled
    }

    companion object {
        fun getFactory(
            settingsInteractor: SettingsInteractor
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(settingsInteractor)
            }
        }
    }
}
