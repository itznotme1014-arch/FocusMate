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
import com.focusmate.app.databinding.FragmentTasksBinding
import com.focusmate.app.ui.adapters.TaskAdapter
import com.focusmate.app.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FocusMateApplication.database
        val taskRepository = StudyTaskRepository(database.studyTaskDao())
        taskViewModel = ViewModelProvider(
            this,
            TaskViewModel.Factory(taskRepository)
        ).get(TaskViewModel::class.java)

        setupUI()
        observeData()
    }

    private fun setupUI() {
        taskAdapter = TaskAdapter(
            onTaskComplete = { task -> taskViewModel.completeTask(task.id) },
            onTaskDelete = { task -> taskViewModel.deleteTask(task) }
        )

        binding.tasksRecycler.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.addTaskButton.setOnClickListener {
            val title = binding.taskTitleInput.text.toString().trim()
            val description = binding.taskDescriptionInput.text.toString().trim()
            
            if (title.isNotEmpty()) {
                taskViewModel.addTask(title, description)
                binding.taskTitleInput.text.clear()
                binding.taskDescriptionInput.text.clear()
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.pendingTasks.collect { tasks ->
                taskAdapter.submitList(tasks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
