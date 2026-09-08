# FocusMate - Study Productivity App

## Overview
FocusMate is a modern, beginner-friendly Android app designed to help students stay focused and organize their study time. With integrated focus timer sessions, task management, and progress tracking, FocusMate makes studying productive and rewarding.

## Features

### 1. Home Dashboard
- Daily greeting with current date
- Quick overview of today's study progress
- Display of completed tasks and focus sessions
- Prominent "Start Focus" button for quick access
- Clean, uncluttered interface

### 2. Focus Timer
- Default 25-minute focus duration (customizable)
- Start, pause, resume, and reset controls
- Large, easy-to-read countdown display
- Completion notifications
- Optional 5-minute break timer with notifications
- Automatic session tracking

### 3. Tasks Management
- Add study tasks with descriptions
- Mark tasks as completed
- Delete tasks
- View pending and completed tasks
- Local storage ensures tasks persist after closing the app

### 4. Progress Tracking
- View total focus sessions completed
- Track total completed tasks
- See today's study time
- Daily progress indicator
- Local statistics storage

### 5. Notifications
- Timer completion notifications
- Break completion notifications
- Automatic permission handling for supported Android versions

## Design
- Modern Material Design 3 interface
- Clean, minimal, and student-friendly UI
- Rounded cards and buttons
- Proper spacing and readable typography
- Full light and dark mode support
- Responsive layout for all phone sizes

## Technical Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Persistence Library
- **UI Framework**: AndroidX & Material Components
- **Concurrency**: Kotlin Coroutines & Flow
- **Build System**: Gradle
- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Gradle 8.1.0 or later
- JDK 11 or later
- Android SDK 26 or later

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/itznotme1014-arch/FocusMate.git
   cd FocusMate
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click "File" → "Open"
   - Select the FocusMate project folder
   - Wait for Gradle sync to complete

3. **Build the Project**
   ```bash
   ./gradlew build
   ```

4. **Run on Emulator or Device**
   - Connect an Android device via USB or start an emulator
   - Click "Run" → "Run 'app'" in Android Studio
   - Select your device
   - Click OK

### Building an APK

1. **Debug APK**
   ```bash
   ./gradlew assembleDebug
   ```
   APK location: `app/build/outputs/apk/debug/app-debug.apk`

2. **Release APK**
   ```bash
   ./gradlew assembleRelease
   ```
   APK location: `app/build/outputs/apk/release/app-release.apk`

## Usage

### Starting a Focus Session
1. Tap the "Start Focus Session" button on the home screen
2. Adjust the timer duration using +/- buttons (default: 25 minutes)
3. Tap "Start" to begin
4. Use "Pause", "Resume", or "Reset" as needed
5. When complete, you'll receive a notification

### Managing Tasks
1. Go to the "Tasks" tab
2. Enter task title and optional description
3. Tap "Add Task"
4. Check the box to mark tasks as complete
5. Tap delete icon to remove tasks

### Tracking Progress
1. Visit the "Progress" tab
2. View total sessions and completed tasks
3. See today's focus time
4. Check daily progress with the progress bar

## Project Structure

```
FocusMate/
├── app/
│   ├── src/main/
│   │   ├── java/com/focusmate/app/
│   │   │   ├── MainActivity.kt
│   │   │   ├── FocusMateApplication.kt
│   │   │   ├── data/
│   │   │   │   ├── database/
│   │   │   │   ├── models/
│   │   │   │   └── repository/
│   │   │   ├── ui/
│   │   │   │   ├── fragments/
│   │   │   │   ├── viewmodel/
│   │   │   │   └── adapters/
│   │   │   └── notifications/
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── menu/
│   │   │   ├── values/
│   │   │   └── values-night/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Permissions Required

- `android.permission.POST_NOTIFICATIONS` - For timer and break notifications

## Data Storage

FocusMate uses local device storage only:
- **Room Database**: Stores tasks, sessions, and statistics
- **No server required**: All data stays on your device
- **Backup support**: Enabled for backup and restore

## Troubleshooting

### App crashes on startup
- Ensure you're running Android 8.0 (API 26) or higher
- Clear app cache: Settings → Apps → FocusMate → Storage → Clear Cache
- Reinstall the app

### Notifications not appearing
- Check notification settings: Settings → Apps → FocusMate → Notifications → Enabled
- Ensure battery optimization is disabled for FocusMate

### Tasks/Sessions not saving
- Ensure the app has storage permission
- Check available device storage
- Restart the app and device

## Version

**v1.0.0** - Initial Release
- Focus timer with customizable duration
- Task management system
- Progress tracking dashboard
- Light and dark theme support
- Material Design 3 UI
- Local persistence with Room Database

---

**FocusMate** - Stay Focused, Stay Productive! 🎯
