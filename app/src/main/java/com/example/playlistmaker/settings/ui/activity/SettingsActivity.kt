package com.example.playlistmaker.settings.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.settings.ui.view_model.SettingsViewModel
import com.example.playlistmaker.utils.PLAYLIST_MAKER_PREFERENCES
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModel.Companion.getFactory(
            Creator.provideSettingsInteractor(
                getSharedPreferences(
                    PLAYLIST_MAKER_PREFERENCES,
                    MODE_PRIVATE
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)

        viewModel.darkThemeEnabledLiveData.observe(this) { isEnabled ->
            if (themeSwitcher.isChecked != isEnabled) {
                themeSwitcher.isChecked = isEnabled
            }
        }

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.setDarkThemeEnabled(checked)

            AppCompatDelegate.setDefaultNightMode(
                if (checked) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }

        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        val shareButton = findViewById<ImageView>(R.id.shareButton)

        shareButton.setOnClickListener {

            val link = "https://practicum.yandex.ru/android-developer/"

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, link)
            }

            startActivity(Intent.createChooser(intent, "Поделиться приложением"))
        }

        val supportButton = findViewById<ImageView>(R.id.supportButton)

        supportButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_SENDTO).apply {

                data = "mailto:".toUri()

                putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf("example@mail.com")
                )

                putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(R.string.email_subject)
                )

                putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.email_text)
                )
            }

            startActivity(intent)
        }

        val agreementButton = findViewById<ImageView>(R.id.agreementButton)

        agreementButton.setOnClickListener {

            val url = "https://yandex.ru/legal/practicum_offer/ru"

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )

            startActivity(intent)
        }
    }

}