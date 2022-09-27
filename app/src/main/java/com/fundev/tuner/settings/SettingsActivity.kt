package com.fundev.tuner.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fundev.tuner.R

class SettingsActivity : AppCompatActivity(R.layout.settings) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsContainer, SettingsFragment())
            .commit()
    }
}