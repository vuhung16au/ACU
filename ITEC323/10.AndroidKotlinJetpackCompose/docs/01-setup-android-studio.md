# Setting Up Android Studio

## Overview
Android Studio is the official IDE for Android development, built on IntelliJ IDEA with Android-specific tools integrated.

## System Requirements

### macOS
- **OS**: macOS 13.0 (Ventura) or later
- **RAM**: 8GB minimum, 16GB recommended
- **Disk**: 10GB free space (Android Studio + SDKs + emulator)
- **Processor**: Apple Silicon (M1/M2/M3) or Intel Core i5+

### Windows
- **OS**: Windows 10 64-bit or later
- **RAM**: 8GB minimum, 16GB recommended
- **Disk**: 10GB free space
- **Processor**: Intel Core i5+ or AMD Ryzen equivalent

### Linux
- **OS**: 64-bit Ubuntu 20.04 LTS or later (Debian-based recommended)
- **RAM**: 8GB minimum, 16GB recommended
- **Disk**: 10GB free space

---

## Installation Steps

### 1. Download Android Studio

Visit: [developer.android.com/studio](https://developer.android.com/studio)

**macOS**:
- Download `.dmg` file (~1.1GB)
- Open `.dmg` and drag Android Studio to Applications folder
- Launch from Applications (right-click → Open if security prompt appears)

**Windows**:
- Download `.exe` installer (~1.1GB)
- Run installer as Administrator
- Follow installation wizard (default options recommended)

**Linux**:
```bash
# Extract and run
tar -xzf android-studio-*.tar.gz
cd android-studio/bin
./studio.sh
```

### 2. Complete Setup Wizard

First launch triggers Android Studio Setup Wizard:

1. **Welcome Screen**: Click "Next"
2. **Install Type**: Choose **Standard** (recommended)
3. **UI Theme**: Select theme preference (can change later)
4. **Verify Settings**: Review components to install:
   - Android SDK
   - Android SDK Platform
   - Android Virtual Device (AVD)
   - Performance (Intel HAXM for Intel Macs/Windows)
5. **License Agreement**: Accept all Android SDK licenses
6. **Downloading Components**: Wait 10-30 minutes (depends on internet speed)
   - Downloads ~3GB of SDKs and tools
   - Progress bar shows current download

### 3. Configure SDK for Android 16

After wizard completes:

1. **Open SDK Manager**:
   - macOS: `Android Studio → Preferences → Appearance & Behavior → System Settings → Android SDK`
   - Windows/Linux: `File → Settings → Appearance & Behavior → System Settings → Android SDK`

2. **SDK Platforms Tab**:
   - ✅ Check **Android 16.0 ("Baklava")** - API Level 36
   - ✅ Check **Android 8.0 (Oreo)** - API Level 26 (for minimum SDK)
   - Click **Show Package Details** to verify components:
     - Android SDK Platform 36
     - Google APIs Intel x86 System Image (for emulator)
     - Google Play Intel x86 System Image (if available)

3. **SDK Tools Tab**:
   Ensure these are checked:
   - ✅ Android SDK Build-Tools
   - ✅ Android Emulator
   - ✅ Android SDK Platform-Tools
   - ✅ Google Play services
   - ✅ Intel x86 Emulator Accelerator (HAXM) - Intel only

4. **Click Apply**: Downloads required packages (~2GB)
5. **Click OK**: Closes SDK Manager

---

## Create Virtual Device (Emulator)

### Why Use Emulator?
- Test app without physical device
- Test different screen sizes/Android versions
- Faster iteration during development

### Setup Steps

1. **Open AVD Manager**:
   - Click **Tools → Device Manager** (or AVD icon in toolbar)
   - Or: `Ctrl+Shift+A` / `Cmd+Shift+A` → type "AVD Manager"

2. **Create Virtual Device**:
   - Click **Create Virtual Device** button
   
3. **Select Hardware**:
   - Category: **Phone**
   - Recommended: **Pixel 8 Pro** (6.7" display, high resolution)
   - Alternative: **Pixel 7** or **Pixel 6** (lighter on resources)
   - Click **Next**

4. **Select System Image**:
   - **Release Name**: Baklava
   - **API Level**: 36
   - **ABI**: x86_64 (Intel) or arm64-v8a (Apple Silicon)
   - **Target**: Android 16.0 (Google APIs)
   - If not downloaded: Click **Download** next to system image (~1GB)
   - Click **Next** after download completes

5. **Verify Configuration**:
   - **AVD Name**: Auto-generated (e.g., "Pixel_8_Pro_API_36")
   - **Startup orientation**: Portrait
   - **Advanced Settings** (optional tweaks):
     - RAM: 2048 MB minimum (increase if you have 16GB+ system RAM)
     - Internal Storage: 2048 MB
     - SD Card: 512 MB
   - Click **Finish**

6. **Launch Emulator**:
   - In Device Manager, find your AVD
   - Click ▶️ (Play) icon
   - **First boot**: 2-5 minutes (subsequent boots: 30-60 seconds)
   - Emulator window opens with Android home screen

**Success**: You see Android home screen with apps and navigation bar

---

## Verify Installation

### Test with Sample Project

1. **Create New Project**:
   - Click **New Project** on Welcome screen
   - Or: `File → New → New Project`

2. **Select Template**:
   - Choose **Empty Activity (with Compose)**
   - Click **Next**

3. **Configure Project**:
   - **Name**: TestApp
   - **Package name**: com.test.myapp
   - **Save location**: Any folder
   - **Language**: Kotlin
   - **Minimum SDK**: API 26 ("Oreo"; Android 8.0)
   - Click **Finish**

4. **Wait for Gradle Sync**:
   - Bottom status bar shows "Syncing..."
   - First sync: 2-5 minutes (downloads dependencies)
   - Check "Build" panel for errors

5. **Run App**:
   - Ensure emulator is running
   - Click ▶️ **Run** button (green arrow in toolbar)
   - Or press: `Ctrl+R` (Windows/Linux) / `Cmd+R` (macOS)
   - Select your virtual device from list
   - App builds and installs (~30 seconds first time)

**Success**: App launches in emulator showing "Hello Android!" text

---

## Troubleshooting

### Emulator Won't Start

**Apple Silicon Macs (M1/M2/M3)**:
- Ensure you installed **arm64-v8a** system image (not x86)
- If prompted, install Rosetta 2:
  ```bash
  softwareupdate --install-rosetta
  ```

**Intel Macs/Windows**:
- Install Intel HAXM:
  - SDK Manager → SDK Tools → Intel x86 Emulator Accelerator (HAXM)
  - Or download directly: [github.com/intel/haxm/releases](https://github.com/intel/haxm/releases)
- Enable virtualization in BIOS (Windows)

**Linux**:
- Install KVM:
  ```bash
  sudo apt install qemu-kvm libvirt-daemon-system libvirt-clients bridge-utils
  sudo adduser $USER kvm
  sudo adduser $USER libvirt
  ```
- Reboot after installation

### Gradle Sync Fails

**Issue**: "Failed to resolve dependencies" or timeout errors

**Solutions**:
1. Check internet connection
2. Invalidate caches:
   - `File → Invalidate Caches → Invalidate and Restart`
3. Update Gradle version:
   - Open `gradle/wrapper/gradle-wrapper.properties`
   - Change version to latest: `distributionUrl=https\://services.gradle.org/distributions/gradle-8.5-bin.zip`
4. Sync project:
   - `File → Sync Project with Gradle Files`

### SDK Manager Shows Empty Lists

- Check proxy/firewall settings
- Try HTTP download:
  - `File → Settings → Appearance & Behavior → System Settings → HTTP Proxy`
  - Select "Auto-detect proxy settings"
- Clear download cache:
  - Delete `~/Library/Android/sdk/.downloadIntermediates` (macOS)
  - Delete `C:\Users\<username>\AppData\Local\Android\Sdk\.downloadIntermediates` (Windows)

### "ADB not found" Error

- Reinstall Platform Tools:
  - SDK Manager → SDK Tools → Uncheck "Android SDK Platform-Tools"
  - Apply → Re-check → Apply
- Manually set ADB path:
  - `File → Settings → Appearance & Behavior → System Settings → Android SDK`
  - Note Android SDK Location (e.g., `/Users/<username>/Library/Android/sdk`)
  - ADB should be at `<SDK Location>/platform-tools/adb`

### Emulator Performance Issues

- Allocate more RAM to emulator (AVD settings)
- Use smaller device profile (Pixel 5 instead of Pixel 8 Pro)
- Enable hardware acceleration (HAXM or KVM)
- Close other resource-heavy applications
- Update graphics driver (Windows)

---

## Using Physical Android Device

### Prerequisites
- USB cable
- Android device running Android 8.0+ (API 26+)

### Enable Developer Mode

1. Open **Settings** on device
2. Navigate to **About Phone**
3. Find **Build Number**
4. Tap **Build Number** 7 times
5. Enter PIN/password if prompted
6. Message: "You are now a developer!"

### Enable USB Debugging

1. Go to **Settings → System → Developer Options**
   - If hidden: Settings → Search "Developer Options"
2. Toggle **Developer Options** ON
3. Scroll down to **USB Debugging**
4. Toggle **USB Debugging** ON
5. Read warning and tap **OK**

### Connect Device

1. Connect device to computer via USB
2. On device: Prompt appears "Allow USB Debugging?"
   - Check "Always allow from this computer"
   - Tap **Allow**
3. In Android Studio:
   - Device appears in device selector dropdown (top toolbar)
   - Shows device model and Android version
4. Click ▶️ **Run** to install app on physical device

**Advantages over emulator**:
- Real hardware performance
- Access to sensors (gyroscope, fingerprint, etc.)
- Camera and GPS functionality
- Faster for complex apps

---

## Keyboard Shortcuts (Essential)

| Action | macOS | Windows/Linux |
|--------|-------|---------------|
| Run app | `Cmd+R` | `Ctrl+R` or `Shift+F10` |
| Build project | `Cmd+F9` | `Ctrl+F9` |
| Find file | `Cmd+Shift+O` | `Ctrl+Shift+N` |
| Search everywhere | `Shift+Shift` (double) | `Shift+Shift` (double) |
| Code completion | `Ctrl+Space` | `Ctrl+Space` |
| Reformat code | `Cmd+Alt+L` | `Ctrl+Alt+L` |
| Rename | `Shift+F6` | `Shift+F6` |
| Show quick documentation | `F1` | `Ctrl+Q` |

---

## Next Steps

After successful installation:
1. ✅ Familiarize yourself with Android Studio interface
2. ✅ Explore project structure (left sidebar)
3. ✅ Open `MainActivity.kt` - your app's entry point
4. ✅ Try **Split** view to see live preview of Compose UI
5. ✅ Proceed to [02.android-platform-architecture.md](02-android-platform-architecture.md)
6. ✅ Complete [01.HelloWorldKotlin](../01.HelloWorldKotlin/) project

---

## Resources

- [Official Installation Guide](https://developer.android.com/studio/install)
- [Emulator Documentation](https://developer.android.com/studio/run/emulator)
- [AVD Hardware Profiles](https://developer.android.com/studio/run/managing-avds)
- [Kotlin in Android Studio](https://developer.android.com/kotlin/first)

**Estimated Setup Time**: 45-90 minutes (first time, including downloads)

---

*Need help? Check course forum or attend office hours.*
