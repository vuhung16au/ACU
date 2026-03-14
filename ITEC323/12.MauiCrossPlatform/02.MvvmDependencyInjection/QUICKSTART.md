# QUICKSTART - 02.MvvmDependencyInjection

Follow this guide to build and run the MVVM + DI MAUI app.

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
cd 12.MauiCrossPlatform/02.MvvmDependencyInjection

# Clean and restore first if you had build issues
# dotnet clean
# dotnet restore

# Build for macOS (Mac Catalyst)
dotnet build -f net10.0-maccatalyst

# Build for Android
dotnet build -f net10.0-android 

# Build all targets enabled on your machine
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
dotnet build -t:Run --project 12.MauiCrossPlatform/02.MvvmDependencyInjection/MvvmDependencyInjection.csproj -f net10.0-maccatalyst
```

## 4. Run On Android (Optional)

Start an Android emulator first, then run:

```bash
dotnet build -t:Run --project 12.MauiCrossPlatform/02.MvvmDependencyInjection/MvvmDependencyInjection.csproj -f net10.0-android
```

## 5. Explore Important Files

- MauiProgram.cs: Dependency injection registrations
- Views/MainPage.xaml: Binding expressions and command bindings
- ViewModels/MainViewModel.cs: Observable properties and relay commands
- Services/WelcomeMessageService.cs: Business logic service used by ViewModel
- App.xaml.cs and AppShell.xaml.cs: DI-driven app and shell composition

## Common Issues

### Error: MAUI workload missing

```bash
dotnet workload install maui
```

### Error: Android emulator/device unavailable

- Start emulator from Android Studio
- Or run Mac Catalyst target first

### Error: Build fails after SDK or workload changes

```bash
dotnet clean 12.MauiCrossPlatform/02.MvvmDependencyInjection/MvvmDependencyInjection.csproj
dotnet restore 12.MauiCrossPlatform/02.MvvmDependencyInjection/MvvmDependencyInjection.csproj
dotnet build 12.MauiCrossPlatform/02.MvvmDependencyInjection/MvvmDependencyInjection.csproj -f net10.0-maccatalyst
```

## Success Checklist

- Project builds successfully
- App runs on at least one platform
- Entry updates ViewModel name via two-way binding
- Increment and Reset execute ViewModel relay commands
- You can explain why DI + MVVM reduces code-behind responsibilities
