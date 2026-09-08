package com.focusmate.app

import android.app.Application
import com.focusmate.app.data.database.AppDatabase

class FocusMateApplication : Application() {
    
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
    }
}
