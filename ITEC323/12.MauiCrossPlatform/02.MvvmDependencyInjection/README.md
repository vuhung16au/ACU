# 02.MvvmDependencyInjection

## Overview

This project introduces the MVVM pattern in .NET MAUI using Dependency Injection and .NET Community Toolkit.

Compared with project 01, UI logic is moved out of code-behind into a ViewModel.

The app includes:

- A ViewModel that inherits from ObservableObject
- Observable properties created with [ObservableProperty]
- Commands created with [RelayCommand]
- Constructor injection for services, ViewModels, pages, and shell
- Two-way data binding between Entry and ViewModel


## Demo

![MVVM and Dependency Injection demo](images/maui-mvvm-DI.gif)


## Learning Objectives

By completing this project, you will be able to:

1. Explain why MVVM improves separation of concerns in MAUI apps.
2. Use [ObservableProperty] for bindable state in a ViewModel.
3. Use [RelayCommand] for UI actions without code-behind click handlers.
4. Register services, ViewModels, and pages in MauiProgram.cs.
5. Resolve pages and shell using constructor injection.
6. Build and run on macOS (Mac Catalyst) or Android.

## Prerequisites

- .NET 10.0 SDK
- MAUI workload installed (dotnet workload install maui)
- macOS with Xcode tools for Mac Catalyst and/or Android SDK + emulator

## Project Structure

```text
02.MvvmDependencyInjection/
├── MvvmDependencyInjection.csproj      # MAUI + CommunityToolkit package setup
├── MauiProgram.cs                      # DI registrations and app startup
├── App.xaml                            # Global resources
├── AppShell.xaml                       # Shell container
├── Views/
│   ├── MainPage.xaml                   # UI with bindings and command wiring
│   └── MainPage.xaml.cs                # Constructor injection + accessibility announce
├── ViewModels/
│   └── MainViewModel.cs                # Observable properties and relay commands
├── Services/
│   ├── IWelcomeMessageService.cs       # Service contract
│   └── WelcomeMessageService.cs        # Service implementation
├── Models/
│   └── CounterSnapshot.cs              # Simple domain model
├── QUICKSTART.md                       # Build/run guide
└── docs/
    └── Key-Takeaways.md                # Concepts recap
```

## Run The App

See [QUICKSTART.md](QUICKSTART.md) for step-by-step instructions.

Quick commands:

```bash
# From repository root
dotnet build 12.MauiCrossPlatform/02.MvvmDependencyInjection/MvvmDependencyInjection.csproj -f net10.0-maccatalyst

# Run on Mac Catalyst
dotnet build -t:Run --project 12.MauiCrossPlatform/02.MvvmDependencyInjection/MvvmDependencyInjection.csproj -f net10.0-maccatalyst
```

## What To Test

1. Enter your name in the Entry field.
2. Tap Increment Count and confirm count/status update from the ViewModel.
3. Tap Reset and confirm all values return to initial state.
4. Confirm no button click handlers are required for counter logic.

## Next Project

After this project, continue to:

- 12.MauiCrossPlatform/03.ShellNavigation

You will build multi-page flows using routes and URI-based navigation.
