package com.focusmate.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.focusmate.app.data.models.StudyTask
import com.focusmate.app.data.models.FocusSession
import com.focusmate.app.data.models.DailyStats

@Database(
    entities = [StudyTask::class, FocusSession::class, DailyStats::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studyTaskDao(): StudyTaskDao
    abstract fun focusSessionDao(): FocusSessionDao
    abstract fun dailyStatsDao(): DailyStatsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "focusmate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
