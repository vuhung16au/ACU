# Week 12: Cross-Platform App Development with .NET MAUI

## Overview
Build native mobile and desktop applications from a single C# codebase using .NET MAUI (Multi-platform App UI). This module teaches you to create apps that run on Android, iOS, macOS, and Windows from one project with shared UI and business logic.

## What You'll Learn
- ✅ .NET MAUI architecture and cross-platform concepts
- ✅ MVVM pattern with .NET Community Toolkit
- ✅ Shell-based URI navigation with routes and parameters
- ✅ Responsive layouts and data-bound collections
- ✅ Hardware integration (camera, GPS, sensors)
- ✅ Theming and styling for light/dark modes
- ✅ Building and running apps on multiple platforms

## Why MAUI?
- **Single Codebase**: Write once, run on Android, iOS, macOS, and Windows
- **Native Performance**: Compiled to native code for each platform
- **Familiar Patterns**: Uses C#, MVVM, and dependency injection from ASP.NET Core
- **Full Platform Access**: Camera, GPS, sensors, file system, and more
- **Modern Tooling**: Integrated with Visual Studio and .NET SDK

## Prerequisites
Before starting this module, you should have:
- ✅ Completed C# fundamentals (Week 3-6)
- ✅ Understanding of MVVM and dependency injection (from ASP.NET Core)
- ✅ .NET 8.0 SDK installed
- ✅ MAUI workload installed (`dotnet workload install maui`)
- ✅ Android emulator or physical device for testing

**Platform Requirements**:
- **macOS**: Best for all targets (Android, iOS, macOS)
- **Windows**: Supports Android and Windows targets
- **Linux**: Limited MAUI support (not recommended for this course)

## Module Structure

### 📂 [01.HelloWorldMAUI](01.HelloWorldMAUI/)
**Introduction to MAUI project structure and XAML**
- Create first MAUI app from template
- Understand project structure (`MauiProgram.cs`, `App.xaml`, `Platforms/`)
- Modify XAML UI with buttons and labels
- Run on macOS or Android

**Key Concepts**: Project structure, XAML basics, code-behind, platform targeting

---

### 📂 [02.MvvmDependencyInjection](02.MvvmDependencyInjection/)
**MVVM pattern with .NET Community Toolkit**
- Implement ViewModels with `ObservableObject`
- Use `[ObservableProperty]` and `[RelayCommand]` attributes
- Data binding from XAML to ViewModel
- Register services and ViewModels in DI container

**Key Concepts**: MVVM, data binding, observable properties, commands, dependency injection

---

### 📂 [03.ShellNavigation](03.ShellNavigation/)
**Multi-page navigation with MAUI Shell**
- Define app structure with `AppShell.xaml`
- URI-based navigation with routes
- Pass parameters between pages
- Tab and flyout navigation patterns

**Key Concepts**: Shell navigation, routing, query properties, hierarchical navigation

---

### 📂 [04.LayoutsAndCollections](04.LayoutsAndCollections/)
**Responsive layouts and data-bound lists**
- Grid, StackLayout for complex UIs
- CollectionView with ItemTemplate
- EmptyView and RefreshView
- Observable collections with data binding

**Key Concepts**: Layouts, CollectionView, data templates, responsive design

---

### 📂 [05.HardwareAndPlatformAPIs](05.HardwareAndPlatformAPIs/)
**Access device hardware from cross-platform code**
- Camera and photo picker (MediaPicker)
- GPS and geolocation
- File system APIs
- Permissions handling

**Key Concepts**: Platform integration, hardware APIs, permissions, cross-platform abstractions

---

### 📂 [06.ComprehensiveTaskListApp](06.ComprehensiveTaskListApp/)
**Complete task management application**
- Full MVVM architecture with CRUD operations
- Shell navigation between list and detail pages
- Local data persistence (Preferences or SQLite)
- Hardware integration (optional photo attachments)
- Theming and styling

**Key Concepts**: Full-stack MAUI app, data persistence, complete user workflows

---

## Technology Stack

| Technology | Purpose | Documentation |
|------------|---------|---------------|
| **.NET 8.0 (LTS)** | Runtime and SDK | [Microsoft Docs](https://learn.microsoft.com/en-us/dotnet/maui/) |
| **C# 12** | Programming language | [C# Guide](https://learn.microsoft.com/en-us/dotnet/csharp/) |
| **.NET MAUI** | Cross-platform UI framework | [MAUI Docs](https://learn.microsoft.com/en-us/dotnet/maui/) |
| **XAML** | UI markup language | [XAML Overview](https://learn.microsoft.com/en-us/dotnet/maui/xaml/) |
| **Community Toolkit** | MVVM helpers | [Toolkit MVVM](https://learn.microsoft.com/en-us/dotnet/communitytoolkit/mvvm/) |
| **Android SDK** | Android platform support | [Android Developer](https://developer.android.com/) |

## Quick Start

### 1. Install MAUI Workload
```bash
# Install MAUI workload for .NET SDK
dotnet workload install maui

# Verify installation
dotnet workload list
```

### 2. Set Up Android Emulator (macOS/Windows)
```bash
# On macOS with Android Studio installed
# Open Android Studio → Tools → AVD Manager → Create Virtual Device
# Or use command line:
sdkmanager "platform-tools" "platforms;android-34"
sdkmanager "system-images;android-34;google_apis;arm64-v8a"
avdmanager create avd -n Pixel5 -k "system-images;android-34;google_apis;arm64-v8a"
```

### 3. Create Your First MAUI App
```bash
# Create new MAUI app
dotnet new maui -n MyFirstMauiApp
cd MyFirstMauiApp

# Build the app
dotnet build

# Run on macOS
dotnet build -t:Run -f net8.0-maccatalyst

# Run on Android emulator
dotnet build -t:Run -f net8.0-android
```

## Development Environment Setup

### Option 1: Visual Studio 2022 (Windows/Mac)
- Install ".NET Multi-platform App UI development" workload
- Built-in Android emulator manager
- Best IDE experience for MAUI development

### Option 2: VS Code (Cross-platform)
- Install **C# Dev Kit** extension
- Install **.NET MAUI** extension
- Use integrated terminal for `dotnet` commands

### Option 3: Command Line
- Use `dotnet CLI` for all operations
- Lightweight and fast
- Good for CI/CD pipelines

## Target Platforms

| Platform | Framework Moniker | Run Command |
|----------|-------------------|-------------|
| **Android** | `net8.0-android` | `dotnet build -t:Run -f net8.0-android` |
| **iOS** | `net8.0-ios` | `dotnet build -t:Run -f net8.0-ios` |
| **macOS** | `net8.0-maccatalyst` | `dotnet build -t:Run -f net8.0-maccatalyst` |
| **Windows** | `net8.0-windows10.0.19041.0` | `dotnet build -t:Run -f net8.0-windows10.0.19041.0` |

## Common Commands

```bash
# Build for all platforms
dotnet build

# Build for specific platform
dotnet build -f net8.0-android

# Run on Android
dotnet build -t:Run -f net8.0-android

# Clean build artifacts
dotnet clean

# Restore NuGet packages
dotnet restore

# List connected devices
dotnet build -t:ListDevices
```

## Project Structure Explained

```
MyMauiApp/
├── MauiProgram.cs          # App startup and DI configuration
├── App.xaml                # Global app resources and styling
├── App.xaml.cs             # App lifecycle events
├── AppShell.xaml           # Shell navigation structure
├── MainPage.xaml           # Default main UI page
├── MainPage.xaml.cs        # Main page code-behind
├── Platforms/              # Platform-specific code
│   ├── Android/            # Android initialization
│   ├── iOS/                # iOS initialization
│   ├── MacCatalyst/        # macOS initialization
│   └── Windows/            # Windows initialization
├── Resources/              # App resources
│   ├── Images/             # Image assets
│   ├── Fonts/              # Custom fonts
│   ├── Styles/             # Global styles and colors
│   └── AppIcon/            # App icon assets
└── ViewModels/             # MVVM ViewModels (add your own)
```

## Documentation
- 📄 **[FRD.md](FRD.md)**: Functional requirements for all projects
- 📄 **[QUICKSTART.md](QUICKSTART.md)**: Detailed setup and installation guide
- 📄 **[FRD-Copilot.md](FRD-Copilot.md)**: AI agent development guidelines
- 📂 **[docs/](docs/)**: Additional technical documentation

## Learning Path
1. **Week 1**: Start with [01.HelloWorldMAUI](01.HelloWorldMAUI/) to understand basics
2. **Week 2**: Build [02.MvvmDependencyInjection](02.MvvmDependencyInjection/) for MVVM patterns
3. **Week 3**: Implement [03.ShellNavigation](03.ShellNavigation/) for multi-page apps
4. **Week 4**: Practice with [04.LayoutsAndCollections](04.LayoutsAndCollections/)
5. **Week 5**: Explore [05.HardwareAndPlatformAPIs](05.HardwareAndPlatformAPIs/)
6. **Final Project**: Complete [06.ComprehensiveTaskListApp](06.ComprehensiveTaskListApp/)

## Troubleshooting

### Build Errors
```bash
# Clear all build outputs
dotnet clean

# Restore NuGet packages
dotnet restore

# Rebuild
dotnet build
```

### Android Emulator Not Starting
- Ensure Android SDK is installed: `sdkmanager --list`
- Check emulator is created: `avdmanager list avd`
- Start emulator manually: `emulator -avd Pixel5`

### macOS Target Not Available
- Install Xcode from App Store
- Accept Xcode license: `sudo xcodebuild -license accept`
- Install command line tools: `xcode-select --install`

### Missing MAUI Workload
```bash
# Reinstall MAUI workload
dotnet workload install maui --source https://api.nuget.org/v3/index.json
```

## Additional Resources
- [.NET MAUI Official Docs](https://learn.microsoft.com/en-us/dotnet/maui/)
- [.NET MAUI GitHub Repository](https://github.com/dotnet/maui)
- [Community Toolkit MVVM](https://learn.microsoft.com/en-us/dotnet/communitytoolkit/mvvm/)
- [MAUI Samples on GitHub](https://github.com/dotnet/maui-samples)
- [James Montemagno's MAUI Tutorials](https://www.youtube.com/@JamesMontemagno)

## Assessment
Students will be assessed on:
- Building a functional cross-platform MAUI app
- Proper implementation of MVVM with data binding
- Use of Shell navigation with parameters
- Integration of platform APIs (camera, location)
- Code quality, documentation, and project structure

## Need Help?
- Check project-specific README files in each subfolder
- Review [QUICKSTART.md](QUICKSTART.md) for setup issues
- Consult [FRD.md](FRD.md) for detailed requirements
- Refer to Microsoft documentation for MAUI-specific questions

