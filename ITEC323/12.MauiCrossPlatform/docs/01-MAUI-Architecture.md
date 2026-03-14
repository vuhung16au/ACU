# .NET MAUI Architecture

## What is .NET MAUI?

.NET Multi-platform App UI (MAUI) is a framework for building native mobile and desktop apps from a single C# codebase. It's the successor to Xamarin.Forms with a modernized architecture.

**Target Platforms**:
- Android (API 21+)
- iOS (10+)
- macOS (Mac Catalyst)
- Windows (WinUI 3)

## MAUI vs Xamarin.Forms

| Feature | Xamarin.Forms | .NET MAUI |
|---------|---------------|-----------|
| Project Structure | Separate projects per platform | Single project, multi-targeted |
| .NET Version | .NET Framework / Mono | .NET 6/7/8+ |
| Startup | Platform-specific Main | Unified `MauiProgram.cs` |
| Performance | Good | Better (native AOT) |
| Handler Architecture | Custom renderers | Modern handlers |

## Architecture Overview

```
┌─────────────────────────────────────────────────┐
│           Your MAUI Application                 │
│  (XAML, C#, ViewModels, Models, Services)      │
└────────────────┬────────────────────────────────┘
                 │
        ┌────────┴────────┐
        │  .NET MAUI APIs │
        │   (Controls,    │
        │   Layouts, etc) │
        └────────┬────────┘
                 │
    ┌────────────┴─────────────┐
    │  Platform Abstractions   │
    │  (Handlers, Essentials)  │
    └────────────┬─────────────┘
                 │
    ┌────────────┴────────────────────┐
    │  Native Platform SDKs           │
    ├──────────┬──────────┬───────────┤
    │ Android  │   iOS    │  Windows  │
    │   SDK    │   UIKit  │   WinUI   │
    └──────────┴──────────┴───────────┘
```

## Single-Project Structure

Unlike Xamarin.Forms (multiple projects), MAUI uses **one project** that targets multiple platforms:

```
MyMauiApp/
├── MyMauiApp.csproj        # Multi-targeted project file
├── MauiProgram.cs          # App startup and configuration
├── App.xaml                # Global resources
├── AppShell.xaml           # Shell structure
├── MainPage.xaml           # Pages (shared across platforms)
├── Platforms/              # Platform-specific code
│   ├── Android/
│   │   ├── MainActivity.cs
│   │   └── AndroidManifest.xml
│   ├── iOS/
│   │   └── Info.plist
│   ├── MacCatalyst/
│   └── Windows/
│       └── app.manifest
├── Resources/              # Shared resources
│   ├── Images/
│   ├── Fonts/
│   └── Styles/
└── ViewModels/             # Your code (shared)
```

## How MAUI Renders Native Controls

MAUI controls map to native platform controls via **Handlers**:

| MAUI Control | Android | iOS | Windows |
|--------------|---------|-----|---------|
| `Button` | `AppCompatButton` | `UIButton` | `Button` |
| `Entry` | `AppCompatEditText` | `UITextField` | `TextBox` |
| `Label` | `AppCompatTextView` | `UILabel` | `TextBlock` |
| `Image` | `ImageView` | `UIImageView` | `Image` |

**Result**: Native performance and look-and-feel on each platform.

## Key Components

### 1. MauiProgram.cs
**Purpose**: App startup, dependency injection, configuration

```csharp
public static class MauiProgram
{
    public static MauiApp CreateMauiApp()
    {
        var builder = MauiApp.CreateBuilder();
        builder
            .UseMauiApp<App>()
            .ConfigureFonts(fonts =>
            {
                fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
            });

        // Register services
        builder.Services.AddSingleton<IDataService, DataService>();
        builder.Services.AddTransient<MainViewModel>();

        return builder.Build();
    }
}
```

### 2. App.xaml / App.xaml.cs
**Purpose**: Global resources, app lifecycle events

```csharp
public partial class App : Application
{
    public App()
    {
        InitializeComponent();
        MainPage = new AppShell(); // Set initial page
    }

    protected override void OnStart()
    {
        // Called when app starts
    }

    protected override void OnSleep()
    {
        // Called when app goes to background
    }

    protected override void OnResume()
    {
        // Called when app returns from background
    }
}
```

### 3. AppShell.xaml
**Purpose**: Navigation structure (tabs, flyout, routes)

```xml
<Shell xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
       x:Class="MyApp.AppShell">
    
    <TabBar>
        <ShellContent Title="Home" 
                      Icon="home.png"
                      ContentTemplate="{DataTemplate local:MainPage}" />
        <ShellContent Title="Settings" 
                      Icon="settings.png"
                      ContentTemplate="{DataTemplate local:SettingsPage}" />
    </TabBar>
</Shell>
```

## Platform-Specific vs Shared Code

### Shared Code (90%+)
- UI (XAML pages)
- Business logic (ViewModels, Services)
- Data models
- Cross-platform APIs (camera, GPS via MAUI Essentials)

### Platform-Specific Code (~10%)
- App initialization (`MainActivity.cs`, etc.)
- Platform permissions
- Native API calls not abstracted by MAUI
- Platform-specific UI customizations

**Example: Conditional Compilation**
```csharp
public string GetPlatformName()
{
#if ANDROID
    return "Android";
#elif IOS
    return "iOS";
#elif WINDOWS
    return "Windows";
#elif MACCATALYST
    return "macOS";
#else
    return "Unknown";
#endif
}
```

## Handler Architecture

MAUI uses **Handlers** instead of Xamarin.Forms Renderers for better performance:

```csharp
// Custom button handler example
public class CustomButtonHandler : ButtonHandler
{
    protected override void ConnectHandler(Android.Widget.Button platformView)
    {
        base.ConnectHandler(platformView);
        // Customize Android button
        platformView.SetAllCaps(false);
    }
}
```

## Build Process

1. **Compile C# code** → IL (Intermediate Language)
2. **Platform-specific compilation**:
   - **Android**: IL → Native (AOT or JIT)
   - **iOS**: IL → Native (AOT only, Apple requirement)
   - **Windows**: IL → Native JIT
3. **Bundle resources** (images, fonts)
4. **Package**: APK (Android), IPA (iOS), MSIX (Windows)

## Target Framework Monikers

When building, specify platform:

```bash
# Build for Android
dotnet build -f net10.0-android

# Build for iOS
dotnet build -f net10.0-ios

# Build for macOS
dotnet build -f net10.0-maccatalyst

# Build for Windows
dotnet build -f net10.0-windows10.0.19041.0
```

## Why Single Project?

**Benefits**:
- ✅ Simpler project management
- ✅ Shared resources across platforms
- ✅ Easier updates and maintenance
- ✅ Single NuGet package restore
- ✅ Consistent build configuration

**Previous (Xamarin.Forms)**: 4-5 projects  
**Now (MAUI)**: 1 project

## Key Takeaways

- MAUI = one codebase, native performance on all platforms
- Single project structure simplifies development
- Handlers map MAUI controls to native platform controls
- 90%+ code is shared, ~10% platform-specific
- Unified startup with `MauiProgram.cs` (similar to ASP.NET Core)

## Next Steps

- Learn [MVVM Pattern](02-MVVM-Pattern.md) for structuring your app
- Understand [Data Binding](03-Data-Binding.md) to connect UI to data
- Explore [Shell Navigation](04-Shell-Navigation.md) for multi-page apps
