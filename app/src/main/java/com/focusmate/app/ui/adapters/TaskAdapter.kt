package com.focusmate.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.focusmate.app.data.models.StudyTask
import com.focusmate.app.databinding.ItemTaskBinding

class TaskAdapter(
    private val onTaskComplete: (StudyTask) -> Unit,
    private val onTaskDelete: (StudyTask) -> Unit
) : ListAdapter<StudyTask, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: StudyTask) {
            binding.taskTitle.text = task.title
            binding.taskDescription.text = task.description.takeIf { it.isNotEmpty() } ?: "No description"
            binding.taskCheckbox.isChecked = task.isCompleted

            binding.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onTaskComplete(task)
                }
            }

            binding.deleteButton.setOnClickListener {
                onTaskDelete(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<StudyTask>() {
        override fun areItemsTheSame(oldItem: StudyTask, newItem: StudyTask) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: StudyTask, newItem: StudyTask) =
            oldItem == newItem
    }
}
