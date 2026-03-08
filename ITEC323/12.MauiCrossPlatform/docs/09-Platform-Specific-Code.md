# Platform-Specific Code in .NET MAUI

## Why Platform-Specific Code?

While MAUI provides cross-platform APIs, sometimes you need:
- **Features not in MAUI**: Platform-unique functionality
- **Custom UI**: Native controls or behaviors
- **Performance**: Direct platform API access
- **Third-party SDKs**: Native-only libraries

## Four Approaches

1. **Conditional Compilation** - Simplest, for small differences
2. **Partial Classes** - Organized, for medium complexity
3. **Dependency Injection** - Best practice, fully testable
4. **Platform Folders** - Auto-compiled per platform

## 1. Conditional Compilation

Use `#if` directives for platform-specific code:

```csharp
public class DeviceService
{
    public string GetDeviceModel()
    {
#if ANDROID
        return Android.OS.Build.Model;
#elif IOS
        return UIKit.UIDevice.CurrentDevice.Model;
#elif WINDOWS
        return "Windows Device";
#elif MACCATALYST
        return "Mac";
#else
        return "Unknown";
#endif
    }
}
```

### Available Symbols
- `ANDROID` - Android platform
- `IOS` - iOS platform
- `MACCATALYST` - macOS via Mac Catalyst
- `WINDOWS` - Windows platform
- `TIZEN` - Tizen platform

### Example: Platform-Specific Path Separator
```csharp
public string GetPath()
{
#if ANDROID || IOS
    return "/data/app/files";
#elif WINDOWS
    return "C:\\App\\Data";
#else
    return string.Empty;
#endif
}
```

### Example: Status Bar Color (Android)
```csharp
public void SetStatusBarColor(Color color)
{
#if ANDROID
    var activity = Platform.CurrentActivity;
    if (activity?.Window != null)
    {
        activity.Window.SetStatusBarColor(color.ToPlatform());
        activity.Window.SetNavigationBarColor(color.ToPlatform());
    }
#endif
}
```

**When to use**: Simple, small differences; quick platform checks.

## 2. Partial Classes

Split implementation across platform-specific files:

### Shared Code (DeviceService.cs)
```csharp
namespace MyApp.Services;

public partial class DeviceService
{
    // Shared method
    public string GetInfo()
    {
        return $"Model: {GetDeviceModel()}, Version: {GetOSVersion()}";
    }
    
    // Platform-specific methods (implemented in platform folders)
    public partial string GetDeviceModel();
    public partial string GetOSVersion();
}
```

### Android (Platforms/Android/DeviceService.cs)
```csharp
namespace MyApp.Services;

public partial class DeviceService
{
    public partial string GetDeviceModel()
    {
        return Android.OS.Build.Model;
    }
    
    public partial string GetOSVersion()
    {
        return Android.OS.Build.VERSION.Release;
    }
}
```

### iOS (Platforms/iOS/DeviceService.cs)
```csharp
namespace MyApp.Services;

public partial class DeviceService
{
    public partial string GetDeviceModel()
    {
        return UIKit.UIDevice.CurrentDevice.Model;
    }
    
    public partial string GetOSVersion()
    {
        return UIKit.UIDevice.CurrentDevice.SystemVersion;
    }
}
```

**When to use**: Multiple platform-specific methods; moderate complexity.

## 3. Dependency Injection (Recommended)

Define an interface in shared code, implement per platform:

### Shared Interface (IDeviceService.cs)
```csharp
namespace MyApp.Services;

public interface IDeviceService
{
    string GetDeviceModel();
    string GetOSVersion();
    void Vibrate(int milliseconds);
}
```

### Android Implementation (Platforms/Android/DeviceService.cs)
```csharp
using Android.OS;
using Android.Content;

namespace MyApp.Services;

public class DeviceService : IDeviceService
{
    public string GetDeviceModel()
    {
        return Build.Model;
    }
    
    public string GetOSVersion()
    {
        return Build.VERSION.Release;
    }
    
    public void Vibrate(int milliseconds)
    {
        var vibrator = (Vibrator)Android.App.Application.Context
            .GetSystemService(Context.VibratorService);
        
        if (Build.VERSION.SdkInt >= BuildVersionCodes.O)
        {
            vibrator.Vibrate(VibrationEffect.CreateOneShot(milliseconds, 
                VibrationEffect.DefaultAmplitude));
        }
        else
        {
            vibrator.Vibrate(milliseconds);
        }
    }
}
```

### iOS Implementation (Platforms/iOS/DeviceService.cs)
```csharp
using UIKit;
using AudioToolbox;

namespace MyApp.Services;

public class DeviceService : IDeviceService
{
    public string GetDeviceModel()
    {
        return UIDevice.CurrentDevice.Model;
    }
    
    public string GetOSVersion()
    {
        return UIDevice.CurrentDevice.SystemVersion;
    }
    
    public void Vibrate(int milliseconds)
    {
        SystemSound.Vibrate.PlaySystemSound();
    }
}
```

### Registration (MauiProgram.cs)
```csharp
public static class MauiProgram
{
    public static MauiApp CreateMauiApp()
    {
        var builder = MauiApp.CreateBuilder();
        builder
            .UseMauiApp<App>();
        
        // Register platform-specific service
#if ANDROID
        builder.Services.AddSingleton<IDeviceService, Platforms.Android.DeviceService>();
#elif IOS
        builder.Services.AddSingleton<IDeviceService, Platforms.iOS.DeviceService>();
#elif WINDOWS
        builder.Services.AddSingleton<IDeviceService, Platforms.Windows.DeviceService>();
#endif
        
        return builder.Build();
    }
}
```

### Usage in ViewModel
```csharp
public partial class MainViewModel : ObservableObject
{
    private readonly IDeviceService _deviceService;
    
    public MainViewModel(IDeviceService deviceService)
    {
        _deviceService = deviceService;
    }
    
    [RelayCommand]
    private void ShowDeviceInfo()
    {
        var model = _deviceService.GetDeviceModel();
        var version = _deviceService.GetOSVersion();
        Shell.Current.DisplayAlert("Device", $"{model} - {version}", "OK");
    }
    
    [RelayCommand]
    private void VibrateDevice()
    {
        _deviceService.Vibrate(500);
    }
}
```

**When to use**: Complex platform code; testable architecture (best practice).

## 4. Platform Folders

Files in `Platforms/` folders are auto-compiled only for that platform:

```
MyApp/
├── Services/
│   └── INotificationService.cs
└── Platforms/
    ├── Android/
    │   └── NotificationService.cs    # Android only
    ├── iOS/
    │   └── NotificationService.cs    # iOS only
    └── Windows/
        └── NotificationService.cs    # Windows only
```

Auto-included, no `#if` needed!

**When to use**: Large platform-specific files; native SDK integration.

## Runtime Platform Detection

Check platform at runtime:

```csharp
if (DeviceInfo.Platform == DevicePlatform.Android)
{
    // Android-specific code
}
else if (DeviceInfo.Platform == DevicePlatform.iOS)
{
    // iOS-specific code
}
else if (DeviceInfo.Platform == DevicePlatform.WinUI)
{
    // Windows-specific code
}
else if (DeviceInfo.Platform == DevicePlatform.MacCatalyst)
{
    // macOS-specific code
}
```

**Check device type**:
```csharp
if (DeviceInfo.Idiom == DeviceIdiom.Phone)
{
    // Phone UI
}
else if (DeviceInfo.Idiom == DeviceIdiom.Tablet)
{
    // Tablet UI
}
else if (DeviceInfo.Idiom == DeviceIdiom.Desktop)
{
    // Desktop UI
}
```

## Example: Toast Notification Service

### Interface (Shared)
```csharp
public interface IToastService
{
    void ShowToast(string message);
}
```

### Android Implementation
```csharp
using Android.Widget;
using Android.App;

namespace MyApp.Platforms.Android;

public class ToastService : IToastService
{
    public void ShowToast(string message)
    {
        Toast.MakeText(Application.Context, message, ToastLength.Short)?.Show();
    }
}
```

### iOS Implementation
```csharp
using UIKit;

namespace MyApp.Platforms.iOS;

public class ToastService : IToastService
{
    public void ShowToast(string message)
    {
        var alert = UIAlertController.Create(null, message, UIAlertControllerStyle.Alert);
        
        var window = UIApplication.SharedApplication.KeyWindow;
        var viewController = window.RootViewController;
        
        viewController?.PresentViewController(alert, true, null);
        
        // Auto-dismiss after 2 seconds
        Task.Delay(2000).ContinueWith(_ =>
        {
            alert.DismissViewController(true, null);
        }, TaskScheduler.FromCurrentSynchronizationContext());
    }
}
```

### Windows Implementation
```csharp
using Microsoft.UI.Xaml.Controls;

namespace MyApp.Platforms.Windows;

public class ToastService : IToastService
{
    public void ShowToast(string message)
    {
        var infoBar = new InfoBar
        {
            Message = message,
            Severity = InfoBarSeverity.Informational,
            IsOpen = true
        };
        
        // Add to UI (simplified)
        // In real app, add to page content
    }
}
```

### Registration
```csharp
#if ANDROID
builder.Services.AddSingleton<IToastService, Platforms.Android.ToastService>();
#elif IOS
builder.Services.AddSingleton<IToastService, Platforms.iOS.ToastService>();
#elif WINDOWS
builder.Services.AddSingleton<IToastService, Platforms.Windows.ToastService>();
#endif
```

## Example: Custom Status Bar

### Android (MainActivity.cs)
```csharp
using Android.OS;
using Microsoft.Maui;

namespace MyApp;

[Activity(Theme = "@style/Maui.SplashTheme", MainLauncher = true)]
public class MainActivity : MauiAppCompatActivity
{
    protected override void OnCreate(Bundle savedInstanceState)
    {
        base.OnCreate(savedInstanceState);
        
        // Set status bar color
        if (Build.VERSION.SdkInt >= BuildVersionCodes.Lollipop)
        {
            Window.SetStatusBarColor(Android.Graphics.Color.ParseColor("#007bff"));
        }
    }
}
```

### iOS (AppDelegate.cs)
```csharp
using UIKit;

namespace MyApp;

[Register("AppDelegate")]
public class AppDelegate : MauiUIApplicationDelegate
{
    protected override MauiApp CreateMauiApp() => MauiProgram.CreateMauiApp();
    
    public override bool FinishedLaunching(UIApplication app, NSDictionary options)
    {
        // Set status bar style
        UIApplication.SharedApplication.SetStatusBarStyle(UIStatusBarStyle.LightContent, false);
        
        return base.FinishedLaunching(app, options);
    }
}
```

## When to Use Each Approach

| Approach | Use When | Pros | Cons |
|----------|----------|------|------|
| **Conditional Compilation** | Small differences, quick checks | Simple, no extra files | Hard to test, messy if overused |
| **Partial Classes** | Medium complexity, organized code | Clean separation | Still compile-time only |
| **Dependency Injection** | Complex logic, testable code | Fully testable, clean | More setup required |
| **Platform Folders** | Large files, native SDKs | Auto-included, organized | Need to manage multiple files |

## Best Practices

1. ✅ **Prefer DI**: Use dependency injection for testable, clean code
2. ✅ **Keep interfaces simple**: Don't expose platform-specific types
3. ✅ **Document platform differences**: Comment why platform code differs
4. ✅ **Test on all platforms**: Ensure implementations work consistently
5. ✅ **Minimize platform code**: Use MAUI APIs when possible
6. ✅ **Use Platform folders**: For large platform-specific files

## Common Mistakes

❌ **Exposing platform types in interface**
```csharp
// Bad - UIImage is iOS-only
public interface IImageService
{
    UIImage GetImage();  // Won't compile on Android!
}

// Good - Use shared types
public interface IImageService
{
    byte[] GetImageData();
}
```

❌ **Forgetting to register platform services**
```csharp
// Missing platform-specific registration
builder.Services.AddSingleton<IDeviceService, DeviceService>();  // Which DeviceService?
```

❌ **Overusing conditional compilation**
```csharp
// Too much platform code in one method
public void DoSomething()
{
#if ANDROID
    // 50 lines of Android code
#elif IOS
    // 50 lines of iOS code
#elif WINDOWS
    // 50 lines of Windows code
#endif
}

// Better: Use DI with separate implementations
```

## Complete Example: Camera Service

### Interface
```csharp
public interface ICameraService
{
    Task<byte[]> TakePictureAsync();
    bool IsCameraAvailable();
}
```

### Android Implementation
```csharp
using Android.Content;

namespace MyApp.Platforms.Android;

public class CameraService : ICameraService
{
    public bool IsCameraAvailable()
    {
        var pm = Application.Context.PackageManager;
        return pm.HasSystemFeature(PackageManager.FeatureCamera);
    }
    
    public async Task<byte[]> TakePictureAsync()
    {
        // Android camera implementation
        var photo = await MediaPicker.Default.CapturePhotoAsync();
        if (photo != null)
        {
            using var stream = await photo.OpenReadAsync();
            using var ms = new MemoryStream();
            await stream.CopyToAsync(ms);
            return ms.ToArray();
        }
        return null;
    }
}
```

### iOS Implementation
```csharp
using UIKit;

namespace MyApp.Platforms.iOS;

public class CameraService : ICameraService
{
    public bool IsCameraAvailable()
    {
        return UIImagePickerController.IsSourceTypeAvailable(
            UIImagePickerControllerSourceType.Camera);
    }
    
    public async Task<byte[]> TakePictureAsync()
    {
        // iOS camera implementation
        var photo = await MediaPicker.Default.CapturePhotoAsync();
        if (photo != null)
        {
            using var stream = await photo.OpenReadAsync();
            using var ms = new MemoryStream();
            await stream.CopyToAsync(ms);
            return ms.ToArray();
        }
        return null;
    }
}
```

### ViewModel
```csharp
public partial class CameraViewModel : ObservableObject
{
    private readonly ICameraService _cameraService;
    
    [ObservableProperty]
    private ImageSource photoSource;
    
    public CameraViewModel(ICameraService cameraService)
    {
        _cameraService = cameraService;
    }
    
    [RelayCommand]
    private async Task TakePhoto()
    {
        if (!_cameraService.IsCameraAvailable())
        {
            await Shell.Current.DisplayAlert("Error", "No camera available", "OK");
            return;
        }
        
        var imageData = await _cameraService.TakePictureAsync();
        if (imageData != null)
        {
            PhotoSource = ImageSource.FromStream(() => new MemoryStream(imageData));
        }
    }
}
```

## Key Takeaways

- Use **conditional compilation** (`#if ANDROID`) for simple checks
- Use **partial classes** for organized platform-specific methods
- Use **dependency injection** (recommended) for testable, clean architecture
- Use **Platform folders** for auto-compiled platform files
- **DeviceInfo.Platform** for runtime platform detection
- Keep interfaces simple, avoid exposing platform types
- Minimize platform-specific code, prefer MAUI APIs
- Test on all target platforms

## Next Steps

- Learn [Platform APIs](07-Platform-APIs.md) for cross-platform features
- See [Permissions](08-Permissions.md) for platform permission handling
- Practice with [05.HardwareAndPlatformAPIs](../05.HardwareAndPlatformAPIs/)
