package com.focusmate.app.data.repository

import com.focusmate.app.data.database.FocusSessionDao
import com.focusmate.app.data.models.FocusSession
import kotlinx.coroutines.flow.Flow

class FocusSessionRepository(private val sessionDao: FocusSessionDao) {
    
    fun getAllSessions(): Flow<List<FocusSession>> = sessionDao.getAllSessions()
    
    fun getTotalCompletedSessions(): Flow<Int> = sessionDao.getTotalCompletedSessions()
    
    fun getTodayCompletedSessions(): Flow<Int> = sessionDao.getTodayCompletedSessions()
    
    fun getTodayTotalFocusMinutes(): Flow<Int> = sessionDao.getTodayTotalFocusMinutes()
    
    suspend fun insertSession(session: FocusSession) = sessionDao.insertSession(session)
    
    suspend fun updateSession(session: FocusSession) = sessionDao.updateSession(session)
}
