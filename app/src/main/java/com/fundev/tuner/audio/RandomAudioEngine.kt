package com.fundev.tuner.audio

import android.os.Handler
import kotlin.random.Random

class RandomAudioEngine : IAudioEngine {
    override var callback: IAudioEngine.Callback? = null
    override var analyzeFrequency: Int = 10

    override fun start() {
        val handler = Handler()
        val random = Random(0)
        handler.postDelayed(object : Runnable {
            override fun run() {
                callback?.onAnalyze(random.nextDouble())
                handler.postDelayed(this, 500)
            }
        }, 500)
    }

    override fun halt() {
        // No op.
    }
}