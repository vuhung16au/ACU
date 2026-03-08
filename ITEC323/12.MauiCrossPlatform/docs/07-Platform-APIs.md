# Platform APIs in .NET MAUI

## What are Platform APIs?

**Platform APIs** provide access to native device features like camera, GPS, battery, and sensors from shared C# code.

**Key Namespaces**:
- `Microsoft.Maui.Media` - Camera, screenshots
- `Microsoft.Maui.Devices` - Battery, flashlight, vibration
- `Microsoft.Maui.Devices.Sensors` - Accelerometer, gyroscope, compass
- `Microsoft.Maui.Storage` - File storage, preferences

## MediaPicker (Camera & Gallery)

### Take a Photo
```csharp
[RelayCommand]
private async Task TakePhoto()
{
    if (MediaPicker.Default.IsCaptureSupported)
    {
        var photo = await MediaPicker.Default.CapturePhotoAsync();
        
        if (photo != null)
        {
            // Save to local storage
            var localFilePath = Path.Combine(FileSystem.CacheDirectory, photo.FileName);
            
            using var sourceStream = await photo.OpenReadAsync();
            using var localFileStream = File.OpenWrite(localFilePath);
            
            await sourceStream.CopyToAsync(localFileStream);
            
            PhotoPath = localFilePath;
        }
    }
}
```

### Pick from Gallery
```csharp
[RelayCommand]
private async Task PickPhoto()
{
    var photo = await MediaPicker.Default.PickPhotoAsync(new MediaPickerOptions
    {
        Title = "Pick a photo"
    });
    
    if (photo != null)
    {
        var stream = await photo.OpenReadAsync();
        PhotoImageSource = ImageSource.FromStream(() => stream);
    }
}
```

### Pick Video
```csharp
var video = await MediaPicker.Default.PickVideoAsync();
if (video != null)
{
    var videoPath = video.FullPath;
    // Use video path
}
```

## Geolocation

### Get Current Location
```csharp
[RelayCommand]
private async Task GetLocation()
{
    try
    {
        var location = await Geolocation.Default.GetLocationAsync(new GeolocationRequest
        {
            DesiredAccuracy = GeolocationAccuracy.Medium,
            Timeout = TimeSpan.FromSeconds(10)
        });
        
        if (location != null)
        {
            LocationText = $"Lat: {location.Latitude}, Lon: {location.Longitude}";
            Altitude = location.Altitude;
            Accuracy = location.Accuracy;
        }
    }
    catch (FeatureNotSupportedException)
    {
        await Shell.Current.DisplayAlert("Error", "Geolocation not supported", "OK");
    }
    catch (PermissionException)
    {
        await Shell.Current.DisplayAlert("Error", "Permission denied", "OK");
    }
}
```

### Track Location Changes
```csharp
private CancellationTokenSource _cancelTokenSource;

public async Task StartTracking()
{
    _cancelTokenSource = new CancellationTokenSource();
    
    await Geolocation.Default.ListenToLocationUpdates(
        new GeolocationListeningRequest
        {
            DesiredAccuracy = GeolocationAccuracy.Best
        },
        (location) =>
        {
            // Called when location updates
            CurrentLocation = location;
        },
        _cancelTokenSource.Token);
}

public void StopTracking()
{
    _cancelTokenSource?.Cancel();
}
```

### Open Maps
```csharp
[RelayCommand]
private async Task OpenMaps(double latitude, double longitude)
{
    var location = new Location(latitude, longitude);
    var options = new MapLaunchOptions { Name = "Target Location" };
    
    await Map.Default.OpenAsync(location, options);
}
```

## Battery

```csharp
// Check battery status
var battery = Battery.Default;

var level = battery.ChargeLevel;  // 0.0 to 1.0
var state = battery.State;         // Charging, Discharging, Full, NotCharging
var powerSource = battery.PowerSource;  // Battery, AC, Usb, Wireless

// Monitor battery changes
battery.BatteryInfoChanged += (sender, e) =>
{
    var info = e;
    BatteryLevel = $"{info.ChargeLevel * 100}%";
    BatteryState = info.State.ToString();
};
```

## Connectivity

```csharp
// Check network access
var connectivity = Connectivity.Current;

var access = connectivity.NetworkAccess;
// None, Unknown, Internet, ConstrainedInternet, Local

if (access == NetworkAccess.Internet)
{
    // Internet available
}

// Monitor connectivity changes
connectivity.ConnectivityChanged += (sender, e) =>
{
    var newAccess = e.NetworkAccess;
    IsOnline = newAccess == NetworkAccess.Internet;
};

// Check connection profiles
var profiles = connectivity.ConnectionProfiles;
// WiFi, Cellular, Bluetooth, Ethernet
```

## Flashlight

```csharp
[RelayCommand]
private async Task ToggleFlashlight()
{
    try
    {
        if (IsFlashlightOn)
        {
            await Flashlight.Default.TurnOffAsync();
        }
        else
        {
            await Flashlight.Default.TurnOnAsync();
        }
        IsFlashlightOn = !IsFlashlightOn;
    }
    catch (FeatureNotSupportedException)
    {
        await Shell.Current.DisplayAlert("Error", "Flashlight not supported", "OK");
    }
}
```

## Vibration

```csharp
// Vibrate for 500ms
Vibration.Default.Vibrate(TimeSpan.FromMilliseconds(500));

// Pattern vibration (not available on all platforms)
Vibration.Default.Vibrate();  // Default vibration

// Cancel vibration
Vibration.Default.Cancel();
```

## Sensors

### Accelerometer
Measures device acceleration (moving/tilting):

```csharp
public void StartAccelerometer()
{
    if (Accelerometer.Default.IsSupported)
    {
        Accelerometer.Default.ReadingChanged += Accelerometer_ReadingChanged;
        Accelerometer.Default.Start(SensorSpeed.UI);
    }
}

private void Accelerometer_ReadingChanged(object sender, AccelerometerChangedEventArgs e)
{
    var data = e.Reading;
    AccelX = data.Acceleration.X;
    AccelY = data.Acceleration.Y;
    AccelZ = data.Acceleration.Z;
}

public void StopAccelerometer()
{
    Accelerometer.Default.Stop();
    Accelerometer.Default.ReadingChanged -= Accelerometer_ReadingChanged;
}
```

### Gyroscope
Measures device rotation:

```csharp
Gyroscope.Default.ReadingChanged += (sender, e) =>
{
    var data = e.Reading;
    GyroX = data.AngularVelocity.X;
    GyroY = data.AngularVelocity.Y;
    GyroZ = data.AngularVelocity.Z;
};

Gyroscope.Default.Start(SensorSpeed.UI);
```

### Compass
Measures magnetic heading:

```csharp
Compass.Default.ReadingChanged += (sender, e) =>
{
    var heading = e.Reading.HeadingMagneticNorth;
    CompassHeading = $"{heading:F1}°";
};

Compass.Default.Start(SensorSpeed.UI);
```

### SensorSpeed Options
- `SensorSpeed.Fastest` - ~100Hz, drains battery
- `SensorSpeed.Game` - ~50Hz, for games
- `SensorSpeed.UI` - ~20Hz, for UI updates
- `SensorSpeed.Default` - ~5Hz, battery friendly

## FileSystem

### App Directories
```csharp
// App data directory (persistent)
var appDataDir = FileSystem.AppDataDirectory;
// iOS: /Documents
// Android: /data/user/0/[package]/files

// Cache directory (can be cleared)
var cacheDir = FileSystem.CacheDirectory;
// iOS: /Library/Caches
// Android: /data/user/0/[package]/cache
```

### Save Text File
```csharp
[RelayCommand]
private async Task SaveData()
{
    var filePath = Path.Combine(FileSystem.AppDataDirectory, "data.txt");
    await File.WriteAllTextAsync(filePath, "Hello, MAUI!");
}
```

### Read Text File
```csharp
[RelayCommand]
private async Task LoadData()
{
    var filePath = Path.Combine(FileSystem.AppDataDirectory, "data.txt");
    
    if (File.Exists(filePath))
    {
        var content = await File.ReadAllTextAsync(filePath);
        DataText = content;
    }
}
```

### Save JSON
```csharp
public async Task SaveTodos(List<TodoItem> todos)
{
    var json = JsonSerializer.Serialize(todos);
    var filePath = Path.Combine(FileSystem.AppDataDirectory, "todos.json");
    await File.WriteAllTextAsync(filePath, json);
}

public async Task<List<TodoItem>> LoadTodos()
{
    var filePath = Path.Combine(FileSystem.AppDataDirectory, "todos.json");
    
    if (File.Exists(filePath))
    {
        var json = await File.ReadAllTextAsync(filePath);
        return JsonSerializer.Deserialize<List<TodoItem>>(json);
    }
    
    return new List<TodoItem>();
}
```

## Preferences (Simple Key-Value Storage)

Store small amounts of data:

```csharp
// Save
Preferences.Default.Set("username", "johndoe");
Preferences.Default.Set("counter", 42);
Preferences.Default.Set("is_logged_in", true);

// Read
var username = Preferences.Default.Get("username", "Guest");  // Default: "Guest"
var counter = Preferences.Default.Get("counter", 0);
var isLoggedIn = Preferences.Default.Get("is_logged_in", false);

// Remove
Preferences.Default.Remove("username");

// Clear all
Preferences.Default.Clear();
```

**Good for**: Settings, flags, simple values  
**Not good for**: Large data, complex objects (use FileSystem with JSON)

## SecureStorage (Encrypted Storage)

Store sensitive data (passwords, tokens):

```csharp
// Save
await SecureStorage.Default.SetAsync("api_token", "abc123xyz");

// Read
var token = await SecureStorage.Default.GetAsync("api_token");

// Remove
SecureStorage.Default.Remove("api_token");

// Remove all
SecureStorage.Default.RemoveAll();
```

**Platform backing**:
- **iOS**: Keychain
- **Android**: KeyStore
- **Windows**: Data Protection API

## DeviceInfo

Get device information:

```csharp
var info = DeviceInfo.Current;

var model = info.Model;            // "iPhone 14", "Pixel 6"
var manufacturer = info.Manufacturer;  // "Apple", "Google"
var name = info.Name;              // Device name
var versionString = info.VersionString;  // "16.0", "13"
var platform = info.Platform;      // iOS, Android, WinUI, MacCatalyst
var idiom = info.Idiom;           // Phone, Tablet, Desktop, TV, Watch

// Check platform
if (DeviceInfo.Platform == DevicePlatform.Android)
{
    // Android-specific code
}
```

## AppInfo

Get app information:

```csharp
var appInfo = AppInfo.Current;

var packageName = appInfo.PackageName;  // "com.company.app"
var name = appInfo.Name;                // "My App"
var versionString = appInfo.VersionString;  // "1.0.0"
var buildString = appInfo.BuildString;      // "1"

// Open settings
AppInfo.ShowSettingsUI();
```

## Launcher (Open URLs/Files)

```csharp
// Open URL in browser
await Launcher.Default.OpenAsync("https://microsoft.com");

// Open email
await Launcher.Default.OpenAsync("mailto:support@example.com");

// Open phone dialer
await Launcher.Default.OpenAsync("tel:+1234567890");

// Open SMS
await Launcher.Default.OpenAsync("sms:+1234567890");

// Check if can open
var canOpen = await Launcher.Default.CanOpenAsync("geo:37.7749,-122.4194");
if (canOpen)
{
    await Launcher.Default.OpenAsync("geo:37.7749,-122.4194");
}
```

## Share (Native Share Dialog)

```csharp
[RelayCommand]
private async Task ShareText()
{
    await Share.Default.RequestAsync(new ShareTextRequest
    {
        Text = "Check out this cool app!",
        Title = "Share App"
    });
}

[RelayCommand]
private async Task ShareUrl()
{
    await Share.Default.RequestAsync(new ShareTextRequest
    {
        Uri = "https://example.com",
        Title = "Share Link"
    });
}

[RelayCommand]
private async Task ShareFile()
{
    var filePath = Path.Combine(FileSystem.CacheDirectory, "photo.jpg");
    
    await Share.Default.RequestAsync(new ShareFileRequest
    {
        Title = "Share Photo",
        File = new ShareFile(filePath)
    });
}
```

## Complete Example: Photo Capture App

**ViewModel**:
```csharp
public partial class CameraViewModel : ObservableObject
{
    [ObservableProperty]
    private ImageSource photoSource;

    [ObservableProperty]
    private string location;

    [RelayCommand]
    private async Task TakePhoto()
    {
        // Check permission first (see Permissions guide)
        var status = await Permissions.CheckStatusAsync<Permissions.Camera>();
        if (status != PermissionStatus.Granted)
        {
            status = await Permissions.RequestAsync<Permissions.Camera>();
            if (status != PermissionStatus.Granted)
                return;
        }

        // Capture photo
        var photo = await MediaPicker.Default.CapturePhotoAsync();
        if (photo != null)
        {
            // Load image
            var stream = await photo.OpenReadAsync();
            PhotoSource = ImageSource.FromStream(() => stream);

            // Get location
            var location = await Geolocation.Default.GetLocationAsync();
            if (location != null)
            {
                Location = $"{location.Latitude:F4}, {location.Longitude:F4}";
            }
        }
    }

    [RelayCommand]
    private async Task SharePhoto()
    {
        if (PhotoSource != null)
        {
            await Share.Default.RequestAsync(new ShareTextRequest
            {
                Text = $"Photo taken at {Location}",
                Title = "Share Photo"
            });
        }
    }
}
```

**View**:
```xml
<ContentPage xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:DataType="vm:CameraViewModel">
    
    <VerticalStackLayout Padding="20" Spacing="15">
        <Image Source="{Binding PhotoSource}" 
               HeightRequest="300" 
               Aspect="AspectFit" />
        
        <Label Text="{Binding Location}" 
               HorizontalOptions="Center" />
        
        <Button Text="Take Photo" 
                Command="{Binding TakePhotoCommand}" 
                Style="{StaticResource PrimaryButton}" />
        
        <Button Text="Share" 
                Command="{Binding SharePhotoCommand}"
                IsEnabled="{Binding PhotoSource, Converter={StaticResource IsNotNullConverter}}" />
    </VerticalStackLayout>
</ContentPage>
```

## Best Practices

1. ✅ **Check feature support**: Use `.IsSupported` before accessing sensors
2. ✅ **Request permissions**: Always check/request permissions (see [Permissions](08-Permissions.md))
3. ✅ **Handle exceptions**: Wrap API calls in try-catch
4. ✅ **Stop sensors**: Remember to stop sensors to save battery
5. ✅ **Use appropriate SensorSpeed**: Don't use `Fastest` unless necessary
6. ✅ **Secure sensitive data**: Use `SecureStorage` for tokens/passwords

## Common Mistakes

❌ **Not stopping sensors**
```csharp
Accelerometer.Default.Start();  // Never stopped - drains battery!
```

❌ **Ignoring permissions**
```csharp
var photo = await MediaPicker.CapturePhotoAsync();  // Might fail without permission check
```

❌ **Using Preferences for large data**
```csharp
Preferences.Set("todos", largeJsonString);  // Use FileSystem instead
```

## Key Takeaways

- **MediaPicker**: Camera and gallery access
- **Geolocation**: GPS location tracking
- **Battery/Connectivity**: Device status monitoring
- **Sensors**: Accelerometer, gyroscope, compass
- **FileSystem**: File/folder storage
- **Preferences**: Simple key-value storage
- **SecureStorage**: Encrypted credential storage
- **Launcher**: Open URLs, email, phone
- **Share**: Native sharing dialog
- Always check permissions before using APIs

## Next Steps

- Learn [Permissions](08-Permissions.md) for handling permissions properly
- See [Platform-Specific Code](09-Platform-Specific-Code.md) for custom implementations
- Practice with [05.HardwareAndPlatformAPIs](../05.HardwareAndPlatformAPIs/)
