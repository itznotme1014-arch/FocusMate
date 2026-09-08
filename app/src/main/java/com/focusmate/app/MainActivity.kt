package com.focusmate.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.focusmate.app.databinding.ActivityMainBinding
import com.focusmate.app.ui.fragments.HomeFragment
import com.focusmate.app.ui.fragments.TasksFragment
import com.focusmate.app.ui.fragments.ProgressFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannels()
        setupNavigation()
    }

    private fun setupNavigation() {
        // Setup bottom navigation with fragments
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, HomeFragment())
                        .commit()
                    true
                }
                R.id.tasks_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, TasksFragment())
                        .commit()
                    true
                }
                R.id.progress_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, ProgressFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Set default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HomeFragment())
                .commit()
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            val timerChannel = NotificationChannel(
                "timer_channel",
                "Timer Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for timer completion"
                enableVibration(true)
            }

            val breakChannel = NotificationChannel(
                "break_channel",
                "Break Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for break completion"
            }

            notificationManager.createNotificationChannel(timerChannel)
            notificationManager.createNotificationChannel(breakChannel)
        }
    }
}
