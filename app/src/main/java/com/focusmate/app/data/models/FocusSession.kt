package com.focusmate.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_sessions")
data class FocusSession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val durationMinutes: Int,
    val breakMinutes: Int = 0,
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long? = null,
    val isCompleted: Boolean = false
)
