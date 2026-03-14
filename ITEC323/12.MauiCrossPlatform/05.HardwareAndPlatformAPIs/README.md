# 05.HardwareAndPlatformAPIs

## Overview

This project demonstrates how to access device hardware and platform-specific features from shared .NET MAUI code using the MAUI Essentials APIs.

Compared with project 04, this app moves beyond layouts and shows how MAUI bridges a single C# codebase to native device capabilities on Android, iOS, and macOS.

The app includes three interactive feature sections:

- Camera / Photo Library: capture a photo with `MediaPicker.CapturePhotoAsync` or pick one from the gallery, then display it in an `Image` control.
- GPS Location: retrieve the current latitude, longitude, and accuracy with `Geolocation.GetLocationAsync` and show them in the UI.
- File System: write and read a text file in the app's sandboxed data directory using `FileSystem.AppDataDirectory` and standard `File` I/O.

## Demo

![Hardware and Platform APIs demo](images/hardware-platform-APIs.gif)



## Learning Objectives

By completing this project, you will be able to:

1. Use `MediaPicker` to capture and display photos from camera or gallery.
2. Request and handle runtime permissions for camera and location on Android and iOS.
3. Retrieve GPS coordinates with `Geolocation` and display them in the UI.
4. Read and write files in the app's sandboxed local storage with `FileSystem`.
5. Wrap platform APIs behind a testable `IHardwareService` interface.
6. Handle permission denials and missing hardware gracefully with user feedback.
7. Build and run on macOS or Android.

## Prerequisites

- .NET 10.0 SDK
- MAUI workload installed with `dotnet workload install maui`
- macOS with Xcode tools for Mac Catalyst and or Android SDK plus emulator
- On Android, grant Camera and Location permissions when prompted
- On macOS, grant Camera and Location permissions in System Settings > Privacy & Security

## Project Structure

```text
05.HardwareAndPlatformAPIs/
├── HardwareAndPlatformAPIs.csproj        # MAUI + CommunityToolkit package setup
├── MauiProgram.cs                        # DI registrations and app startup
├── App.xaml                              # Global resource dictionaries
├── AppShell.xaml                         # Shell host with hardware route
├── Views/
│   ├── MainPage.xaml                     # ScrollView with Camera, GPS, and File cards
│   └── MainPage.xaml.cs                  # Constructor injection + placeholder toggle
├── ViewModels/
│   └── HardwarePlatformViewModel.cs      # Commands for camera, location, file I/O
├── Services/
│   ├── IHardwareService.cs               # Hardware service contract
│   └── HardwareService.cs                # MAUI Essentials implementation
├── Models/
│   ├── PhotoResult.cs                    # Photo metadata returned by camera or gallery
│   └── LocationReading.cs                # GPS fix data returned by Geolocation
├── Platforms/
│   ├── Android/AndroidManifest.xml       # Camera + location permissions
│   ├── iOS/Info.plist                    # NSCamera and NSLocation usage descriptions
│   └── MacCatalyst/Info.plist            # NSCamera and NSLocation usage descriptions
├── QUICKSTART.md                         # Build and run guide
└── docs/
    └── Key-Takeaways.md                  # Concepts recap
```

## Run The App

See [QUICKSTART.md](QUICKSTART.md) for step-by-step instructions.

Quick commands:

```bash
dotnet build 05.HardwareAndPlatformAPIs/HardwareAndPlatformAPIs.csproj -f net10.0-maccatalyst
dotnet build -t:Run --project 05.HardwareAndPlatformAPIs/HardwareAndPlatformAPIs.csproj -f net10.0-maccatalyst
dotnet build 05.HardwareAndPlatformAPIs/HardwareAndPlatformAPIs.csproj -f net10.0-android
```

## What To Test

1. Tap Take Photo, grant the camera permission, capture a photo, and confirm it appears in the image control.
2. Tap Pick from Gallery, select an existing photo, and confirm it loads.
3. Tap Get Location, grant the location permission, and confirm latitude and longitude values appear.
4. Type text in the editor, tap Save to File, clear the editor, then tap Load from File and confirm the text is restored.
5. Deny a permission and confirm the status label shows a helpful message rather than crashing.

## Next Project

After this project, continue to `06.ComprehensiveTaskListApp`.
