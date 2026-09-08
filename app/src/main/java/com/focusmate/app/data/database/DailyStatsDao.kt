package com.focusmate.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.focusmate.app.data.models.DailyStats
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DailyStatsDao {
    @Insert
    suspend fun insertDailyStats(stats: DailyStats)

    @Update
    suspend fun updateDailyStats(stats: DailyStats)

    @Query("SELECT * FROM daily_stats WHERE date = :date")
    fun getTodayStats(date: String = LocalDate.now().toString()): Flow<DailyStats?>

    @Query("SELECT * FROM daily_stats ORDER BY date DESC LIMIT 30")
    fun getLast30DaysStats(): Flow<List<DailyStats>>
}
