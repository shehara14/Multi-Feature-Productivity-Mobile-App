package com.example.tasklytic

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasklytic.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null
    private var isTimerRunning = false
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var binding: ActivityTimerBinding // Declare ViewBinding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater) // Inflate the binding
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound) // Use alarm_sound.wav

        binding.startButton.setOnClickListener {
            if (!isTimerRunning) {
                val timeInSeconds = binding.timeInput.text.toString().toLongOrNull()
                if (timeInSeconds != null && timeInSeconds > 0) {
                    startTimer(timeInSeconds * 1000) // Convert seconds to milliseconds
                } else {
                    Toast.makeText(this, "Please enter a valid number of seconds", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.stopButton.setOnClickListener {
            stopTimer()
        }

        binding.resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer(millis: Long) {
        isTimerRunning = true
        timer = object : CountDownTimer(millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000) % 60
                val minutes = (millisUntilFinished / (1000 * 60)) % 60
                val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
                binding.timerText.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }

            override fun onFinish() {
                binding.timerText.text = "00:00:00"
                mediaPlayer.start()
                isTimerRunning = false
            }
        }.start()
    }

    private fun stopTimer() {
        timer?.cancel()
        isTimerRunning = false
    }

    private fun resetTimer() {
        stopTimer()
        binding.timerText.text = "00:00:00"
        binding.timeInput.text.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
