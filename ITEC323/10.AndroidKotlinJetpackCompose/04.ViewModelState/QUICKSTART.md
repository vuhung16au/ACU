# Quick Start

## Prerequisites
- Android Studio installed
- Android 16 SDK available
- `adb` available from the Android SDK platform tools
- An emulator or Android device ready

## Build and Run from the Command Line (Gradle)

This is useful when you want to build or run the app without opening Android Studio.

### Step 1 - Navigate to the project directory

```bash
cd 10.AndroidKotlinJetpackCompose/04.ViewModelState
```

### Step 2 - Build the debug APK

```bash
# macOS / Linux
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

### Step 3 - Install the APK

```bash
# macOS / Linux
./gradlew installDebug

# Windows
gradlew.bat installDebug
```

### Step 4 - Launch the app

```bash
adb shell am start -n com.acu.viewmodelstate/.MainActivity
```

## What To Check
- The counter changes when you press the buttons
- The status text shows the last action
- The counter value stays the same after screen rotation

## If Something Breaks
- `zsh: no such file or directory: ./gradlew` - you are in the wrong folder
- Make sure the emulator is already running before `installDebug`
- `Error type 3` usually means the app is not installed yet, so run `./gradlew installDebug` first
- Confirm SDK 36 is installed
