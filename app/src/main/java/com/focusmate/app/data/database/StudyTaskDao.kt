package com.focusmate.app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.focusmate.app.data.models.StudyTask
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyTaskDao {
    @Insert
    suspend fun insertTask(task: StudyTask): Long

    @Update
    suspend fun updateTask(task: StudyTask)

    @Delete
    suspend fun deleteTask(task: StudyTask)

    @Query("SELECT * FROM study_tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<StudyTask>>

    @Query("SELECT * FROM study_tasks WHERE isCompleted = 0 ORDER BY createdAt DESC")
    fun getPendingTasks(): Flow<List<StudyTask>>

    @Query("SELECT * FROM study_tasks WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedTasks(): Flow<List<StudyTask>>

    @Query("SELECT COUNT(*) FROM study_tasks WHERE isCompleted = 1")
    fun getTotalCompletedTasksCount(): Flow<Int>
}
