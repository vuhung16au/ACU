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

## 5. Explore Important Files

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
