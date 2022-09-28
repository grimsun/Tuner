package com.fundev.tuner.audio

// Defines interface to process and analyze audio stream.
interface IAudioEngine {
    // Callback is used to deliver analysis results of the audio stream.
    interface Callback {
        // Provides frequency of audio data.
        fun onAnalyze(frequency: Double)
    }

    // If caller code does not set callback, then audio engine is useless.
    var callback: Callback?
    // Defines how often callback should be executed (in Hz).
    var analyzeFrequency: Int

    // Start to listen audio stream and provide analysis results in the callback.
    fun start()
    // Stop listening audio stream. No-op if already stopped.
    fun halt()
}