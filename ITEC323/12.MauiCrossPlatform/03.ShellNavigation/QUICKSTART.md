# QUICKSTART - 03.ShellNavigation

Follow this guide to build and run the Shell navigation MAUI app.

## 1. Verify Prerequisites

```bash
dotnet --version
dotnet workload list
```

Confirm:

- .NET SDK is 10.0 or higher
- maui workload is installed

If needed:

```bash
dotnet workload install maui
```

## 2. Build The Project

From repository root:

```bash
cd 12.MauiCrossPlatform/03.ShellNavigation

# Optional recovery sequence if you had prior build problems
# dotnet clean
# dotnet restore

# Build for macOS (Mac Catalyst)
dotnet build -f net10.0-maccatalyst

# Build for Android
dotnet build -f net10.0-android

# Build all available targets
dotnet build
```

Expected result:

```text
Build succeeded.
```

## 3. Run On macOS (Mac Catalyst)

```bash
# From project folder
dotnet build -t:Run -f net10.0-maccatalyst

# Or from repository root
dotnet build -t:Run 12.MauiCrossPlatform/03.ShellNavigation/ShellNavigation.csproj -f net10.0-maccatalyst
```

## 4. Run On Android (Optional)

Start an Android emulator first, then run:

```bash

# From project folder
cd 03.ShellNavigation
dotnet build -t:Run  -f net10.0-android

# Or from repository root
# dotnet build -t:Run 12.MauiCrossPlatform/03.ShellNavigation/ShellNavigation.csproj -f net10.0-android
```

## 5. Explore Important Files

- AppShell.xaml.cs: Shell hierarchy and route registration
- AppRoutes.cs: Central route constants
- Views/HomePage.xaml: List route source page
- ViewModels/DetailViewModel.cs: [QueryProperty] route parameter receiver
- ViewModels/EditViewModel.cs: Two-way editing and save/back navigation

## Common Issues

### Error: MAUI workload missing

```bash
dotnet workload install maui
```

### Error: Route not found at runtime

- Confirm routes are registered in AppShell.xaml.cs
- Confirm GoToAsync uses exact route names from AppRoutes

### Error XA0010: No available device

This means no Android emulator/physical device is visible to ADB.

1. Ensure SDK tools are on PATH (zsh):

```bash
echo 'export ANDROID_HOME="$HOME/Library/Android/sdk"' >> ~/.zshrc
echo 'export ANDROID_SDK_ROOT="$ANDROID_HOME"' >> ~/.zshrc
echo 'export PATH="$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator"' >> ~/.zshrc
source ~/.zshrc
```

2. Verify tools and devices:

```bash
which adb
which emulator
adb devices -l
emulator -list-avds
```

3. Start an emulator if needed:

```bash
emulator -avd <YourAvdName>
```

4. Re-run Android app:

```bash
dotnet build -t:Run 12.MauiCrossPlatform/03.ShellNavigation/ShellNavigation.csproj -f net10.0-android
```

### Error: Build fails after SDK or workload changes

```bash
dotnet clean 12.MauiCrossPlatform/03.ShellNavigation/ShellNavigation.csproj
dotnet restore 12.MauiCrossPlatform/03.ShellNavigation/ShellNavigation.csproj
dotnet build 12.MauiCrossPlatform/03.ShellNavigation/ShellNavigation.csproj -f net10.0-maccatalyst
```

## Success Checklist

- Project builds successfully
- App runs on at least one platform
- List -> Detail -> Edit route flow works
- taskId query parameter is received in detail/edit view models
- Back navigation with .. returns to the previous page
