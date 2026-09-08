package com.focusmate.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.focusmate.app.data.models.FocusSession
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusSessionDao {
    @Insert
    suspend fun insertSession(session: FocusSession): Long

    @Update
    suspend fun updateSession(session: FocusSession)

    @Query("SELECT * FROM focus_sessions ORDER BY startTime DESC LIMIT 50")
    fun getAllSessions(): Flow<List<FocusSession>>

    @Query("SELECT COUNT(*) FROM focus_sessions WHERE isCompleted = 1")
    fun getTotalCompletedSessions(): Flow<Int>

    @Query("SELECT COUNT(*) FROM focus_sessions WHERE isCompleted = 1 AND date(startTime / 1000, 'unixepoch') = date('now')")
    fun getTodayCompletedSessions(): Flow<Int>

    @Query("SELECT COALESCE(SUM(durationMinutes), 0) FROM focus_sessions WHERE isCompleted = 1 AND date(startTime / 1000, 'unixepoch') = date('now')")
    fun getTodayTotalFocusMinutes(): Flow<Int>
}
