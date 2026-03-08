# Quick Start Guide - Android Development

## Prerequisites
- **macOS**: macOS 13.0 (Ventura) or later
- **RAM**: 8GB minimum, 16GB recommended
- **Disk Space**: 10GB for Android Studio + SDKs
- **Internet**: Required for downloading SDKs and emulator images

## Step 1: Install Android Studio

### macOS Installation
1. Download Android Studio from [developer.android.com/studio](https://developer.android.com/studio)
2. Open the `.dmg` file and drag Android Studio to Applications
3. Launch Android Studio from Applications folder
4. Follow the setup wizard:
   - Choose "Standard" installation
   - Accept license agreements
   - Wait for SDK and emulator downloads (~3GB)

**Time Required**: 15-30 minutes depending on internet speed

## Step 2: Configure SDK for Android 16

1. Open **Tools → SDK Manager** (or Preferences → Appearance & Behavior → System Settings → Android SDK)
2. In **SDK Platforms** tab:
   - ✅ Check **Android 16.0 (Baklava)** - API Level 36
   - ✅ Check **Show Package Details** to see components
3. In **SDK Tools** tab, ensure these are installed:
   - ✅ Android SDK Build-Tools
   - ✅ Android Emulator
   - ✅ Android SDK Platform-Tools
4. Click **Apply** and wait for downloads

## Step 3: Create Virtual Device (Emulator)

1. Open **Tools → Device Manager**
2. Click **Create Virtual Device**
3. Select **Phone → Pixel 8 Pro** (or any recent device)
4. Click **Next**
5. Select **Android 16.0 (Baklava) API Level 36** system image
   - If not downloaded, click **Download** next to it
6. Click **Next**, then **Finish**
7. Click ▶️ (Play) button to test emulator launch

**Emulator should boot in 1-2 minutes on first launch**

## Step 4: Create Your First Project

1. Click **New Project**
2. Select **Empty Activity (Compose)**
3. Configure project:
   - **Name**: HelloAndroid
   - **Package name**: com.itec323.helloandroid
   - **Save location**: Choose a folder
   - **Language**: Kotlin
   - **Minimum SDK**: API 26 (Android 8.0)
4. Click **Finish**
5. Wait for Gradle sync (~2-5 minutes first time)

## Step 5: Run Your App

1. Ensure emulator is running (or connect physical device)
2. Click ▶️ **Run** button (or press `Ctrl+R` / `Cmd+R`)
3. Select your virtual device
4. App builds and launches in ~30 seconds

**Success**: You should see "Hello Android!" on the emulator screen 🎉

## Troubleshooting

### Emulator Won't Start
- **Mac with Apple Silicon**: Enable Rosetta 2 if prompted
- **Intel Mac**: Ensure Intel HAXM is installed (SDK Manager → SDK Tools)
- **All**: Check virtualization is enabled in BIOS

### Gradle Sync Fails
- Check internet connection
- Click **File → Invalidate Caches → Invalidate and Restart**
- Update Gradle wrapper if prompted

### "ADB not found" Error
- Open **Tools → SDK Manager → SDK Tools**
- Reinstall **Android SDK Platform-Tools**

### Build Errors
- Ensure correct SDK versions installed
- Check minimum SDK is set to API 26 or lower
- Sync Gradle: **File → Sync Project with Gradle Files**

## Using Physical Android Device

1. Enable **Developer Options** on device:
   - Go to Settings → About Phone
   - Tap **Build Number** 7 times
2. Enable **USB Debugging**:
   - Settings → System → Developer Options → USB Debugging
3. Connect device via USB
4. Accept "Allow USB Debugging" prompt on device
5. Device appears in Android Studio's device selector

## Next Steps

Once setup is complete:
1. Explore Project Structure (Left sidebar)
2. Open `MainActivity.kt` - your app's entry point
3. Open `ui/theme/` - Material Design 3 theming
4. Try Live Preview: Click **Split** mode in `MainActivity.kt`
5. Complete [01.HelloWorldKotlin](01.HelloWorldKotlin/) project

## Resources
- [Android Studio User Guide](https://developer.android.com/studio/intro)
- [Emulator Setup](https://developer.android.com/studio/run/emulator)
- [Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial)
