package com.focusmate.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.focusmate.app.FocusMateApplication
import com.focusmate.app.data.repository.StudyTaskRepository
import com.focusmate.app.data.repository.FocusSessionRepository
import com.focusmate.app.databinding.FragmentHomeBinding
import com.focusmate.app.ui.adapters.TaskAdapter
import com.focusmate.app.ui.viewmodel.TaskViewModel
import com.focusmate.app.ui.viewmodel.FocusSessionViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var sessionViewModel: FocusSessionViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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

        setupUI()
        observeData()
    }

    private fun setupUI() {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
        binding.todayDate.text = today.format(formatter)

        taskAdapter = TaskAdapter(
            onTaskComplete = { task -> taskViewModel.completeTask(task.id) },
            onTaskDelete = { task -> taskViewModel.deleteTask(task) }
        )

        binding.pendingTasksRecycler.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.startFocusButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(android.R.id.content, FocusTimerFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.pendingTasks.collect { tasks ->
                taskAdapter.submitList(tasks)
                binding.pendingTasksCount.text = "${tasks.size} pending"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.totalCompletedCount.collect { count ->
                binding.completedTasksCount.text = "$count completed"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sessionViewModel.todayCompletedSessions.collect { count ->
                binding.sessionsCount.text = "$count sessions"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sessionViewModel.todayFocusMinutes.collect { minutes ->
                binding.focusTimeCount.text = "$minutes min"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
