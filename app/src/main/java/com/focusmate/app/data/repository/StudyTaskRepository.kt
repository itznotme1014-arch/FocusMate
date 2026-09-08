package com.focusmate.app.data.repository

import com.focusmate.app.data.database.StudyTaskDao
import com.focusmate.app.data.models.StudyTask
import kotlinx.coroutines.flow.Flow

class StudyTaskRepository(private val taskDao: StudyTaskDao) {
    
    fun getAllTasks(): Flow<List<StudyTask>> = taskDao.getAllTasks()
    
    fun getPendingTasks(): Flow<List<StudyTask>> = taskDao.getPendingTasks()
    
    fun getCompletedTasks(): Flow<List<StudyTask>> = taskDao.getCompletedTasks()
    
    fun getTotalCompletedCount(): Flow<Int> = taskDao.getTotalCompletedTasksCount()
    
    suspend fun insertTask(task: StudyTask) = taskDao.insertTask(task)
    
    suspend fun updateTask(task: StudyTask) = taskDao.updateTask(task)
    
    suspend fun deleteTask(task: StudyTask) = taskDao.deleteTask(task)
    
    suspend fun completeTask(taskId: Int, tasks: List<StudyTask>) {
        val task = tasks.find { it.id == taskId }
        if (task != null) {
            val completedTask = task.copy(
                isCompleted = true,
                completedAt = System.currentTimeMillis()
            )
            updateTask(completedTask)
        }
    }
}
