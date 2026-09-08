package com.focusmate.app.ui.fragments

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.focusmate.app.FocusMateApplication
import com.focusmate.app.R
import com.focusmate.app.data.repository.FocusSessionRepository
import com.focusmate.app.databinding.FragmentFocusTimerBinding
import com.focusmate.app.ui.viewmodel.FocusSessionViewModel

class FocusTimerFragment : Fragment() {

    private var _binding: FragmentFocusTimerBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionViewModel: FocusSessionViewModel
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 0
    private var isRunning = false
    private var focusDurationMinutes = 25
    private var breakDurationMinutes = 5
    private var isBreakTime = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFocusTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FocusMateApplication.database
        val sessionRepository = FocusSessionRepository(database.focusSessionDao())
        sessionViewModel = ViewModelProvider(
            this,
            FocusSessionViewModel.Factory(sessionRepository)
        ).get(FocusSessionViewModel::class.java)

        setupUI()
    }

    private fun setupUI() {
        timeLeftInMillis = (focusDurationMinutes * 60 * 1000).toLong()
        updateTimerDisplay()

        binding.startButton.setOnClickListener { startTimer() }
        binding.pauseButton.setOnClickListener { pauseTimer() }
        binding.resumeButton.setOnClickListener { resumeTimer() }
        binding.resetButton.setOnClickListener { resetTimer() }

        binding.decrementButton.setOnClickListener {
            if (!isRunning && focusDurationMinutes > 1) {
                focusDurationMinutes--
                timeLeftInMillis = (focusDurationMinutes * 60 * 1000).toLong()
                updateTimerDisplay()
            }
        }

        binding.incrementButton.setOnClickListener {
            if (!isRunning && focusDurationMinutes < 60) {
                focusDurationMinutes++
                timeLeftInMillis = (focusDurationMinutes * 60 * 1000).toLong()
                updateTimerDisplay()
            }
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun startTimer() {
        if (!isRunning) {
            isRunning = true
            binding.startButton.isEnabled = false
            countDownTimer = object : CountDownTimer(timeLeftInMillis, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMillis = millisUntilFinished
                    updateTimerDisplay()
                }

                override fun onFinish() {
                    onTimerFinish()
                }
            }.start()
        }
    }

    private fun pauseTimer() {
        if (isRunning) {
            isRunning = false
            countDownTimer?.cancel()
            binding.startButton.isEnabled = true
        }
    }

    private fun resumeTimer() {
        startTimer()
    }

    private fun resetTimer() {
        pauseTimer()
        timeLeftInMillis = (focusDurationMinutes * 60 * 1000).toLong()
        isBreakTime = false
        updateTimerDisplay()
        binding.timerStatus.text = "Focus Time"
        binding.startButton.isEnabled = true
    }

    private fun onTimerFinish() {
        isRunning = false
        binding.startButton.isEnabled = true

        if (!isBreakTime) {
            // Focus session completed
            sessionViewModel.addCompletedSession(focusDurationMinutes, breakDurationMinutes)
            showNotification(
                "Focus session complete!",
                "Great work! Time for a break.",
                "timer_channel"
            )
            Toast.makeText(requireContext(), "Focus session complete!", Toast.LENGTH_SHORT).show()

            // Start break
            isBreakTime = true
            timeLeftInMillis = (breakDurationMinutes * 60 * 1000).toLong()
            binding.timerStatus.text = "Break Time"
            updateTimerDisplay()
            startTimer()
        } else {
            // Break completed
            showNotification(
                "Break finished!",
                "Ready for another focus session?",
                "break_channel"
            )
            Toast.makeText(requireContext(), "Break finished!", Toast.LENGTH_SHORT).show()
            resetTimer()
        }
    }

    private fun updateTimerDisplay() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        binding.timerText.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun showNotification(title: String, message: String, channelId: String) {
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(requireContext(), channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .build()

        notificationManager.notify((System.currentTimeMillis() / 1000).toInt(), notification)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        _binding = null
    }
}
