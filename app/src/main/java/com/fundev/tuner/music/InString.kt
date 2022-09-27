package com.fundev.tuner.music

/**
 * Represents a string of a music instrument.
 */
class InString(val position: Int, val note: Note, var state: State = State.UNKNOWN) {
    enum class State {
        IN_TUNE, OUT_OF_TUNE, UNKNOWN
    }

    val noteName : String
        get() = note.name.value
}