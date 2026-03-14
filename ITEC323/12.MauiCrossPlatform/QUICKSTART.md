# QUICKSTART: .NET MAUI Development Setup

This guide walks you through setting up your development environment for .NET MAUI cross-platform app development. Follow the steps for your operating system.

---

## Prerequisites
- **Operating System**: macOS, Windows, or Linux (macOS recommended for full platform support)
- **.NET 10.0 SDK** installed
- **8 GB RAM** minimum (16 GB recommended for Android emulator)
- **20 GB free disk space** (for Android SDK and tools)

---

## Step 1: Verify .NET SDK Installation

```bash
# Check .NET version (should be 8.0 or higher)
dotnet --version

# If not installed, download from:
# https://dotnet.microsoft.com/download
```

**Expected Output**: `8.0.x` or higher

---

## Step 2: Install .NET MAUI Workload

The MAUI workload adds cross-platform app development support to the .NET SDK.

```bash
# Install MAUI workload
dotnet workload install maui

# This may take 5-10 minutes depending on your internet speed
```

**Verify Installation**:
```bash
# List installed workloads
dotnet workload list

# You should see:
# maui       8.x.x      sdk-band-8.0.xxx
```

---

## Step 3: Platform-Specific Setup

### macOS Setup (Recommended for Full Support)

#### 3a. Install Xcode
```bash
# Install from App Store or download from:
# https://developer.apple.com/xcode/

# After installation, accept the license:
sudo xcodebuild -license accept

# Install command line tools:
xcode-select --install
```

#### 3b. Install Android SDK
**Option 1: Via Android Studio (Easiest)**
1. Download Android Studio: https://developer.android.com/studio
2. Install Android Studio
3. Open Android Studio → SDK Manager
4. Install:
   - Android SDK Platform 34 (or latest)
   - Android SDK Build-Tools
   - Android Emulator
   - Intel x86 Emulator Accelerator (HAXM) or Google Play Intel x86 Atom System Image

**Option 2: Via Command Line**
```bash
# Install Android command line tools
brew install android-commandlinetools

# Set ANDROID_HOME environment variable
echo 'export ANDROID_HOME=$HOME/Library/Android/sdk' >> ~/.zshrc
echo 'export PATH=$PATH:$ANDROID_HOME/emulator' >> ~/.zshrc
echo 'export PATH=$PATH:$ANDROID_HOME/platform-tools' >> ~/.zshrc
source ~/.zshrc

# Install SDK components
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
sdkmanager "system-images;android-34;google_apis;arm64-v8a"
```

#### 3c. Create Android Emulator
```bash
# Create a Pixel 5 emulator with Android 14
avdmanager create avd -n Pixel5 -k "system-images;android-34;google_apis;arm64-v8a" -d pixel_5

# List available emulators
avdmanager list avd
```

---

### Windows Setup

#### 3a. Install Visual Studio 2022
1. Download: https://visualstudio.microsoft.com/downloads/
2. During installation, select:
   - **.NET Multi-platform App UI development** workload
   - **Mobile development with .NET** workload
3. This will install Android SDK and emulators automatically

#### 3b. Enable Windows Features (for Windows apps)
- Open **Windows Features**
- Enable **Hyper-V** (for emulator acceleration)
- Enable **Windows Subsystem for Linux** (optional, for better dev experience)

---

## Step 4: Create Your First MAUI App

```bash
# Navigate to your projects folder
cd ~/Projects  # or your preferred location

# Create new MAUI app
dotnet new maui -n HelloMAUI

# Navigate into project
cd HelloMAUI

# Restore dependencies
dotnet restore
```

---

## Step 5: Build and Run

### Run on macOS (Mac Catalyst)
```bash
# Build and run on macOS
dotnet build -t:Run -f net10.0-maccatalyst
```

**Expected Result**: A window opens with "Hello, World!" and a counter button.

---

### Run on Android Emulator

#### Start Emulator First
```bash
# List available emulators
emulator -list-avds

# Start the emulator (replace Pixel5 with your AVD name)
emulator -avd Pixel5 &

# Wait 30-60 seconds for emulator to fully boot
```

#### Deploy and Run App
```bash
# Build and deploy to running emulator
dotnet build -t:Run -f net10.0-android
```

**Expected Result**: App installs and launches on Android emulator with "Hello, World!" screen.

---

### Run on Physical Android Device

#### Enable Developer Mode on Device
1. Go to **Settings** → **About Phone**
2. Tap **Build Number** 7 times to enable Developer Mode
3. Go to **Settings** → **Developer Options**
4. Enable **USB Debugging**

#### Connect and Deploy
```bash
# Connect device via USB

# Check device is connected
adb devices

# You should see:
# List of devices attached
# ABC123XYZ    device

# Build and deploy
dotnet build -t:Run -f net10.0-android
```

---

## Step 6: Verify IDE Setup (Optional)

### Visual Studio Code
```bash
# Install VS Code if not already installed
# Download from: https://code.visualstudio.com/

# Install C# Dev Kit extension
code --install-extension ms-dotnettools.csdevkit

# Install .NET MAUI extension
code --install-extension ms-dotnettools.dotnet-maui

# Open your MAUI project
code ~/Projects/HelloMAUI
```

### Visual Studio 2022 (Windows/Mac)
- Open Visual Studio
- File → Open → Select `HelloMAUI.sln` or `HelloMAUI.csproj`
- Select target platform from dropdown (Android, Windows, Mac Catalyst)
- Press F5 to run

---

## Troubleshooting

### Issue: "Could not find a part of the path" on macOS
**Solution**: Grant Terminal/VS Code full disk access
- System Preferences → Security & Privacy → Privacy → Full Disk Access
- Add Terminal and/or VS Code

### Issue: Android Emulator Won't Start
**Check #1**: Verify virtualization is enabled
```bash
# On macOS/Linux, check Hypervisor framework is available
sysctl kern.hv_support

# Should return: kern.hv_support: 1
```

**Check #2**: Start emulator with more verbose output
```bash
emulator -avd Pixel5 -verbose
```

### Issue: "No workloads available"
**Solution**: Update .NET SDK to latest version
```bash
# Check for SDK updates
dotnet --list-sdks

# Update to latest
# Download from https://dotnet.microsoft.com/download
```

### Issue: Build Fails with "Cannot resolve reference"
**Solution**: Clean and rebuild
```bash
cd ~/Projects/HelloMAUI
dotnet clean
dotnet restore
dotnet build
```

### Issue: Slow Android Emulator
**Solution**: Allocate more RAM to emulator
```bash
# Edit AVD to use more RAM (2 GB minimum, 4 GB recommended)
avdmanager create avd -n Pixel5Fast -k "system-images;android-34;google_apis;arm64-v8a" -d pixel_5 --device "pixel_5"

# Or use a physical device for faster testing
```

---

## Useful Commands Reference

```bash
# MAUI Workload Management
dotnet workload list                    # List installed workloads
dotnet workload update                  # Update MAUI workload
dotnet workload repair                  # Repair broken workload

# Project Management
dotnet new maui -n MyApp               # Create new MAUI app
dotnet build                           # Build for all platforms
dotnet clean                           # Clean build artifacts
dotnet restore                         # Restore NuGet packages

# Platform-Specific Build
dotnet build -f net10.0-android         # Build for Android
dotnet build -f net10.0-ios             # Build for iOS
dotnet build -f net10.0-maccatalyst     # Build for macOS
dotnet build -f net10.0-windows10.0.19041.0  # Build for Windows

# Run Commands
dotnet build -t:Run -f net10.0-android          # Run on Android
dotnet build -t:Run -f net10.0-maccatalyst      # Run on macOS

# Device Management
adb devices                            # List connected Android devices
dotnet build -t:ListDevices            # List all available devices
xcrun simctl list                      # List iOS simulators (macOS only)

# Android Emulator
emulator -list-avds                    # List Android emulators
emulator -avd Pixel5                   # Start specific emulator
adb shell                              # Open shell in Android device
```

---

## Next Steps

Once your environment is set up:

1. ✅ **Explore the default app**: Click the counter button, examine the code
2. ✅ **Read project structure**: Open `MauiProgram.cs`, `App.xaml`, `MainPage.xaml`
3. ✅ **Start with 01.HelloWorldMAUI**: Follow the first project tutorial
4. ✅ **Experiment**: Change text, colors, add buttons
5. ✅ **Learn MVVM**: Move to 02.MvvmDependencyInjection

---

## Environment Variables Summary

Add these to your `~/.zshrc` (macOS) or Environment Variables (Windows):

```bash
# Android SDK (macOS)
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/tools
```

Windows equivalent (set in System Environment Variables):
```
ANDROID_HOME=C:\Users\YourName\AppData\Local\Android\Sdk
```

---

## Minimum System Requirements

| Platform | CPU | RAM | Storage | OS Version |
|----------|-----|-----|---------|------------|
| **macOS** | Apple Silicon (M1+) or Intel | 8 GB | 20 GB | macOS 12+ |
| **Windows** | x64 processor | 8 GB | 20 GB | Windows 10 1903+ |
| **Android Device** | ARMv8-A | 2 GB | - | Android 7.0+ (API 24) |

---

## Additional Resources

- [.NET MAUI Installation Docs](https://learn.microsoft.com/en-us/dotnet/maui/get-started/installation)
- [Android Studio Download](https://developer.android.com/studio)
- [Xcode Download](https://developer.apple.com/xcode/)
- [Visual Studio Download](https://visualstudio.microsoft.com/)

---

**Need Help?** 
- Check terminal output for specific error messages
- Visit [module README](README.md) for more details
- Consult [docs/](docs/) for troubleshooting guides

---

**Setup Complete!** 🎉 You're ready to start building cross-platform apps with .NET MAUI.
