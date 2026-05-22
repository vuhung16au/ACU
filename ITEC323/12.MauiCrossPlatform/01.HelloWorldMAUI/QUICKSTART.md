# QUICKSTART - 01.HelloWorldMAUI

Follow this guide to build and run the native MAUI app and the browser version.

## 1. Verify Prerequisites

```bash
dotnet --version
dotnet workload list
```

Confirm:

- .NET SDK is `10.0.x`
- `maui` workload is installed

If needed:

```bash
dotnet workload install maui
```

## 2. Move Into The Project Folder

```bash
cd 12.MauiCrossPlatform/01.HelloWorldMAUI
```

## 3. Restore The Starter Solution

```bash
make restore
```

This restores:

- `HelloWorldMAUI.csproj`
- `HelloWorldMAUI.Shared/HelloWorldMAUI.Shared.csproj`
- `HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj`

## 4. Build The Browser Version

```bash
make build-web
```

Expected result:

```text
Build succeeded.
```

## 5. Run The Browser Version

```bash
make run-web
```

Then open the local URL shown in the terminal, usually:

```text
http://localhost:5xxx
```

What to test in the browser:

1. The page shows the Hello World MAUI heading.
2. Click `Tap to Count` and confirm the text changes.
3. Click `Reset Counter` and confirm the message resets.

## 6. Build The Native MAUI App

### Mac Catalyst

```bash
make build-maccatalyst
```

### Android

```bash
make build-android
```

### iOS

```bash
make build-ios
```

### Windows

```bash
make build-windows
```

### Default native host for the current OS

```bash
make build-native
```

## 7. Run The Native MAUI App

### Mac Catalyst

```bash
make run-maccatalyst
```

### Android

```bash
make run-android
```

### iOS Simulator

```bash
make run-ios
```

## 8. Understand The Starter Structure

- `HelloWorldMAUI.csproj`: native MAUI host
- `HelloWorldMAUI.Shared/`: shared Razor UI, styles, and shared state
- `HelloWorldMAUI.Web/`: browser host built with ASP.NET Core
- `HelloWorldMAUI.Starter.slnx`: starter solution containing all three projects
- `Makefile`: standard entrypoints for restore, build, and run
- `MainPage.xaml`: hosts the shared UI inside `BlazorWebView`

## 9. Important Note About `dotnet build -f net10.0-browser`

This command does **not** apply to this project:

```bash
dotnet build -f net10.0-browser
```

Reason:

- this project is still a MAUI app for native targets
- the browser experience is provided by the separate `HelloWorldMAUI.Web` project
- the browser workflow uses `dotnet build` and `dotnet run` on that web project instead

Use this instead:

```bash
make build-web
make run-web
```

## Common Issues

### Error: `NETSDK1005` for `net10.0-browser`

Example:

```text
Assets file ... doesn't have a target for 'net10.0-browser'
```

This happens because `HelloWorldMAUI.csproj` does not target `net10.0-browser`.
Use the web host project instead:

```bash
make build-web
make run-web
```

### Error: MAUI workload missing

```bash
dotnet workload install maui
```

### Error: Android emulator/device unavailable

- Start the emulator manually in Android Studio
- Or run the Mac Catalyst target first

### Error: iOS simulator unavailable or not detected

```bash
open -a Simulator
xcrun simctl list devices available
```

If needed, target a specific simulator:

```bash
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=<SIMULATOR_UDID>
```

### Error: Build fails after SDK or package changes

```bash
make clean
make restore
make build-web
make build-native
```

## Success Checklist

- Browser project builds successfully
- Browser version runs locally
- Native MAUI app builds for at least one platform
- Counter button updates the UI
- Reset button restores the default message
- You can explain the role of the native host, shared UI project, and web host
