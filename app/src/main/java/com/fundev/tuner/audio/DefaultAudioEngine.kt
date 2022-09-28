package com.fundev.tuner.audio

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import java.util.concurrent.Executors

class DefaultAudioEngine : IAudioEngine {

    private val samplingRate = 22050
    // this should fit 50ms of audio with 22kHz sampling in array of shorts
    private val bufferSizeBase = 1103
    private val audioSource: Int = MediaRecorder.AudioSource.MIC
    private val audioEncoding: Int = AudioFormat.ENCODING_PCM_16BIT
    private val channelConfig: Int = AudioFormat.CHANNEL_IN_MONO
    private val bufferSize: Int = Math.max(AudioRecord.getMinBufferSize(
            samplingRate,
            channelConfig,
            audioEncoding),
            bufferSizeBase)
    private val audioRecord = AudioRecord(
            audioSource,
            samplingRate,
            channelConfig,
            audioEncoding,
            bufferSize)

    override var callback: IAudioEngine.Callback? = null
    override var analyzeFrequency: Int = 10

    override fun start() {
        audioRecord.startRecording()
        val executor = Executors.newSingleThreadExecutor()
        val buffer = ShortArray(bufferSizeBase)
        executor.execute {
            var res = audioRecord.read(buffer, 0, bufferSizeBase)
            while (res > 0) {
                val freq = getFrequency(
                        buffer.slice(IntRange(0, res-1)).toShortArray())
                //listener?.onMeasure(freq)
                res = audioRecord.read(buffer, 0, bufferSizeBase)
            }
        }
    }

    override fun halt() {
        audioRecord.stop()
    }

    private fun getFrequency(rawData: ShortArray): Double {
        // TODO: implement
        return 0.0;
    }
}