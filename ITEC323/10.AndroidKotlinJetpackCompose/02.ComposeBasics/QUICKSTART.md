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
cd 10.AndroidKotlinJetpackCompose/02.ComposeBasics
```

### Step 2 - Build the debug APK

```bash
# macOS / Linux
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

The APK is produced at:
```
app/build/outputs/apk/debug/app-debug.apk
```

### Step 3 - Install the APK

```bash
# macOS / Linux
./gradlew installDebug

# Windows
gradlew.bat installDebug
```

This step must succeed before you try to launch the activity with `adb`.

### Step 4 - Launch the app

```bash
adb shell am start -n com.acu.composebasics/.MainActivity
```

### Useful commands

| Command | What it does |
|---|---|
| `./gradlew test` | Run unit tests |
| `./gradlew clean` | Delete build output |
| `./gradlew tasks` | List available Gradle tasks |

## What To Check
- Type a name into the form
- Toggle the checkbox and switch
- Watch the preview list update immediately

## If Something Breaks
- `zsh: no such file or directory: ./gradlew` - you are in the wrong folder
- Make sure the emulator is already running before `installDebug`
- `Error type 3` usually means the app is not installed yet, so run `./gradlew installDebug` first
- Confirm SDK 36 is installed
