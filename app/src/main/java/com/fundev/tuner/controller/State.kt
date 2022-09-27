package com.fundev.tuner.controller

import com.fundev.tuner.music.InString
import com.fundev.tuner.music.Tuning

class State(val tuning: Tuning) {
    val strings: MutableList<InString> = MutableList(tuning.notes.size) {
        ind -> InString(ind, tuning.notes[ind], InString.State.UNKNOWN)
    }
    // Automatically start tuning with a first string.
    var currentString: InString = strings[0]
}