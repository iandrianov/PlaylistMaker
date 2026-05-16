package com.example.playlistmaker.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri
import com.example.playlistmaker.R

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_settings)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val switch = findViewById<Switch>(R.id.themeSwitch)

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        val link = "https://practicum.yandex.ru/android-developer/"
        val shareButton = findViewById<ImageView>(R.id.shareButton)
        shareButton.setOnClickListener {
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
                putExtra(Intent.EXTRA_EMAIL, arrayOf("example@mail.com"))
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    R.string.email_subject
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    R.string.email_text
                )
            }
            startActivity(Intent.createChooser(intent, "Отправить письмо"))
        }

        val userAgreementButton = findViewById<ImageView>(R.id.agreementButton)
        userAgreementButton.setOnClickListener {
            val url = "https://yandex.ru/legal/practicum_offer/ru"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
            startActivity(intent)
        }

    }
}
