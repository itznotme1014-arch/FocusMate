package com.focusmate.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.focusmate.app.data.repository.DailyStatsRepository
import com.focusmate.app.data.models.DailyStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgressViewModel(private val repository: DailyStatsRepository) : ViewModel() {

    private val _todayStats = MutableStateFlow<DailyStats?>(null)
    val todayStats: StateFlow<DailyStats?> = _todayStats

    private val _last30DaysStats = MutableStateFlow<List<DailyStats>>(emptyList())
    val last30DaysStats: StateFlow<List<DailyStats>> = _last30DaysStats

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            repository.getTodayStats().collect { stats ->
                _todayStats.value = stats
            }
        }
        viewModelScope.launch {
            repository.getLast30DaysStats().collect { stats ->
                _last30DaysStats.value = stats
            }
        }
    }

    class Factory(private val repository: DailyStatsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProgressViewModel(repository) as T
        }
    }
}
