# Permissions in .NET MAUI

## Why Permissions?

Mobile platforms require explicit user permission to access sensitive features like:
- Camera and microphone
- Location (GPS)
- Photos and files
- Contacts
- Notifications

**Best Practice**: Always check and request permissions before using platform APIs.

## Permission Types

Common permissions in MAUI:

| Permission | When Needed |
|-----------|-------------|
| `Camera` | Taking photos/videos |
| `Photos` | Access photo library (iOS) |
| `Media` | Access media files (Android 13+) |
| `Microphone` | Recording audio |
| `LocationWhenInUse` | GPS while app is active |
| `LocationAlways` | GPS in background |
| `StorageRead` | Read files (Android) |
| `StorageWrite` | Write files (Android) |
| `CalendarRead` | Read calendar events |
| `CalendarWrite` | Modify calendar |
| `ContactsRead` | Read contacts |
| `ContactsWrite` | Modify contacts |

## Permission Workflow

1. **Check** if permission is granted
2. **Request** if not granted
3. **Handle** the result (Granted/Denied/Unknown)

## Check Permission Status

```csharp
// Check camera permission
var status = await Permissions.CheckStatusAsync<Permissions.Camera>();

// status can be:
// - PermissionStatus.Granted: User has granted
// - PermissionStatus.Denied: User has denied
// - PermissionStatus.Unknown: Never asked before
// - PermissionStatus.Disabled: Feature is disabled
// - PermissionStatus.Restricted: Restricted by OS (iOS)
```

## Request Permission

```csharp
var status = await Permissions.RequestAsync<Permissions.Camera>();

if (status == PermissionStatus.Granted)
{
    // Permission granted, use the feature
}
else
{
    // Permission denied
    await Shell.Current.DisplayAlert("Permission Required", 
        "Camera access is needed to take photos", "OK");
}
```

## Complete Permission Pattern

```csharp
public async Task<bool> CheckAndRequestPermission<T>() where T : Permissions.BasePermission, new()
{
    var status = await Permissions.CheckStatusAsync<T>();
    
    if (status == PermissionStatus.Granted)
        return true;
    
    if (status == PermissionStatus.Denied && DeviceInfo.Platform == DevicePlatform.iOS)
    {
        // On iOS, if denied, can't request again - must open settings
        await Shell.Current.DisplayAlert("Permission Required",
            "Please enable permission in Settings", "OK");
        return false;
    }
    
    status = await Permissions.RequestAsync<T>();
    
    return status == PermissionStatus.Granted;
}
```

**Usage**:
```csharp
[RelayCommand]
private async Task TakePhoto()
{
    if (await CheckAndRequestPermission<Permissions.Camera>())
    {
        // Permission granted, proceed
        var photo = await MediaPicker.Default.CapturePhotoAsync();
    }
}
```

## Platform-Specific Configuration

### Android (AndroidManifest.xml)

Add permissions to `Platforms/Android/AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application ...>
    </application>
    
    <!-- Camera -->
    <uses-permission android:name="android.permission.CAMERA" />
    
    <!-- Location -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    
    <!-- Storage (Android 12 and below) -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" 
                     android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" 
                     android:maxSdkVersion="32" />
    
    <!-- Media (Android 13+) -->
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
    
    <!-- Internet -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    
    <!-- Vibration -->
    <uses-permission android:name="android.permission.VIBRATE" />
</manifest>
```

### iOS (Info.plist)

Add usage descriptions to `Platforms/iOS/Info.plist`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <!-- Camera -->
    <key>NSCameraUsageDescription</key>
    <string>This app needs access to the camera to take photos</string>
    
    <!-- Photo Library -->
    <key>NSPhotoLibraryUsageDescription</key>
    <string>This app needs access to photos to select images</string>
    
    <!-- Location When In Use -->
    <key>NSLocationWhenInUseUsageDescription</key>
    <string>This app needs access to your location</string>
    
    <!-- Location Always -->
    <key>NSLocationAlwaysUsageDescription</key>
    <string>This app needs access to your location even in background</string>
    
    <!-- Microphone -->
    <key>NSMicrophoneUsageDescription</key>
    <string>This app needs access to the microphone to record audio</string>
    
    <!-- Contacts -->
    <key>NSContactsUsageDescription</key>
    <string>This app needs access to contacts</string>
    
    <!-- Calendar -->
    <key>NSCalendarsUsageDescription</key>
    <string>This app needs access to calendar</string>
</dict>
</plist>
```

**Important**: Without these descriptions, your app will crash on iOS when requesting permissions!

## Common Permission Scenarios

### 1. Camera Permission

```csharp
public partial class CameraViewModel : ObservableObject
{
    [RelayCommand]
    private async Task TakePhoto()
    {
        var status = await Permissions.CheckStatusAsync<Permissions.Camera>();
        
        if (status != PermissionStatus.Granted)
        {
            status = await Permissions.RequestAsync<Permissions.Camera>();
        }
        
        if (status == PermissionStatus.Granted)
        {
            var photo = await MediaPicker.Default.CapturePhotoAsync();
            // Process photo
        }
        else
        {
            await Shell.Current.DisplayAlert("Permission Denied", 
                "Camera access is required to take photos", "OK");
        }
    }
}
```

### 2. Location Permission

```csharp
[RelayCommand]
private async Task GetLocation()
{
    var status = await Permissions.CheckStatusAsync<Permissions.LocationWhenInUse>();
    
    if (status != PermissionStatus.Granted)
    {
        status = await Permissions.RequestAsync<Permissions.LocationWhenInUse>();
    }
    
    if (status == PermissionStatus.Granted)
    {
        var location = await Geolocation.Default.GetLocationAsync();
        LocationText = $"{location.Latitude}, {location.Longitude}";
    }
    else
    {
        await Shell.Current.DisplayAlert("Permission Denied", 
            "Location access is required", "OK");
    }
}
```

### 3. Storage Permission (Media)

```csharp
[RelayCommand]
private async Task PickPhoto()
{
    // On Android 13+, use Media permission
    // On iOS, use Photos permission
    PermissionStatus status;
    
    if (DeviceInfo.Platform == DevicePlatform.Android && DeviceInfo.Version.Major >= 13)
    {
        status = await Permissions.CheckStatusAsync<Permissions.Media>();
        if (status != PermissionStatus.Granted)
            status = await Permissions.RequestAsync<Permissions.Media>();
    }
    else
    {
        status = await Permissions.CheckStatusAsync<Permissions.Photos>();
        if (status != PermissionStatus.Granted)
            status = await Permissions.RequestAsync<Permissions.Photos>();
    }
    
    if (status == PermissionStatus.Granted)
    {
        var photo = await MediaPicker.Default.PickPhotoAsync();
        // Process photo
    }
}
```

## Handling Permission Denial

### First-Time Denial
User can be asked again:
```csharp
if (status == PermissionStatus.Denied)
{
    // Show explanation
    var result = await Shell.Current.DisplayAlert(
        "Permission Required",
        "Camera access is needed to take photos. Would you like to grant permission?",
        "Yes", "No");
    
    if (result)
    {
        // Request again
        status = await Permissions.RequestAsync<Permissions.Camera>();
    }
}
```

### Permanent Denial (iOS)
After denial, iOS won't show prompt again:
```csharp
if (status == PermissionStatus.Denied && DeviceInfo.Platform == DevicePlatform.iOS)
{
    var result = await Shell.Current.DisplayAlert(
        "Permission Required",
        "Please enable Camera access in Settings",
        "Open Settings", "Cancel");
    
    if (result)
    {
        AppInfo.ShowSettingsUI();  // Opens app settings
    }
}
```

## Multiple Permissions

Request multiple permissions at once:

```csharp
public async Task<bool> CheckPermissions()
{
    var cameraStatus = await Permissions.CheckStatusAsync<Permissions.Camera>();
    var locationStatus = await Permissions.CheckStatusAsync<Permissions.LocationWhenInUse>();
    
    if (cameraStatus != PermissionStatus.Granted)
        cameraStatus = await Permissions.RequestAsync<Permissions.Camera>();
    
    if (locationStatus != PermissionStatus.Granted)
        locationStatus = await Permissions.RequestAsync<Permissions.LocationWhenInUse>();
    
    return cameraStatus == PermissionStatus.Granted && 
           locationStatus == PermissionStatus.Granted;
}
```

## Custom Permissions

Create custom permission for multiple related permissions:

```csharp
public class MediaPermissions : Permissions.BasePermission
{
    public override async Task<PermissionStatus> CheckStatusAsync()
    {
        var camera = await Permissions.CheckStatusAsync<Permissions.Camera>();
        var media = await Permissions.CheckStatusAsync<Permissions.Media>();
        
        if (camera == PermissionStatus.Granted && media == PermissionStatus.Granted)
            return PermissionStatus.Granted;
        
        return PermissionStatus.Denied;
    }
    
    public override async Task<PermissionStatus> RequestAsync()
    {
        var camera = await Permissions.RequestAsync<Permissions.Camera>();
        var media = await Permissions.RequestAsync<Permissions.Media>();
        
        if (camera == PermissionStatus.Granted && media == PermissionStatus.Granted)
            return PermissionStatus.Granted;
        
        return PermissionStatus.Denied;
    }
}
```

**Usage**:
```csharp
var status = await Permissions.RequestAsync<MediaPermissions>();
```

## Complete Example: Permission Service

Create a reusable service:

```csharp
public interface IPermissionService
{
    Task<bool> CheckAndRequestPermissionAsync<T>() where T : Permissions.BasePermission, new();
}

public class PermissionService : IPermissionService
{
    public async Task<bool> CheckAndRequestPermissionAsync<T>() where T : Permissions.BasePermission, new()
    {
        var status = await Permissions.CheckStatusAsync<T>();
        
        if (status == PermissionStatus.Granted)
            return true;
        
        // If denied on iOS, can't request again
        if (status == PermissionStatus.Denied && DeviceInfo.Platform == DevicePlatform.iOS)
        {
            await ShowSettingsAlert();
            return false;
        }
        
        // Request permission
        status = await Permissions.RequestAsync<T>();
        
        if (status != PermissionStatus.Granted)
        {
            await ShowDeniedAlert();
            return false;
        }
        
        return true;
    }
    
    private async Task ShowSettingsAlert()
    {
        var result = await Shell.Current.DisplayAlert(
            "Permission Required",
            "Please enable the permission in Settings",
            "Open Settings",
            "Cancel");
        
        if (result)
        {
            AppInfo.ShowSettingsUI();
        }
    }
    
    private async Task ShowDeniedAlert()
    {
        await Shell.Current.DisplayAlert(
            "Permission Denied",
            "This feature requires permission to function",
            "OK");
    }
}
```

**Register in MauiProgram.cs**:
```csharp
builder.Services.AddSingleton<IPermissionService, PermissionService>();
```

**Use in ViewModel**:
```csharp
public partial class CameraViewModel : ObservableObject
{
    private readonly IPermissionService _permissionService;
    
    public CameraViewModel(IPermissionService permissionService)
    {
        _permissionService = permissionService;
    }
    
    [RelayCommand]
    private async Task TakePhoto()
    {
        if (await _permissionService.CheckAndRequestPermissionAsync<Permissions.Camera>())
        {
            var photo = await MediaPicker.Default.CapturePhotoAsync();
            // Process photo
        }
    }
}
```

## Best Practices

1. ✅ **Check before requesting**: Always check status first
2. ✅ **Explain why**: Tell users why you need the permission
3. ✅ **Request when needed**: Don't ask for all permissions at startup
4. ✅ **Handle denial gracefully**: Provide fallback or explain impact
5. ✅ **Direct to settings on iOS**: After denial, guide users to settings
6. ✅ **Add usage descriptions**: Always add to Info.plist (iOS)
7. ✅ **Declare in manifest**: Add all permissions to AndroidManifest.xml

## Common Mistakes

❌ **Not checking permission**
```csharp
var photo = await MediaPicker.CapturePhotoAsync();  // Might crash!
```

❌ **Missing Info.plist descriptions (iOS)**
```xml
<!-- Missing NSCameraUsageDescription - app will crash! -->
```

❌ **Requesting all permissions at once**
```csharp
// Don't do this on startup - overwhelms users
await Permissions.RequestAsync<Permissions.Camera>();
await Permissions.RequestAsync<Permissions.Location>();
await Permissions.RequestAsync<Permissions.Media>();
```

❌ **Not handling denial**
```csharp
if (status == PermissionStatus.Granted)
{
    // Use feature
}
// What if denied? No feedback to user!
```

## Permission Checklist

Before using any platform API:

- [ ] Add permission to `AndroidManifest.xml`
- [ ] Add usage description to `Info.plist`
- [ ] Check permission status first
- [ ] Request if not granted
- [ ] Handle granted case
- [ ] Handle denied case with user feedback
- [ ] Test on iOS (strict permission handling)
- [ ] Test on Android (different versions)

## Key Takeaways

- Always check and request permissions before using APIs
- Use `Permissions.CheckStatusAsync<T>()` to check
- Use `Permissions.RequestAsync<T>()` to request
- Handle `PermissionStatus.Denied` gracefully
- Add usage descriptions to Info.plist (iOS crashes without them!)
- Declare permissions in AndroidManifest.xml
- On iOS denial, direct users to Settings
- Request permissions when needed, not at startup
- Create a reusable `PermissionService` for consistency

## Next Steps

- Learn [Platform APIs](07-Platform-APIs.md) that require permissions
- See [Platform-Specific Code](09-Platform-Specific-Code.md) for advanced handling
- Practice with [05.HardwareAndPlatformAPIs](../05.HardwareAndPlatformAPIs/)
