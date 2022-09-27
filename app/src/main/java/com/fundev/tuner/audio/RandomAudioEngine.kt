package com.fundev.tuner.audio

import android.os.Handler
import kotlin.random.Random

class RandomAudioEngine : IAudioEngine {
    override var listener: IAudioListener? = null

    override fun startRecording() {
        val handler = Handler()
        val random = Random(0)
        handler.postDelayed(object : Runnable {
            override fun run() {
                listener?.onMeasure(random.nextDouble())
                handler.postDelayed(this, 500)
            }
        }, 500)
    }

    override fun stopRecording() {
        // No op.
    }
}