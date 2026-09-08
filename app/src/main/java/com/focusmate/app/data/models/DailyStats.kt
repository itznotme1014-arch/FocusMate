package com.focusmate.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "daily_stats")
data class DailyStats(
    @PrimaryKey
    val date: String = LocalDate.now().toString(),
    val totalFocusMinutes: Int = 0,
    val completedTasks: Int = 0,
    val completedSessions: Int = 0
)
