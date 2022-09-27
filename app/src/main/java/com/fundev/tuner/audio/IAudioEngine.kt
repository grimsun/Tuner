package com.fundev.tuner.audio

interface IAudioEngine {

    var listener: IAudioListener?

    fun startRecording()
    fun stopRecording()
}