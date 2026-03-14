# QUICKSTART - 01.HelloWorldMAUI

Follow this guide to build and run your first MAUI app.

## 1. Verify Prerequisites

```bash
dotnet --version
dotnet workload list
```

Confirm:

- .NET SDK is 10.0 or higher
- `maui` workload is installed

If needed:

```bash
dotnet workload install maui
```

## 2. Build The Project

From repository root:

```bash
cd 12.MauiCrossPlatform/01.HelloWorldMAUI

# Build for macOS (Mac Catalyst)
dotnet build -f net10.0-maccatalyst

# Build for Android
dotnet build -f net10.0-android

# Build for iOS (optional, requires Xcode and Simulator)
dotnet build -f net10.0-ios

# Build for WebAssembly (optional)
dotnet build -f net10.0-wasm
# Build for all platforms (optional)
dotnet build
```

Expected result:

```text
Build succeeded.
```

## 3. Run On macOS (Mac Catalyst)

```bash

# Run on Mac Catalyst
dotnet build -t:Run -f net10.0-maccatalyst

# Run on Android (optional, requires emulator)
dotnet build -t:Run -f net10.0-android

# Run on iOS Simulator (optional, requires Xcode and Simulator)
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=$(xcrun simctl list devices booted | awk -F '[()]' '/Booted/{print $2; exit}')


# Run on all platforms (optional)
dotnet build -t:Run
```

What to test:

1. The app window opens with a title and welcome text.
2. Tap `Tap to Count` and confirm the button text updates.
3. Tap `Reset Counter` and confirm the state resets.

## 4. Run On Android (Optional)

Start an Android emulator first, then run:

```bash
dotnet build -t:Run --project 12.MauiCrossPlatform/01.HelloWorldMAUI/HelloWorldMAUI.csproj -f net10.0-android
```

## 5. Run On iOS Simulator (macOS)

From repository root:

```bash
cd 12.MauiCrossPlatform/01.HelloWorldMAUI
```

Start Simulator:

```bash
open -a Simulator
```

Run on iOS:

```bash
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=$(xcrun simctl list devices booted | awk -F '[()]' '/Booted/{print $2; exit}')
```

If you need to target a specific simulator:

```bash
xcrun simctl list devices available
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=<SIMULATOR_UDID>
```

## 6. Explore Important Files

- `MainPage.xaml`: UI layout (labels, image, buttons)
- `MainPage.xaml.cs`: click event handlers and counter logic
- `MauiProgram.cs`: startup configuration
- `App.xaml`: shared styles and colors
- `Platforms/`: platform-specific code

## Common Issues

### Error: MAUI workload missing

```bash
dotnet workload install maui
```

### Error: Android emulator/device unavailable

- Start emulator manually in Android Studio
- Or run Mac Catalyst target first

### Error: iOS simulator unavailable or not detected

```bash
open -a Simulator
xcrun simctl list devices available
```

If Simulator still does not show up, ensure Xcode CLI tools are selected:

```bash
xcode-select -p
sudo xcode-select -s /Applications/Xcode.app/Contents/Developer
```

### Error MT1206: Could not find simulator runtime iOS-26-2

This means the default launch target is stale (for example iOS 26.2) while your installed runtime is different (for example iOS 26.3).

Use the booted simulator UDID explicitly:

```bash
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=$(xcrun simctl list devices booted | awk -F '[()]' '/Booted/{print $2; exit}')
```

If needed, list and pick a specific device:

```bash
xcrun simctl list devices available
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=<SIMULATOR_UDID>
```

### Error: Build fails after SDK changes

```bash
dotnet clean 12.MauiCrossPlatform/01.HelloWorldMAUI/HelloWorldMAUI.csproj
dotnet restore 12.MauiCrossPlatform/01.HelloWorldMAUI/HelloWorldMAUI.csproj
dotnet build 12.MauiCrossPlatform/01.HelloWorldMAUI/HelloWorldMAUI.csproj -f net10.0-maccatalyst
```

### Error: Xcode version mismatch for Mac Catalyst

Symptom example:

```text
error This version of .NET for MacCatalyst requires Xcode <version>. The current version of Xcode is <version>.
```

For this project, a compatibility setting is already included in `HelloWorldMAUI.csproj` (`ValidateXcodeVersion=false` for iOS and Mac Catalyst), so builds can continue when Xcode is newer than the SDK-pinned version.

If you still see the error:

1. Clean and rebuild the project.
2. Confirm you are using this project's `.csproj` file.
3. Update .NET SDK/workloads to the latest patch version.

## Success Checklist

- Project builds successfully
- App runs on at least one platform
- Counter button updates UI text
- Reset button restores default state
- You can explain the role of `MauiProgram.cs`, `App.xaml`, `MainPage.xaml`, and `Platforms/`
