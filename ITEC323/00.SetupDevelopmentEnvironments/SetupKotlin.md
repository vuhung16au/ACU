
# VS Code + Kotlin/Android Setup Guide

## Overview

This guide provides step-by-step instructions for setting up Visual Studio Code (VS Code) to develop Kotlin-based Android applications. While Android Studio is the official IDE, VS Code offers a lightweight, fast alternative for developers who prefer a minimal setup, especially for modern code-driven development with Jetpack Compose.

## What You'll Need

To develop Android apps with Kotlin in VS Code, you need:

- **Java Development Kit (JDK):** Required to run Gradle and compile Kotlin code
- **Android SDK Command Line Tools:** Core Android development tools including `sdkmanager` and `adb`
- **Gradle:** Build automation tool (included with Android projects)
- **VS Code Extensions:** Add Kotlin and Android support to VS Code
- **Environment Variables:** Configure system paths for tools to work together

## Prerequisites

- **Visual Studio Code:** Download from [code.visualstudio.com](https://code.visualstudio.com/)
- **Terminal/Command Line:** Basic familiarity with command line operations
- **Internet Connection:** Required for downloading tools and dependencies

## Installation

### Step 1: Install Java Development Kit (JDK)

The JDK is required to run Gradle and compile Kotlin code.

#### Windows

Using Chocolatey (if installed):

```powershell
choco install openjdk17
```

Or download the installer from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/) (recommended for LTS versions).

#### macOS

Using Homebrew:

```bash
brew install openjdk@17
```

After installation, link it:

```bash
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

#### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

#### Verify Installation

```bash
java -version
```

You should see output showing Java version 17 or higher.

### Step 2: Install Android SDK Command Line Tools

#### Download SDK Command Line Tools

1. Visit [Android Command Line Tools](https://developer.android.com/studio#command-tools)
2. Download the appropriate version for your OS:
   - **Windows:** `commandlinetools-win-[version]_latest.zip`
   - **macOS:** `commandlinetools-mac-[version]_latest.zip`
   - **Linux:** `commandlinetools-linux-[version]_latest.zip`

#### Extract and Setup (All Platforms)

**Windows:**

1. Extract the zip file to `C:\Android\cmdline-tools\`
2. Rename the extracted folder from `cmdline-tools` to `latest`
3. Final path should be: `C:\Android\cmdline-tools\latest\`

**macOS/Linux:**

```bash
# Create directory structure
mkdir -p ~/Android/cmdline-tools

# Extract the downloaded file (adjust filename as needed)
# macOS
unzip ~/Downloads/commandlinetools-mac-*_latest.zip -d ~/Android/cmdline-tools/

# Linux
unzip ~/Downloads/commandlinetools-linux-*_latest.zip -d ~/Android/cmdline-tools/

# Rename the folder
mv ~/Android/cmdline-tools/cmdline-tools ~/Android/cmdline-tools/latest
```

### Step 3: Configure Environment Variables

Environment variables tell your system where to find Android tools.

#### Windows

1. Open **System Properties** → **Environment Variables**
2. Under **User Variables**, click **New**
3. Add these variables:

   - **Variable name:** `ANDROID_HOME`
   - **Variable value:** `C:\Android`

   - **Variable name:** `JAVA_HOME`
   - **Variable value:** `C:\Program Files\Java\jdk-17` (adjust based on your JDK installation path)

4. Edit the **Path** variable and add:
   - `%ANDROID_HOME%\cmdline-tools\latest\bin`
   - `%ANDROID_HOME%\platform-tools`
   - `%JAVA_HOME%\bin`

5. Click **OK** and restart your terminal/PowerShell

#### macOS/Linux

Add these lines to your shell configuration file (`~/.zshrc` for macOS or `~/.bashrc` for Linux):

```bash
# Android SDK
export ANDROID_HOME=$HOME/Android
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools

# Java
export JAVA_HOME=/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home  # macOS
# OR for Linux:
# export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$PATH:$JAVA_HOME/bin
```

Apply the changes:

```bash
source ~/.zshrc   # macOS
# OR
source ~/.bashrc  # Linux
```

#### Verify Environment Variables

```bash
echo $ANDROID_HOME  # macOS/Linux
echo %ANDROID_HOME%  # Windows

echo $JAVA_HOME     # macOS/Linux
echo %JAVA_HOME%    # Windows
```

### Step 4: Install Android SDK Components

Use the SDK Manager to install required Android components:

```bash
# Accept licenses (required before installing packages)
sdkmanager --licenses

# Install essential components
sdkmanager "platform-tools"
sdkmanager "platforms;android-34"
sdkmanager "build-tools;34.0.0"
sdkmanager "system-images;android-34;google_apis;x86_64"  # For emulator

# Optional: Install emulator
sdkmanager "emulator"
```

**Explanation:**
- `platform-tools`: Includes `adb` (Android Debug Bridge) for device communication
- `platforms;android-34`: Android API 34 (Android 14) SDK
- `build-tools`: Tools needed to build Android apps
- `system-images`: OS image for Android emulator

### Step 5: Install VS Code Extensions

Open VS Code and install these essential extensions:

#### Required Extensions

1. **Kotlin Language** (by fwcd)
   - Provides syntax highlighting, code completion, and linting for Kotlin
   - Install: Press `Ctrl+Shift+X` (Windows/Linux) or `Cmd+Shift+X` (macOS)
   - Search for "Kotlin" and install the one by **fwcd**

2. **Gradle for Java** (by Microsoft)
   - Adds Gradle task management to VS Code
   - Displays build tasks in sidebar for easy execution
   - Search for "Gradle for Java" and install

3. **Android Support**
   - **Android iOS Emulator** (by DiemasMichiels) - Launch emulators from VS Code
   - **Logcat** (by Neil Ding) - View Android device logs
   - Install both for better Android development experience

#### Optional but Helpful Extensions

- **XML** (by Red Hat) - Better support for Android XML files
- **Material Icon Theme** - Better file icons for Android projects

### Step 6: Verify Your Setup

Create a test to ensure everything works:

#### Check All Tools

```bash
# Java
java -version
javac -version

# Android SDK
sdkmanager --version
adb version

# Gradle (will be available in Android projects)
# Location: ./gradlew (in project root)
```

#### Create a Test Android Project

You can create a new Android project using Android Studio initially, or use a template. For learning purposes, we recommend:

1. Download a sample project from [Android Samples](https://github.com/android/architecture-samples)
2. Open the project folder in VS Code
3. Look for the **Gradle** icon in the VS Code sidebar
4. Expand tasks and try running `assembleDebug`

## Development Workflow

Once setup is complete, your typical workflow will be:

### 1. Write Code
Write your Kotlin and Jetpack Compose code in VS Code with full syntax highlighting and code completion.

### 2. Build the App

Using VS Code Gradle panel:
- Click the **Gradle** icon in the sidebar
- Expand your project → **Tasks** → **build**
- Click `assembleDebug`

Or use the terminal:

```bash
./gradlew assembleDebug    # macOS/Linux
.\gradlew.bat assembleDebug  # Windows
```

### 3. Run on Device

**Physical Device:**

1. Enable Developer Options on your Android phone
2. Enable USB Debugging
3. Connect phone via USB
4. Verify connection: `adb devices`
5. Install app: `./gradlew installDebug`

**Emulator:**

```bash
# List available emulators
emulator -list-avds

# Create an emulator (if needed)
avdmanager create avd -n MyEmulator -k "system-images;android-34;google_apis;x86_64"

# Launch emulator
emulator -avd MyEmulator

# Install app (in another terminal)
./gradlew installDebug
```

### 4. View Logs

In VS Code terminal:

```bash
adb logcat
```

Or use the **Logcat** extension for a better viewing experience.

## Trade-offs: VS Code vs Android Studio

### What You Gain with VS Code
✅ Lightweight and fast startup  
✅ Minimal resource usage  
✅ Familiar environment if you already use VS Code  
✅ Great for code-driven Jetpack Compose development  
✅ Excellent terminal integration  

### What You Lose Without Android Studio
❌ No visual layout editor  
❌ No Compose Preview (can't see UI without running the app)  
❌ Manual dependency management  
❌ More complex debugging setup  
❌ No built-in device manager  
❌ No visual profiling tools  

**Recommendation:** VS Code is great for learning and lightweight development. For production apps with complex UIs and performance requirements, Android Studio provides more tools.

## Troubleshooting

### "sdkmanager: command not found"

**Cause:** Environment variables not set correctly or terminal not restarted.

**Solution:**
1. Verify `ANDROID_HOME` is set correctly
2. Verify `cmdline-tools/latest/bin` is in your PATH
3. Restart your terminal
4. On Windows, restart PowerShell as Administrator

### "JAVA_HOME is not set"

**Cause:** Java environment variable missing.

**Solution:**
1. Follow Step 3 to set `JAVA_HOME`
2. Restart terminal
3. Verify: `echo $JAVA_HOME` (macOS/Linux) or `echo %JAVA_HOME%` (Windows)

### "No devices/emulators found"

**Cause:** Device not connected or emulator not running.

**Solution for physical device:**
```bash
# Check if device is connected
adb devices

# If device shows "unauthorized", check phone for USB debugging prompt
```

**Solution for emulator:**
```bash
# Ensure emulator is running
emulator -list-avds
emulator -avd MyEmulator
```

### Gradle Build Fails

**Common causes:**
- Wrong JDK version (Android requires JDK 11 or higher)
- Missing Android SDK components
- Incorrect `ANDROID_HOME` path

**Solution:**
```bash
# Check JDK version
java -version

# Install missing SDK components
sdkmanager --list
sdkmanager "platforms;android-34"
sdkmanager "build-tools;34.0.0"

# Clean and rebuild
./gradlew clean
./gradlew assembleDebug
```

### VS Code Gradle Extension Not Working

**Solution:**
1. Ensure `Gradle for Java` extension is installed
2. Reload VS Code: `Ctrl+Shift+P` → "Reload Window"
3. Check if `build.gradle` or `build.gradle.kts` exists in your project
4. Look for errors in VS Code Output panel: View → Output → Select "Gradle Tasks"

## Next Steps

Now that your environment is set up:

1. **Learn Kotlin Basics:** Understand Kotlin syntax and concepts
2. **Explore Jetpack Compose:** Modern UI toolkit for Android
3. **Build Sample Projects:** Start with simple apps (Hello World, Counter, Todo list)
4. **Study Android Architecture:** Learn about Activities, ViewModels, and the Android lifecycle

## References

- [Android Developer Docs](https://developer.android.com/)
- [Kotlin Language Reference](https://kotlinlang.org/docs/reference/)
- [Jetpack Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial)
- [Gradle User Guide](https://docs.gradle.org/)
- [VS Code Kotlin Extension](https://github.com/fwcd/vscode-kotlin)

