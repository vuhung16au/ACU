# 01.HelloWorldMAUI

## Overview

This project is your first .NET MAUI app, now extended with a browser version.
Students can learn the same counter lesson in two ways:

- as a native .NET MAUI app
- as a browser app hosted by ASP.NET Core

Both experiences now share the same Razor UI, which makes it easier to compare native hosting and web hosting in one beginner-friendly example.

## Demo

![HelloWorld MAUI demo](images/hello-world-maui.gif)

![HelloWorld MAUI Android iOS macOS demo](images/hello-world-maui-android-iso-macos.gif)

## Learning Objectives

By completing this project, you will be able to:

1. Create and run a .NET MAUI application.
2. Identify key startup files for native and web hosting.
3. Reuse one UI across a MAUI app and an ASP.NET Core web app.
4. Handle button click events that update state and refresh the UI.
5. Compare native app hosting with browser hosting in a simple example.

## Prerequisites

- .NET 10.0 SDK
- MAUI workload installed: `dotnet workload install maui`
- macOS with Xcode tools for Mac Catalyst, and/or Android SDK for Android

No extra browser workload is required for the web version in this project.

## Project Structure

```text
01.HelloWorldMAUI/
├── HelloWorldMAUI.csproj                 # Native MAUI host for iOS, Android, macOS, Windows
├── HelloWorldMAUI.Starter.slnx           # Starter solution for the native, shared, and web projects
├── HelloWorldMAUI.Shared/                # Shared Razor UI, styles, and app state
├── HelloWorldMAUI.Web/                   # ASP.NET Core browser host
├── Makefile                              # Build and run shortcuts for the starter
├── MauiProgram.cs                        # Native app startup and services
├── MainPage.xaml                         # BlazorWebView host page
├── AppShell.xaml                         # Shell navigation container
├── Resources/                            # Images, fonts, styles, splash/icon
├── wwwroot/                              # Native BlazorWebView host assets
├── QUICKSTART.md                         # Build and run instructions
└── docs/
    └── Key-Takeaways.md                  # Core concepts recap
```

## Build And Run

See [QUICKSTART.md](QUICKSTART.md) for the full workflow.

Common commands:

```bash
# From this project folder
cd 12.MauiCrossPlatform/01.HelloWorldMAUI

# Build the browser host
make build-web

# Build the default native host for your current OS
make build-native

# Run the browser host
make run-web
```

## Important Note About `net10.0-browser`

`dotnet build -f net10.0-browser` is not the supported way to run this MAUI sample in a browser.

Why:

- the native MAUI project targets platforms such as Android, iOS, Mac Catalyst, and Windows
- the browser experience is hosted by the separate `HelloWorldMAUI.Web` ASP.NET Core project
- the shared UI lives in `HelloWorldMAUI.Shared`

So the correct browser workflow is:

```bash
dotnet build HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj
dotnet run --project HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj
```

## Libraries Added For Web Support

The native project now includes:

- `Microsoft.AspNetCore.Components.WebView.Maui`

This package enables the shared Razor UI to run inside the MAUI app through `BlazorWebView`.

## Starter Pattern Notes

This repository now follows the starter pattern discussed earlier:

- `HelloWorldMAUI.csproj` is the MAUI host for iOS, Android, macOS, and Windows
- `HelloWorldMAUI.Shared` contains reusable Blazor components and shared app state
- `HelloWorldMAUI.Web` is the browser host

The sample counter lesson now uses a shared `CounterState` service so both native and web hosts consume the same UI logic and app behavior.

## Further Readings

- [Cross-Platform MAUI With Shared .NET Code](docs/cross-platform-maui.nd)
- [BUILDs Guide](docs/BUILDs.md)

## Next Project

After this project, continue to:

- `12.MauiCrossPlatform/02.MvvmDependencyInjection`

You will move logic from simple UI event handling toward MVVM and dependency injection patterns.
