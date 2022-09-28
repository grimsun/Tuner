package com.fundev.tuner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fundev.tuner.audio.RandomAudioEngine
import com.fundev.tuner.controller.Controller
import com.fundev.tuner.controller.State
import com.fundev.tuner.controller.StateManager
import com.fundev.tuner.music.InString
import com.fundev.tuner.settings.SettingsActivity
import com.fundev.tuner.ui.AnalogIndicator
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // This controller drives audio related logic.
    private val controller = Controller(RandomAudioEngine(), StateManager())
    // UI components.
    private val stringButtonList: MutableList<Button> = mutableListOf()
    private lateinit var textView: TextView
    private lateinit var resetButton: TextView
    private lateinit var settingsButton: Button
    private lateinit var stringButtonContainer: LinearLayout
    private lateinit var indicatorDrawable: AnalogIndicator

    private var lastState: State? = null

    private val listener : StateManager.Listener = object : StateManager.Listener {
        override fun onStateChange(state: State) {
            runOnUiThread {
                indicatorDrawable.indicatorPosition = random.nextFloat()
                indicatorDrawable.invalidateSelf()

//                if (lastState?.currentString?.noteName != state.currentString.noteName) {
                    textView.text = state.currentString.noteName
//                }

                if (lastState?.tuning != state.tuning) {
                    stringButtonContainer.removeAllViewsInLayout()
                    stringButtonList.clear()
                    for ((_, string) in state.strings.iterator().withIndex()) {
                        val stringButton = createStringButton(string, state)

//                        stringButton.setBackgroundColor(getStringButtonColor(string))
                        stringButton.setTextColor(getStringButtonTextColor(string, state))

                        stringButtonContainer.addView(stringButton)
                        stringButtonList.add(stringButton)
                    }
                }
                lastState = state
            }

        }
    }

    private val random = Random(0)
    private val permissionCode = 10001
    private var audioPermissionGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        supportActionBar?.hide()

        textView = findViewById(R.id.textView)
        stringButtonContainer = findViewById(R.id.stringButtonContainer)
        settingsButton = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            controller.reset()
        }
        indicatorDrawable = AnalogIndicator()

        val mainView = findViewById<ConstraintLayout>(R.id.mainView)
        mainView.background = indicatorDrawable

        setupPermissions()
    }

    private fun setupPermissions() {
        val audioPerm =  Manifest.permission.RECORD_AUDIO
        if (ContextCompat.checkSelfPermission(this,audioPerm) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(audioPerm), permissionCode)
        } else {
            audioPermissionGranted = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode &&
                grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            audioPermissionGranted = true
        } else {
            // TODO: Log an error.
        }
    }

    override fun onResume() {
        super.onResume()
        if (audioPermissionGranted) {
            controller.start(listener, this.applicationContext)
        }
    }

    override fun onPause() {
        super.onPause()
        controller.stop()
    }

    private fun createStringButton(string: InString, state: State): Button {
//        val stringButton = FilledTonalButton(onClick = { controller.selectString(string.position) }) { Text(string.noteName) }

        val stringButton = Button(this)
        stringButton.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0F
            )
        // stringButton.id = ???
        stringButton.text = string.noteName
//        stringButton.setBackgroundColor(getStringButtonColor(string))
        stringButton.setTextColor(getStringButtonTextColor(string, state))
        stringButton.setOnClickListener {
            controller.selectString(string.position)
        }

        return stringButton
    }

    private fun getStringButtonTextColor(string: InString, state: State): Int {
        if (string.position == state.currentString.position) {
            return Color.GRAY
        }

        return Color.BLACK
    }

    private fun getStringButtonColor(string: InString): Int {
        return when(string.state) {
            InString.State.IN_TUNE -> Color.GREEN
            InString.State.OUT_OF_TUNE -> Color.RED
            InString.State.UNKNOWN -> Color.YELLOW
        }
    }
}