package com.fundev.tuner.controller

import android.util.Log
import com.fundev.tuner.music.InString
import com.fundev.tuner.music.Tuning

class StateManager {

    interface Listener {
        fun onStateChange(state: State)
    }

    lateinit var state: State
    var listener: Listener? = null

    fun updateMeasuredFrequency(frequency: Double) {
        val pos = state.tuning.findClosetNote(frequency)
        Log.d("Controller", "Closest note: " + state.tuning.notes[pos].name)

        when {
            stringGotInTune(frequency) -> {
                state.currentString.state = InString.State.IN_TUNE
//                listener?.onStateChange(state)
            }
            stringGotOutOfTune(frequency) -> {
                state.currentString.state = InString.State.OUT_OF_TUNE
//                listener?.onStateChange(state)
            }
//            probablySwitchedString(frequency) -> {
//                selectString(state.tuning.findClosetNote(frequency))
//                listener?.onStateChange(state)
//            }
        }

        listener?.onStateChange(state)

    }

    private fun stringGotOutOfTune(frequency: Double): Boolean {
        return state.currentString.state == InString.State.IN_TUNE &&
                !state.currentString.note.validFrequency(frequency) &&
                state.tuning.validFrequency(frequency)
    }

    private fun stringGotInTune(frequency: Double): Boolean {
        return state.currentString.note.validFrequency(frequency)
    }

    fun selectString(ind: Int) {
        //require(ind < State.STRING_NUMBER)
        state.currentString = state.strings[ind]
        //state.currentString.state = InString.State.TUNING_IN_PROGRESS
        listener?.onStateChange(state)
    }

    fun changeTuning(t: Tuning) {
        if (!::state.isInitialized || state.tuning != t) {
            state = State(t)
            listener?.onStateChange(state)
        }
    }

    fun resetState() {
        state = State(state.tuning)
        listener?.onStateChange(state)
    }
}