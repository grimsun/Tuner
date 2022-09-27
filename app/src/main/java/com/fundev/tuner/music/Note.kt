package com.fundev.tuner.music

class Note(val name: Name, private val octave: Int) {
    enum class Name(val value: String) {
        C("C"),
        Cs("C#"),
        D("D"),
        Ds("D#"),
        E("E"),
        F("F"),
        Fs("F#"),
        G("G"),
        Gs("G#"),
        A("A"),
        As("A#"),
        B("B"),
    }

    companion object {
        const val CONCERT_PITCH = 440.0 // frequency of A4
        const val ACCEPTABLE_DELTA = 100 // in cents
        val A4 = Note(Name.A, 4)
    }

    fun validFrequency(frequency: Double): Boolean {
        val thisFrequency = getFrequency()
        val posNext = Name.values().indexOf(name) + 12 * octave + 1
        val nextFrequency = getFrequency(posNext)
        val deltaFrequency = Math.abs(nextFrequency - thisFrequency) * ACCEPTABLE_DELTA / 100.0

        return Math.abs(frequency - thisFrequency) < deltaFrequency
    }

    // frequency in hertz
    fun getFrequency(): Double {
        val posThis = Name.values().indexOf(name) + 12 * octave
        return getFrequency(posThis)
    }

    private fun getFrequency(pos: Int): Double {
        val posA4 = Name.values().indexOf(A4.name) + 12 * A4.octave
        val dist = pos - posA4

        return Math.pow(2.0, dist / 12.0) * CONCERT_PITCH;
    }
}