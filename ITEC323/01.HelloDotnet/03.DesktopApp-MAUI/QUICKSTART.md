# Quick Start Guide - Hello World MAUI Desktop App

This guide walks you through building and running your first .NET MAUI desktop application step by step.

## Prerequisites Check

Before you begin, verify your .NET SDK:

```bash
dotnet --version
```

Expected output: `10.0.xxx` (or newer)

## Step 1: Install MAUI Workload

Install MAUI templates and build tools:

```bash
sudo dotnet workload install maui
```

What this does: installs MAUI tooling required to compile desktop/mobile MAUI projects.

## Step 2: Navigate to the Project Directory

From the repository root:

```bash
cd 01.HelloDotnet/03.DesktopApp-MAUI
```

## Step 3: Restore Dependencies

```bash
dotnet restore
```

Expected output includes:

```
Determining projects to restore...
Restored .../HelloWorldMaui.csproj
```

## Step 4: Build the Desktop App

### macOS (Mac Catalyst)

```bash
dotnet build -f net10.0-maccatalyst
```

### Windows (WinUI)

```bash
dotnet build -f net10.0-windows10.0.19041.0
```

Expected output:

```
Build succeeded.
    0 Warning(s)
    0 Error(s)
```

## Step 5: Run the Desktop App

Run the command matching your operating system.

### macOS (Mac Catalyst)

```bash
dotnet build -t:Run -f net10.0-maccatalyst
```

### Windows (WinUI)

```bash
dotnet build -t:Run -f net10.0-windows10.0.19041.0
```

What you should see:
- A native desktop window opens
- The window shows:
  - `Hello, .NET 10 Desktop World!`
  - `Welcome to my first .NET 10 MAUI app.`

## Step 6: Stop the App

Close the desktop window normally.

If running from terminal and needed:
- Press `Ctrl + C` to stop the active run command

## Verification Checklist

- [ ] `dotnet workload install maui` completed successfully
- [ ] Project restores with `dotnet restore`
- [ ] Project builds with your platform target framework
- [ ] Desktop window opens and shows both labels

## Common Issues and Solutions

### Issue 1: "No templates or subcommands found matching: maui"

Cause: MAUI workload is missing.

Solution:

```bash
dotnet workload install maui
```

### Issue 2: Target framework not supported

Cause: SDK too old for `net10.0-*` target frameworks.

Solution:
1. Install .NET 10 SDK
2. Confirm with `dotnet --version`

### Issue 3: Platform mismatch

Cause: Running the wrong target framework for your OS.

Solutions:
- On macOS, use `net10.0-maccatalyst`
- On Windows, use `net10.0-windows10.0.19041.0`

## Create This Project From Scratch (Reference)

```bash
# 1) Install MAUI workload
dotnet workload install maui

# 2) Create project from template
dotnet new maui -n HelloWorldMaui
cd HelloWorldMaui

# 3) Run on desktop platform
dotnet build -t:Run -f net10.0-maccatalyst            # macOS
dotnet build -t:Run -f net10.0-windows10.0.19041.0    # Windows
```

---

For deeper explanations, see [docs/MauiDesktopBasics.md](docs/MauiDesktopBasics.md).
