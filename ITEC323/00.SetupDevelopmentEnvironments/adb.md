# Using ADB to Install and Run APK Files

## Overview

This guide shows you how to use **Android Debug Bridge (adb)** to install and run APK files on Android devices or emulators without using Android Studio. This is useful for testing apps, sharing builds with others, or when you prefer command-line tools.

## What is ADB?

**ADB (Android Debug Bridge)** is a command-line tool that lets you communicate with Android devices. You can use it to:

- Install and uninstall apps
- Copy files to/from devices
- View device logs
- Launch apps
- Debug applications
- Execute shell commands on Android devices

ADB is part of the Android SDK Platform Tools and works on Windows, macOS, and Linux.

## Prerequisites

Before you begin, ensure you have:

1. **ADB installed** - Comes with Android SDK (see [SetupKotlin.md](SetupKotlin.md) for installation)
2. **Android device** with USB debugging enabled OR **Android emulator** running
3. **APK file** you want to install (typically found in `app/build/outputs/apk/`)

## Installation Paths

APK files are typically generated in these locations after building your Android project:

```
Project Root/
└── app/
    └── build/
        └── outputs/
            └── apk/
                ├── debug/
                │   └── app-debug.apk
                └── release/
                    └── app-release-unsigned.apk
```

## Quick Start

```bash
# 1. Check if device is connected
adb devices

# 2. Install the APK
adb install app/build/outputs/apk/debug/app-debug.apk

# 3. The app appears in your device's app drawer - open it manually
```

## Step-by-Step Guide

### Step 1: Verify ADB Installation

Check if adb is available:

**macOS/Linux:**
```bash
adb version
```

**Windows (PowerShell):**
```powershell
adb version
```

**Expected output:**
```
Android Debug Bridge version 1.0.41
Version 34.0.5-10900879
```

**If "command not found":**

Make sure Android SDK platform-tools is in your PATH (see Troubleshooting section).

### Step 2: Enable USB Debugging on Your Device

#### On Your Android Device:

1. Open **Settings**
2. Go to **About Phone**
3. Tap **Build Number** 7 times (this enables Developer Options)
4. Go back to **Settings** → **Developer Options**
5. Enable **USB Debugging**
6. Connect your device via USB cable
7. Accept the **USB debugging prompt** on your device (check "Always allow from this computer")

### Step 3: Verify Device Connection

Connect your Android device via USB, then run:

```bash
adb devices
```

**Expected output:**
```
List of devices attached
1234567890ABCDEF    device
```

**Status meanings:**
- `device` - Device is connected and ready
- `unauthorized` - You need to accept USB debugging prompt on device
- `offline` - Device is not responding
- Empty list - No devices detected

**If multiple devices are connected:**
```bash
# Target a specific device
adb -s 1234567890ABCDEF install app.apk
```

### Step 4: Install the APK

#### Basic Installation

**macOS/Linux:**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Windows (PowerShell):**
```powershell
adb install app\build\outputs\apk\debug\app-debug.apk
```

#### Installation Options

```bash
# Reinstall existing app (keeps data)
adb install -r app-debug.apk

# Reinstall and keep data on downgrade
adb install -r -d app-debug.apk

# Grant all runtime permissions during install
adb install -g app-debug.apk

# Install to SD card (if supported)
adb install -s app-debug.apk
```

**Success message:**
```
Performing Streamed Install
Success
```

### Step 5: Launch the App

After installation, you can launch the app in two ways:

#### Method 1: Launch from App Drawer (Easiest)

Simply open the app from your device's app drawer - it will appear with the app name and icon.

#### Method 2: Launch via ADB Command

To launch programmatically:

```bash
# Basic syntax
adb shell am start -n <package.name>/.MainActivity

# Example
adb shell am start -n com.example.myapp/.MainActivity
```

**Finding Your Package Name:**

If you don't know the package name:

```bash
# List all installed packages
adb shell pm list packages

# Search for your app (example: search for "greeting")
adb shell pm list packages | grep greeting

# Get full app info
adb shell dumpsys package <package.name> | grep Activity
```

**Alternative - Extract from APK:**

**macOS/Linux:**
```bash
# Using aapt (Android Asset Packaging Tool)
aapt dump badging app-debug.apk | grep package:\ name

# Output: package: name='com.example.myapp' versionCode='1'
```

**Windows:**
```powershell
aapt dump badging app-debug.apk | Select-String "package: name"
```

## Using Android Emulator (Without Android Studio UI)

If you don't have a physical device, you can use the Android emulator from the command line.

### Step 1: List Available Emulators

```bash
emulator -list-avds
```

**Example output:**
```
Pixel_5_API_34
Pixel_Tablet_API_33
```

### Step 2: Start an Emulator

**macOS/Linux:**
```bash
# Start in background
emulator -avd Pixel_5_API_34 &

# Start with console output
emulator -avd Pixel_5_API_34
```

**Windows (PowerShell):**
```powershell
# Start emulator
Start-Process emulator -ArgumentList "-avd", "Pixel_5_API_34"
```

**Common emulator options:**
```bash
# Start with specific resolution
emulator -avd Pixel_5_API_34 -skin 1080x1920

# Start with more RAM
emulator -avd Pixel_5_API_34 -memory 2048

# Start without audio
emulator -avd Pixel_5_API_34 -no-audio
```

### Step 3: Wait for Emulator to Boot

```bash
# Check boot status
adb shell getprop sys.boot_completed

# Returns: 1 (when ready)
```

### Step 4: Install APK to Emulator

Once the emulator is running:

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Useful ADB Commands

### App Management

```bash
# Uninstall an app
adb uninstall com.example.myapp

# Uninstall but keep data
adb uninstall -k com.example.myapp

# Clear app data (without uninstalling)
adb shell pm clear com.example.myapp

# Get app info
adb shell dumpsys package com.example.myapp
```

### File Operations

```bash
# Push file to device
adb push local-file.txt /sdcard/Download/

# Pull file from device
adb pull /sdcard/Download/file.txt ./

# List files on device
adb shell ls /sdcard/Download/
```

### Viewing Logs

```bash
# View all logs
adb logcat

# View logs for your app only
adb logcat | grep "MyAppTag"

# Clear logs
adb logcat -c

# Save logs to file
adb logcat > app-logs.txt
```

### Device Information

```bash
# Get device model
adb shell getprop ro.product.model

# Get Android version
adb shell getprop ro.build.version.release

# Get device serial number
adb get-serialno

# Get device state
adb get-state
```

### Screenshots and Screen Recording

```bash
# Take screenshot
adb shell screencap /sdcard/screenshot.png
adb pull /sdcard/screenshot.png ./

# Record screen (Press Ctrl+C to stop)
adb shell screenrecord /sdcard/demo.mp4
adb pull /sdcard/demo.mp4 ./
```

## Platform-Specific Notes

### Windows

**PowerShell vs Command Prompt:**
- Both work the same for adb commands
- Use PowerShell for better terminal experience
- Path separators: use backslash `\` for local paths

**Running as Administrator:**
- May be required for first-time USB driver installation
- Right-click PowerShell → "Run as Administrator"

### macOS

**Apple Silicon (M1/M2/M3):**
- Use emulators with ARM images when possible
- Intel-based emulators require Rosetta 2: `softwareupdate --install-rosetta`

**Finder Alternative:**
- Instead of `adb push`, you can drag files to Android File Transfer app

### Linux

**USB Permissions:**
- May need to configure udev rules for USB devices
- See [Android Developer Docs - USB Setup](https://developer.android.com/studio/run/device#setting-up)

**Example udev rule** (`/etc/udev/rules.d/51-android.rules`):
```
SUBSYSTEM=="usb", ATTR{idVendor}=="18d1", MODE="0666", GROUP="plugdev"
```

Then reload:
```bash
sudo udevadm control --reload-rules
```

## Understanding APK Types

### Debug APK
- **Location:** `app/build/outputs/apk/debug/app-debug.apk`
- **Signed with:** Debug key (automatically)
- **Use case:** Development and testing
- **Security:** Not suitable for distribution

### Release APK (Unsigned)
- **Location:** `app/build/outputs/apk/release/app-release-unsigned.apk`
- **Signed with:** None
- **Use case:** Must be signed before distribution
- **Limitation:** Some devices may block installation

### Release APK (Signed)
- **Location:** `app/build/outputs/apk/release/app-release.apk`
- **Signed with:** Your release keystore
- **Use case:** Distribution (Play Store, direct downloads)
- **Security:** Proper for production use

## Troubleshooting

### "adb: command not found"

**Cause:** ADB is not in your system PATH.

**Solution:**

**macOS/Linux** - Add to `~/.zshrc` or `~/.bashrc`:
```bash
export PATH=$PATH:$ANDROID_HOME/platform-tools
source ~/.zshrc  # or ~/.bashrc
```

**Windows** - Add to System Environment Variables:
1. Search for "Environment Variables"
2. Edit **Path** variable
3. Add: `C:\Android\platform-tools`
4. Restart PowerShell

### "unauthorized" Device Status

**Cause:** USB debugging not authorized on device.

**Solution:**
1. Disconnect and reconnect USB cable
2. Look for authorization prompt on device screen
3. Check "Always allow from this computer"
4. Tap "OK"
5. Run `adb devices` again

### "INSTALL_FAILED_UPDATE_INCOMPATIBLE"

**Cause:** Trying to install over an existing app with different signature.

**Solution:**
```bash
# Uninstall old version first
adb uninstall com.example.myapp

# Then install new version
adb install app-debug.apk
```

### "INSTALL_FAILED_INSUFFICIENT_STORAGE"

**Cause:** Not enough space on device.

**Solution:**
```bash
# Check available space
adb shell df /data

# Free up space by uninstalling unused apps
adb uninstall <package.name>
```

### "device offline" or "device not found"

**Cause:** Device disconnected or not responding.

**Solution:**
```bash
# Restart adb server
adb kill-server
adb start-server

# Check connection
adb devices
```

### Multiple Devices Connected Issue

**Cause:** More than one device/emulator connected.

**Error:**
```
error: more than one device/emulator
```

**Solution:**
```bash
# List all devices
adb devices

# Target specific device by serial number
adb -s 1234567890ABCDEF install app.apk

# Or use -e for emulator, -d for physical device
adb -e install app.apk  # Target emulator
adb -d install app.apk  # Target physical device
```

### APK File Not Found

**Cause:** Wrong path or APK not built yet.

**Solution:**
```bash
# Build the APK first
./gradlew assembleDebug    # macOS/Linux
.\gradlew.bat assembleDebug  # Windows

# Verify APK exists
ls app/build/outputs/apk/debug/  # macOS/Linux
dir app\build\outputs\apk\debug\  # Windows
```

### "INSTALL_PARSE_FAILED_NO_CERTIFICATES"

**Cause:** APK is unsigned or corrupted.

**Solution:**
- Use debug APK for testing (automatically signed)
- For release builds, sign the APK properly
- Verify APK file is not corrupted (re-download or rebuild)

## Best Practices

### Development Workflow

1. **Use debug builds for testing**
   ```bash
   ./gradlew assembleDebug
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

2. **Monitor logs while testing**
   ```bash
   adb logcat | grep "MyApp"
   ```

3. **Clear app data between tests**
   ```bash
   adb shell pm clear com.example.myapp
   ```

### Distribution Testing

1. **Test on multiple devices/emulators**
2. **Use release builds with proper signing**
3. **Test both new installs and updates**

### Performance Tips

```bash
# Install over Wi-Fi (ADB over TCP/IP)
adb tcpip 5555
adb connect <device-ip>:5555

# Now you can unplug USB and use wireless debugging
```

## Next Steps

Now that you know how to use adb:

1. **Practice with sample apps** - Build and install simple projects
2. **Learn more adb commands** - Explore `adb help`
3. **Set up wireless debugging** - Free yourself from USB cables
4. **Automate testing** - Create shell scripts for common tasks

## References

- [Android Developer - ADB Documentation](https://developer.android.com/studio/command-line/adb)
- [Android Developer - Test Your App](https://developer.android.com/studio/test)
- [Android Debug Bridge User Guide](https://developer.android.com/studio/command-line/adb)
- [SetupKotlin.md](SetupKotlin.md) - Android development environment setup
