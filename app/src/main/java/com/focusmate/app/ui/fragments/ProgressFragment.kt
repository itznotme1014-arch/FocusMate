package com.focusmate.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.focusmate.app.FocusMateApplication
import com.focusmate.app.data.repository.FocusSessionRepository
import com.focusmate.app.data.repository.StudyTaskRepository
import com.focusmate.app.databinding.FragmentProgressBinding
import com.focusmate.app.ui.viewmodel.FocusSessionViewModel
import com.focusmate.app.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var sessionViewModel: FocusSessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FocusMateApplication.database
        val taskRepository = StudyTaskRepository(database.studyTaskDao())
        val sessionRepository = FocusSessionRepository(database.focusSessionDao())

        taskViewModel = ViewModelProvider(
            this,
            TaskViewModel.Factory(taskRepository)
        ).get(TaskViewModel::class.java)

        sessionViewModel = ViewModelProvider(
            this,
            FocusSessionViewModel.Factory(sessionRepository)
        ).get(FocusSessionViewModel::class.java)

        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.totalCompletedCount.collect { count ->
                binding.totalTasksCompleted.text = "$count"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sessionViewModel.totalCompletedSessions.collect { count ->
                binding.totalSessions.text = "$count"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sessionViewModel.todayFocusMinutes.collect { minutes ->
                binding.todayFocusTime.text = "$minutes minutes"
                updateProgressBar(minutes)
            }
        }
    }

    private fun updateProgressBar(minutes: Int) {
        val progress = (minutes / 120f * 100).toInt().coerceIn(0, 100)
        binding.focusProgressBar.progress = progress
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
