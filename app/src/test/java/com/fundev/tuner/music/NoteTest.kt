package com.fundev.tuner.music

import org.junit.Test

import org.junit.Assert.*

class NoteTest {
    @Test
    fun checkA4Frequency() {
        val A4 = Note(Note.Name.A, 4)
        assertEquals(440.0, A4.getFrequency(), .001)
    }

    @Test
    fun checkC5Frequency() {
        val C5 = Note(Note.Name.C, 5)
        assertEquals(523.2, C5.getFrequency(), .1)
    }

    @Test
    fun checkF4Frequency() {
        val F4 = Note(Note.Name.F, 4)
        assertEquals(349.2, F4.getFrequency(), .1)
    }
}