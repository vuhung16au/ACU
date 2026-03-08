# Debugging .NET MAUI Applications

## Debugging Overview

MAUI apps can be debugged on:
- **Android Emulator** or physical device
- **iOS Simulator** (macOS only) or physical device
- **Windows** desktop
- **Mac Catalyst** (macOS only)

## Setting Up Debugging

### Visual Studio (Windows/Mac)

1. Select target platform from dropdown (Android, iOS, Windows, Mac Catalyst)
2. Select device/emulator
3. Press **F5** or click **Debug** button

### VS Code

1. Install **.NET MAUI extension**
2. Open Command Palette: `Ctrl+Shift+P` (Windows/Linux) or `Cmd+Shift+P` (Mac)
3. Run: `.NET MAUI: Pick Android Device` or `.NET MAUI: Pick iOS Simulator`
4. Press **F5** to start debugging

## Breakpoints

### Set Breakpoints
Click in left margin of code editor or press **F9** on a line:

```csharp
[RelayCommand]
private async Task LoadData()
{
    IsLoading = true;  // ← Set breakpoint here
    
    var data = await _dataService.GetDataAsync();
    
    Items = new ObservableCollection<Item>(data);  // ← Or here
    
    IsLoading = false;
}
```

### Breakpoint Actions
- **F5**: Continue execution
- **F10**: Step Over (execute current line)
- **F11**: Step Into (enter method)
- **Shift+F11**: Step Out (exit current method)

### Conditional Breakpoints
Right-click breakpoint → **Conditions**:

```csharp
// Break only when count > 10
foreach (var item in items)
{
    count++;  // Breakpoint condition: count > 10
}
```

## Debug Console

### Output Window
View debug output:
- **Visual Studio**: View → Output
- **VS Code**: Terminal → Output

```csharp
// Write to output
System.Diagnostics.Debug.WriteLine("Debug message");
System.Diagnostics.Debug.WriteLine($"Value: {myVariable}");

// Conditional output
System.Diagnostics.Debug.WriteLineIf(condition, "Message");
```

### Locals Window
- **Visual Studio**: Debug → Windows → Locals
- Shows all local variables in current scope

### Watch Window
- **Visual Studio**: Debug → Windows → Watch
- Add expressions to monitor

## Platform-Specific Debugging

### Android

**View Logs**:
```bash
# Android logcat (all logs)
adb logcat

# Filter by app name
adb logcat | grep "com.yourcompany.app"

# Filter by tag
adb logcat *:E  # Errors only
```

**Common Issues**:
- **App not deploying**: Restart emulator, clean solution
- **Build errors**: Check Android SDK version in `.csproj`
- **Runtime crashes**: Check `logcat` for stack traces

### iOS

**View Logs** (macOS only):
- Xcode → Window → Devices and Simulators → Select device → Console

**Common Issues**:
- **Provisioning errors**: Check Apple Developer account, certificates
- **Simulator not found**: Run `xcrun simctl list` to verify
- **Build errors**: Clean build folder: `Shift+Cmd+K` in Xcode

### Windows

- Standard .NET debugging
- Use Windows Event Viewer for crashes
- Check Output window for exceptions

## Hot Reload

**Hot Reload** lets you modify code/XAML without restarting the app.

### XAML Hot Reload

1. Make changes to XAML while debugging
2. Save file (`Ctrl+S` / `Cmd+S`)
3. UI updates automatically!

Example:
```xml
<!-- Change this while debugging -->
<Label Text="Hello World" />

<!-- To this -->
<Label Text="Hello MAUI" FontSize="24" TextColor="Blue" />

<!-- UI updates without restart! -->
```

### C# Hot Reload

**Supported Changes**:
- ✅ Method body changes
- ✅ Add new methods
- ✅ Property value changes
- ❌ Add new properties (requires restart)
- ❌ Change class structure (requires restart)

```csharp
[RelayCommand]
private void DoSomething()
{
    // Change this
    Title = "Old Title";
    
    // To this (updates without restart)
    Title = "New Title";
}
```

**Enable Hot Reload**:
- Visual Studio: Enabled by default
- VS Code: Should work automatically with MAUI extension

**Limitations**:
- Can't add new properties/fields
- Can't change method signatures
- Some changes require app restart

## Exception Handling

### Try-Catch in Code
```csharp
[RelayCommand]
private async Task LoadData()
{
    try
    {
        var data = await _dataService.GetDataAsync();
        Items = new ObservableCollection<Item>(data);
    }
    catch (HttpRequestException ex)
    {
        await Shell.Current.DisplayAlert("Network Error", ex.Message, "OK");
        System.Diagnostics.Debug.WriteLine($"HTTP Error: {ex}");
    }
    catch (Exception ex)
    {
        await Shell.Current.DisplayAlert("Error", "Something went wrong", "OK");
        System.Diagnostics.Debug.WriteLine($"Unexpected error: {ex}");
    }
}
```

### Break on Exceptions
**Visual Studio**: Debug → Windows → Exception Settings
- Check **Common Language Runtime Exceptions** to break on all exceptions

**VS Code**: Add to `launch.json`:
```json
{
    "configurations": [
        {
            "name": ".NET MAUI",
            "type": "coreclr",
            "request": "launch",
            "exceptionBreakMode": "UserUnhandled"
        }
    ]
}
```

## Common Debugging Patterns

### 1. Logging Service
```csharp
public interface ILogger
{
    void Log(string message);
    void LogError(string message, Exception ex);
}

public class DebugLogger : ILogger
{
    public void Log(string message)
    {
        System.Diagnostics.Debug.WriteLine($"[LOG] {message}");
    }
    
    public void LogError(string message, Exception ex)
    {
        System.Diagnostics.Debug.WriteLine($"[ERROR] {message}: {ex.Message}");
        System.Diagnostics.Debug.WriteLine(ex.StackTrace);
    }
}

// Register in MauiProgram.cs
builder.Services.AddSingleton<ILogger, DebugLogger>();

// Use in ViewModel
public partial class MainViewModel : ObservableObject
{
    private readonly ILogger _logger;
    
    public MainViewModel(ILogger logger)
    {
        _logger = logger;
    }
    
    [RelayCommand]
    private async Task LoadData()
    {
        _logger.Log("Loading data...");
        
        try
        {
            var data = await _dataService.GetDataAsync();
            _logger.Log($"Loaded {data.Count} items");
        }
        catch (Exception ex)
        {
            _logger.LogError("Failed to load data", ex);
        }
    }
}
```

### 2. Debug Overlays
Show debug info on screen:

```xml
<ContentPage>
    <Grid>
        <!-- Main content -->
        <ScrollView>
            <!-- Your app UI -->
        </ScrollView>
        
        <!-- Debug overlay (conditional) -->
        <Frame BackgroundColor="#80000000" 
               Padding="10" 
               VerticalOptions="End"
               IsVisible="{Binding IsDebugMode}">
            <VerticalStackLayout>
                <Label Text="{Binding DebugInfo}" TextColor="White" FontSize="12" />
                <Label Text="{Binding DeviceInfo}" TextColor="Yellow" FontSize="10" />
            </VerticalStackLayout>
        </Frame>
    </Grid>
</ContentPage>
```

```csharp
public partial class MainViewModel : ObservableObject
{
    [ObservableProperty]
    private string debugInfo;
    
    [ObservableProperty]
    private string deviceInfo;
    
    public bool IsDebugMode =>
#if DEBUG
        true;
#else
        false;
#endif
    
    public void UpdateDebugInfo()
    {
        DebugInfo = $"Items: {Items.Count}, Loading: {IsLoading}";
        DeviceInfo = $"Platform: {DeviceInfo.Platform}, Version: {DeviceInfo.VersionString}";
    }
}
```

### 3. Remote Logging
For production debugging, log to remote service:

```csharp
public class RemoteLogger : ILogger
{
    private readonly HttpClient _httpClient;
    
    public RemoteLogger()
    {
        _httpClient = new HttpClient { BaseAddress = new Uri("https://your-logging-service.com") };
    }
    
    public async void Log(string message)
    {
        try
        {
            await _httpClient.PostAsJsonAsync("/logs", new
            {
                Level = "INFO",
                Message = message,
                Timestamp = DateTime.UtcNow,
                DeviceInfo = $"{DeviceInfo.Platform} {DeviceInfo.VersionString}"
            });
        }
        catch
        {
            // Fail silently for logging errors
        }
    }
    
    public async void LogError(string message, Exception ex)
    {
        try
        {
            await _httpClient.PostAsJsonAsync("/logs", new
            {
                Level = "ERROR",
                Message = message,
                Exception = ex.ToString(),
                Timestamp = DateTime.UtcNow,
                DeviceInfo = $"{DeviceInfo.Platform} {DeviceInfo.VersionString}"
            });
        }
        catch
        {
            // Fail silently
        }
    }
}
```

## Performance Profiling

### Memory Profiler (Visual Studio)
1. Debug → Performance Profiler
2. Select **.NET Object Allocation Tracking**
3. Start profiling
4. Use app, take snapshots
5. Compare snapshots to find memory leaks

### App Startup Time
```csharp
public App()
{
    var stopwatch = System.Diagnostics.Stopwatch.StartNew();
    
    InitializeComponent();
    
    MainPage = new AppShell();
    
    stopwatch.Stop();
    System.Diagnostics.Debug.WriteLine($"App startup: {stopwatch.ElapsedMilliseconds}ms");
}
```

## Debugging Tips

### 1. Clean and Rebuild
If strange errors:
```bash
# Clean solution
dotnet clean

# Delete bin/ and obj/
rm -rf bin/ obj/

# Rebuild
dotnet build
```

### 2. Check Build Output
Always check **Output** window for build warnings/errors.

### 3. Simplify Problem
Comment out code until issue disappears, then add back piece by piece.

### 4. Check Platform Logs
- **Android**: `adb logcat`
- **iOS**: Xcode Device Console
- **Windows**: Event Viewer

### 5. Test on Multiple Devices
Bug might be device-specific (memory, OS version, screen size).

## Common Issues

| Issue | Solution |
|-------|----------|
| App crashes immediately | Check logcat/console for exception |
| UI not updating | Ensure `ObservableProperty` or `INotifyPropertyChanged` |
| Breakpoint not hit | Check if code is actually executed |
| Slow startup | Profile app, reduce initialization work |
| Memory leak | Use Memory Profiler to find unreleased objects |
| Layout issues | Use XAML Hot Reload to iterate quickly |

## Best Practices

1. ✅ **Use logging**: Don't rely only on breakpoints
2. ✅ **Test on real devices**: Emulators don't catch all issues
3. ✅ **Handle exceptions**: Try-catch with user feedback
4. ✅ **Use Hot Reload**: Iterate quickly on UI changes
5. ✅ **Profile performance**: Find bottlenecks early
6. ✅ **Debug on all platforms**: Platform-specific bugs exist

## Key Takeaways

- Set breakpoints, use F5/F10/F11 for step debugging
- Use `Debug.WriteLine()` for console output
- XAML Hot Reload updates UI without restart
- C# Hot Reload works for method body changes
- Check platform logs (`adb logcat` for Android)
- Use try-catch for exception handling
- Create logging service for production debugging
- Profile performance with Visual Studio tools
- Clean/rebuild when debugging gets weird

## Next Steps

- Learn [Hot Reload](13-Hot-Reload.md) advanced techniques
- See [Deployment](14-Deployment.md) for release builds
- Practice with all 6 projects to master debugging
