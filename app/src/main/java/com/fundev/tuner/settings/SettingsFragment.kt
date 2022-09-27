package com.fundev.tuner.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.fundev.tuner.R
import com.fundev.tuner.constants.TUNING_PREFERENCE_KEY
import com.fundev.tuner.music.Tuning

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val listPreference: ListPreference? = findPreference(TUNING_PREFERENCE_KEY)
        listPreference?.entries = Tuning.ALL_TUNINGS.keys.toTypedArray()
        listPreference?.entryValues = Tuning.ALL_TUNINGS.keys.toTypedArray()
        listPreference?.setDefaultValue(Tuning.ALL_TUNINGS.keys.iterator().next())
    }
}