package com.fundev.tuner.music

class Tuning(val notes: List<Note>, val name: String, val instrument: Instrument) {
    companion object {
        fun fromName(name: String?): Tuning {
            if (name in ALL_TUNINGS) {
                return ALL_TUNINGS[name] as Tuning
            }
            // better to throw exception instead
            return STANDARD
        }

        private val STANDARD = Tuning(listOf(
            Note(Note.Name.E, 2),
            Note(Note.Name.A, 2),
            Note(Note.Name.D, 3),
            Note(Note.Name.G, 3),
            Note(Note.Name.B, 3),
            Note(Note.Name.E, 4)),
            "Standard",
            Instrument.GUITAR_6)

        private val D_STANDARD = Tuning(listOf(
            Note(Note.Name.D, 2),
            Note(Note.Name.A, 2),
            Note(Note.Name.D, 3),
            Note(Note.Name.Fs, 3),
            Note(Note.Name.A, 3),
            Note(Note.Name.D, 4)),
            "D Standard",
            Instrument.GUITAR_6)

        private val DROP_C = Tuning(listOf(
            Note(Note.Name.C, 2),
            Note(Note.Name.G, 2),
            Note(Note.Name.C, 3),
            Note(Note.Name.F, 3),
            Note(Note.Name.A, 3),
            Note(Note.Name.D, 4)),
            "Drop C",
            Instrument.GUITAR_6)

        val ALL_TUNINGS = mapOf(
            STANDARD.name to STANDARD,
            D_STANDARD.name to D_STANDARD,
            DROP_C.name to DROP_C)
    }

    fun findClosetNote(frequency: Double): Int {
        var res = 0
        var min = Double.MAX_VALUE
        for ((ind, note) in notes.withIndex()) {
            if(Math.abs(note.getFrequency() - frequency) < min) {
                min = Math.abs(note.getFrequency() - frequency)
                res = ind
            }
        }
        return res
    }

    fun validFrequency(frequency: Double): Boolean {
        for (note in notes) {
            if (note.validFrequency(frequency)) {
                return true
            }
        }

        return false
    }
}