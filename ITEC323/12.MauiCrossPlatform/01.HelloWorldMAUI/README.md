# 01.HelloWorldMAUI

## Overview

This project is your first .NET MAUI app. 
You will build and run a simple cross-platform app that uses XAML for UI and C# code-behind for interaction.

The app includes:

- A welcome title and description
- A counter button that updates text when tapped
- A reset button to restore the initial state

## Demo 

![HelloWorld MAUI demo](images/hello-world-maui.gif)

![HelloWorld MAUI Android iOS macOS demo](images/hello-world-maui-android-iso-macos.gif)

## Learning Objectives

By completing this project, you will be able to:

1. Create and run a .NET MAUI application.
2. Identify key MAUI files: `MauiProgram.cs`, `App.xaml`, `MainPage.xaml`, and `Platforms/`.
3. Build a simple UI with labels, image, and buttons in XAML.
4. Handle button click events in C# code-behind.
5. Test the app on macOS (Mac Catalyst) or Android.

## Prerequisites

- .NET 10.0 SDK
- MAUI workload installed (`dotnet workload install maui`)
- macOS with Xcode tools (for Mac Catalyst) and/or Android SDK + emulator

## Project Structure

```text
01.HelloWorldMAUI/
├── HelloWorldMAUI.csproj         # MAUI project configuration
├── MauiProgram.cs                # App startup and service setup
├── App.xaml                      # Global resources (colors/styles)
├── AppShell.xaml                 # Main shell navigation container
├── MainPage.xaml                 # Main UI page (XAML)
├── MainPage.xaml.cs              # Main page code-behind logic
├── Platforms/                    # Platform-specific startup code
├── Resources/                    # Images, fonts, styles, splash/icon
├── QUICKSTART.md                 # Build and run instructions
└── docs/
    └── Key-Takeaways.md          # Core concepts recap
```

## Run The App

See [QUICKSTART.md](QUICKSTART.md) for step-by-step instructions.

Quick commands:

```bash
# From repository root
dotnet build 12.MauiCrossPlatform/01.HelloWorldMAUI/HelloWorldMAUI.csproj -f net10.0-maccatalyst

# Alternatively, navigate to project folder first
cd 12.MauiCrossPlatform/01.HelloWorldMAUI

# Run on Mac Catalyst
dotnet build -t:Run -f net10.0-maccatalyst

# Run on Android (optional, requires emulator)
dotnet build -t:Run -f net10.0-android

# Run on iOS Simulator (optional, requires Xcode and Simulator)
# Start Simulator first, then target the currently booted device.
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=$(xcrun simctl list devices booted | awk -F '[()]' '/Booted/{print $2; exit}')

# If no simulator is booted:
open -a Simulator
xcrun simctl list devices available

# Or from root with full path
dotnet build -t:Run --project 12.MauiCrossPlatform/01.HelloWorldMAUI/HelloWorldMAUI.csproj -f net10.0-maccatalyst
```

## Next Project

After this project, continue to:

- `12.MauiCrossPlatform/02.MvvmDependencyInjection`

You will move logic from code-behind into ViewModels using MVVM and .NET Community Toolkit.
