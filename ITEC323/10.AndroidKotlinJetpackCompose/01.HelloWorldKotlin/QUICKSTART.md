# Quick Start

## Prerequisites
- Android Studio installed
- Android 16 SDK available
- An emulator or Android device ready

## Run The App (Android Studio)
1. Open `10.AndroidKotlinJetpackCompose/01.HelloWorldKotlin` in Android Studio.
2. Let Gradle sync finish.
3. Start an emulator or connect a device.
4. Click `Run`.

## Build and Run from the Command Line (Gradle)

This is useful when you want to build or run the app without opening Android Studio.

### Prerequisites
- `ANDROID_HOME` environment variable set to your Android SDK directory
  - macOS/Linux default: `~/Library/Android/sdk`
  - Windows default: `%LOCALAPPDATA%\Android\Sdk`
- A running emulator or USB-connected device (for install/launch steps)

### Step 1 – Navigate to the project directory

All Gradle commands must be run from inside `01.HelloWorldKotlin/`, where `gradlew` lives:

```bash
cd 10.AndroidKotlinJetpackCompose/01.HelloWorldKotlin
```

### Step 2 – Build the debug APK

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

### Step 3 – Install the APK on a device or emulator

```bash
# macOS / Linux
./gradlew installDebug

# Windows
gradlew.bat installDebug
```

> The emulator must be running (or a device connected via USB) before this step.

### Step 4 – Launch the app

```bash
adb shell am start -n com.acu.helloworldkotlin/.MainActivity
```

### All-in-one: navigate, build, install, and launch

```bash
cd 10.AndroidKotlinJetpackCompose/01.HelloWorldKotlin && ./gradlew installDebug && adb shell am start -n com.acu.helloworldkotlin/.MainActivity
```

### Other useful Gradle commands

| Command | What it does |
|---|---|
| `./gradlew clean` | Delete build output |
| `./gradlew build` | Build debug + release APKs |
| `./gradlew test` | Run unit tests (no device needed) |
| `./gradlew connectedAndroidTest` | Run instrumented tests on a device |
| `./gradlew tasks` | List all available tasks |

## What To Check
- The screen shows `Hello Android 16!`
- The button says `Tap me`
- The counter increases when you press the button

## If Something Breaks
- `zsh: no such file or directory: ./gradlew` — you are in the wrong directory; `cd` into `01.HelloWorldKotlin/` first
- Re-sync Gradle (`./gradlew --refresh-dependencies`) if dependencies fail to resolve
- Confirm SDK 36 is installed in Android Studio → SDK Manager
- Check that the emulator is running before running `installDebug` or pressing `Run`
