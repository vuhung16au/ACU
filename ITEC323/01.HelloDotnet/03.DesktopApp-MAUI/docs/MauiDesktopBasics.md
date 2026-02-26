# MAUI Desktop Basics

This document explains the key files and concepts in the `03.DesktopApp-MAUI` project.

## 1. What MAUI does in this project

MAUI lets you define one shared UI and run it as native desktop applications on different platforms.

In this project:
- Shared UI code lives in `MainPage.xaml` and `MainPage.xaml.cs`
- App setup lives in `MauiProgram.cs`
- Platform startup files live in `Platforms/`

## 2. Startup Flow

The app starts in this order:

1. Platform entry point starts (Mac Catalyst or Windows)
2. Platform startup class calls `MauiProgram.CreateMauiApp()`
3. MAUI creates the `App` object (`App.xaml.cs`)
4. `App` sets `MainPage` to `new MainPage()`
5. The desktop window opens and renders `MainPage.xaml`

## 3. Shared UI with XAML

`MainPage.xaml` contains two `Label` controls inside a `VerticalStackLayout`.

This is ideal for beginners because:
- You can see UI structure clearly
- Layout is readable and declarative
- Small changes are easy to test

## 4. Target Frameworks in This App

The project uses desktop target frameworks:

- `net10.0-maccatalyst` for macOS
- `net10.0-windows10.0.19041.0` for Windows

You run one target at a time depending on your operating system.

## 5. First Exercises You Can Try

1. Change the greeting text in `MainPage.xaml`
2. Add a button below the labels
3. Handle the button click in `MainPage.xaml.cs`
4. Add another `Label` that updates after button click

## 6. Key Learning Point

A MAUI app is a combination of:
- Shared cross-platform code
- Platform-specific startup wiring

As a beginner, this project helps you understand that separation before introducing more advanced patterns.
