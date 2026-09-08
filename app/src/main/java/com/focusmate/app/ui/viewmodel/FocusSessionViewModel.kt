package com.focusmate.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.focusmate.app.data.models.FocusSession
import com.focusmate.app.data.repository.FocusSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FocusSessionViewModel(private val repository: FocusSessionRepository) : ViewModel() {

    private val _totalCompletedSessions = MutableStateFlow(0)
    val totalCompletedSessions: StateFlow<Int> = _totalCompletedSessions

    private val _todayCompletedSessions = MutableStateFlow(0)
    val todayCompletedSessions: StateFlow<Int> = _todayCompletedSessions

    private val _todayFocusMinutes = MutableStateFlow(0)
    val todayFocusMinutes: StateFlow<Int> = _todayFocusMinutes

    private val _allSessions = MutableStateFlow<List<FocusSession>>(emptyList())
    val allSessions: StateFlow<List<FocusSession>> = _allSessions

    init {
        loadSessionData()
    }

    private fun loadSessionData() {
        viewModelScope.launch {
            repository.getTotalCompletedSessions().collect { count ->
                _totalCompletedSessions.value = count
            }
        }
        viewModelScope.launch {
            repository.getTodayCompletedSessions().collect { count ->
                _todayCompletedSessions.value = count
            }
        }
        viewModelScope.launch {
            repository.getTodayTotalFocusMinutes().collect { minutes ->
                _todayFocusMinutes.value = minutes
            }
        }
        viewModelScope.launch {
            repository.getAllSessions().collect { sessions ->
                _allSessions.value = sessions
            }
        }
    }

    fun addCompletedSession(durationMinutes: Int, breakMinutes: Int = 0) {
        viewModelScope.launch {
            val session = FocusSession(
                durationMinutes = durationMinutes,
                breakMinutes = breakMinutes,
                isCompleted = true,
                endTime = System.currentTimeMillis()
            )
            repository.insertSession(session)
        }
    }

    class Factory(private val repository: FocusSessionRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return FocusSessionViewModel(repository) as T
        }
    }
}
