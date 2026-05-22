# BUILDs Guide

This guide explains how to build this starter in two ways:

- using `make`
- using direct `dotnet` command lines

It also distinguishes between:

- macOS commands that were verified in this repository
- Windows and Linux commands that are documented for reference but were **not verified** in this turn

## Verified Platform

The following workflows were verified on **macOS**:

- `make restore`
- `make build-web`
- `make build-maccatalyst`
- `make build`
- `make run-web`
- `dotnet build HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj`
- `dotnet build HelloWorldMAUI.csproj -f net10.0-maccatalyst`

## Project Build Targets

This starter contains three projects:

- `HelloWorldMAUI.csproj`
  Native MAUI host for iOS, Android, macOS, and Windows
- `HelloWorldMAUI.Shared/HelloWorldMAUI.Shared.csproj`
  Shared Blazor UI, styles, and shared app logic/state
- `HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj`
  Browser host for the shared UI

## Build Using `make`

From the project folder:

```bash
cd 12.MauiCrossPlatform/01.HelloWorldMAUI
```

### Show available targets

```bash
make help
```

### Restore all projects

```bash
make restore
```

### Build the browser host

```bash
make build-web
```

### Build the default native host for the current OS

On macOS, this builds the Mac Catalyst target.

```bash
make build-native
```

### Build everything commonly needed on macOS

This builds:

- the shared project through project references
- the web host
- the default native host for the current OS

```bash
make build
```

### Build specific native targets

```bash
make build-maccatalyst
make build-android
make build-ios
make build-windows
```

### Run the browser host

```bash
make run-web
```

### Run native targets

```bash
make run-maccatalyst
make run-android
make run-ios
```

### Clean build outputs

```bash
make clean
```

## Build Without `make`

You can also call `dotnet` directly.

From the project folder:

```bash
cd 12.MauiCrossPlatform/01.HelloWorldMAUI
```

### Restore the starter solution

```bash
dotnet restore HelloWorldMAUI.Starter.slnx
```

### Build the browser host

```bash
dotnet build HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj -v minimal
```

### Build the shared project directly

```bash
dotnet build HelloWorldMAUI.Shared/HelloWorldMAUI.Shared.csproj -v minimal
```

### Build native targets

#### macOS Mac Catalyst

```bash
dotnet build HelloWorldMAUI.csproj -f net10.0-maccatalyst -v minimal
```

#### Android

```bash
dotnet build HelloWorldMAUI.csproj -f net10.0-android -v minimal
```

#### iOS

```bash
dotnet build HelloWorldMAUI.csproj -f net10.0-ios -v minimal
```

#### Windows

```bash
dotnet build HelloWorldMAUI.csproj -f net10.0-windows10.0.19041.0 -v minimal
```

### Run the browser host

```bash
dotnet run --project HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj
```

### Run native targets

#### macOS Mac Catalyst

```bash
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-maccatalyst
```

#### Android

```bash
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-android
```

#### iOS Simulator

```bash
dotnet build HelloWorldMAUI.csproj -t:Run -f net10.0-ios -p:_DeviceName=:v2:udid=$(xcrun simctl list devices booted | awk -F '[()]' '/Booted/{print $2; exit}')
```

## macOS Notes

These notes apply to the current machine and were used during verification:

- `make build` works on macOS because `build-native` maps to `build-maccatalyst`
- `make run-web` starts the browser host on `http://localhost:5000` by default
- iOS run commands assume Xcode and a booted simulator
- Android build/run commands assume the MAUI Android tooling is already configured

## Windows Notes

The following commands are listed for reference but were **not verified** in this turn.

### Using `make`

If GNU Make is available on Windows:

```bash
make restore
make build-web
make build-windows
```

### Using `dotnet`

```bash
dotnet restore HelloWorldMAUI.Starter.slnx
dotnet build HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj -v minimal
dotnet build HelloWorldMAUI.csproj -f net10.0-windows10.0.19041.0 -v minimal
```

Note:

- Windows support depends on having the required .NET MAUI Windows workload and SDK support installed.

## Linux Notes

The following commands are listed for reference but were **not verified** in this turn.

### Browser host on Linux

The web host should be the main Linux-friendly build target:

```bash
dotnet restore HelloWorldMAUI.Starter.slnx
dotnet build HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj -v minimal
dotnet run --project HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj
```

### `make build-native` on Linux

At the moment, the `Makefile` intentionally does **not** define a default native Linux target.

Running this on Linux will fail by design:

```bash
make build-native
```

Why:

- .NET MAUI native targets in this starter are for iOS, Android, macOS, and Windows
- Linux is useful here mainly as a host for restoring and building the browser project

## Important Note About `net10.0-browser`

Do **not** use this command for this repository:

```bash
dotnet build -f net10.0-browser
```

Why:

- `HelloWorldMAUI.csproj` is a MAUI native host, not a browser target
- the browser app is hosted by `HelloWorldMAUI.Web`
- shared UI lives in `HelloWorldMAUI.Shared`

Use these commands instead:

```bash
make build-web
make run-web
```

Or without `make`:

```bash
dotnet build HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj -v minimal
dotnet run --project HelloWorldMAUI.Web/HelloWorldMAUI.Web.csproj
```
