package com.fundev.tuner.controller

import android.content.Context
import androidx.preference.PreferenceManager
import com.fundev.tuner.audio.IAudioEngine
import com.fundev.tuner.audio.IAudioListener
import com.fundev.tuner.constants.TUNING_PREFERENCE_KEY
import com.fundev.tuner.music.Tuning

class Controller(private val audioEngine: IAudioEngine, val stateManager: StateManager) {

    private var recording : Boolean = false
    private var listener : StateManager.Listener?
        set(value) {
            if (value != null && stateManager.listener == null) {
                value.onStateChange(stateManager.state)
            }
            stateManager.listener = value
        }
        get() = stateManager.listener

    init {
        audioEngine.listener = object : IAudioListener {
            override fun onMeasure(frequency: Double) {
                stateManager.updateMeasuredFrequency(frequency)
            }
        }
    }

    fun start(l: StateManager.Listener, context: Context) {
        stateManager.changeTuning(getTuning(context))
        listener = l
        if (!recording) {
            audioEngine.startRecording()
            recording = true
        }
    }

    fun stop() {
        if (recording) {
            audioEngine.stopRecording()
            recording = false
        }
        listener = null
    }

    fun reset() {
        stateManager.resetState()
    }

    fun selectString(ind: Int) {
        stateManager.selectString(ind)
    }

    private fun getTuning(context : Context): Tuning {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val tuningName = sharedPreferences.getString(TUNING_PREFERENCE_KEY, null)
        return Tuning.fromName(tuningName)
    }
}