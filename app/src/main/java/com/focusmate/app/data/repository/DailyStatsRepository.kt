package com.focusmate.app.data.repository

import com.focusmate.app.data.database.DailyStatsDao
import com.focusmate.app.data.models.DailyStats
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DailyStatsRepository(private val statsDao: DailyStatsDao) {
    
    fun getTodayStats(): Flow<DailyStats?> = statsDao.getTodayStats()
    
    fun getLast30DaysStats(): Flow<List<DailyStats>> = statsDao.getLast30DaysStats()
    
    suspend fun insertStats(stats: DailyStats) = statsDao.insertDailyStats(stats)
    
    suspend fun updateStats(stats: DailyStats) = statsDao.updateDailyStats(stats)
    
    suspend fun getOrCreateTodayStats(): DailyStats {
        val today = LocalDate.now().toString()
        val existing = statsDao.getTodayStats(today).value
        return existing ?: DailyStats(date = today).also { insertStats(it) }
    }
}
