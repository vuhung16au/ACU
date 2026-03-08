# Hot Reload in .NET MAUI

## What is Hot Reload?

**Hot Reload** allows you to modify your app's code and UI while it's running, without losing app state or restarting.

**Benefits**:
- ⚡ Faster development cycle
- 🔄 No app restart needed
- 💾 Maintains app state
- 🎨 Instant UI feedback

## XAML Hot Reload

### How it Works

1. Run app in Debug mode (**F5**)
2. Modify XAML file
3. Save file (**Ctrl+S** / **Cmd+S**)
4. Changes appear **instantly**!

### Example: UI Tweaking

**Before**:
```xml
<Label Text="Welcome" 
       FontSize="18" 
       TextColor="Black" />
```

**After** (save to see changes immediately):
```xml
<Label Text="Welcome to MAUI" 
       FontSize="24" 
       FontAttributes="Bold"
       TextColor="Blue" />
```

No restart needed! ✨

### What You Can Change

✅ **Supported**:
- Text content
- Font properties (Size, Attributes, Family)
- Colors
- Padding, Margin, Spacing
- Layout properties (Row, Column, Alignment)
- Visibility
- Add/remove controls
- Change layout structure

❌ **Not Supported** (requires restart):
- Change XAML namespaces
- Add new resource dictionaries
- Change `x:Name` of existing controls
- Some binding changes

## C# Hot Reload

### How it Works

1. Run app in Debug mode
2. Modify C# code
3. Save file
4. Changes apply **automatically**

### Example: Logic Changes

**Before**:
```csharp
[RelayCommand]
private void Increment()
{
    Count++;  // Simple increment
}
```

**After** (save to apply immediately):
```csharp
[RelayCommand]
private void Increment()
{
    Count += 5;  // Now increments by 5
    Title = $"Count: {Count}";  // Added title update
}
```

### What You Can Change

✅ **Supported**:
- Method body logic
- Add new methods
- Add new private fields (limited)
- Change existing property values
- Add local variables
- Change control flow (if/else, loops)

❌ **Not Supported** (requires restart):
- Add new `[ObservableProperty]` attributes
- Change method signatures
- Add/remove constructor parameters
- Change base classes
- Add interfaces
- Modify DI registration

## Visual Studio Hot Reload

### Toolbar
While debugging, Hot Reload toolbar appears:

- **🔥 Hot Reload** button: Manually trigger reload
- **Auto-apply**: Changes apply on save

### Settings
**Tools → Options → Debugging → .NET/C++ Hot Reload**:
- ✅ Enable Hot Reload at debugging start
- ✅ Apply changes on save

## VS Code Hot Reload

Hot Reload works automatically with **.NET MAUI extension**.

**Trigger manually**:
1. Open Command Palette: `Ctrl+Shift+P` / `Cmd+Shift+P`
2. Run: **.NET MAUI: Hot Reload**

## Common Hot Reload Scenarios

### 1. Adjusting Layout

**Original**:
```xml
<VerticalStackLayout Spacing="10" Padding="20">
    <Label Text="Title" FontSize="20" />
    <Entry Placeholder="Enter text" />
    <Button Text="Submit" />
</VerticalStackLayout>
```

**Adjust** (updates live):
```xml
<VerticalStackLayout Spacing="15" Padding="20">
    <Label Text="Title" FontSize="24" FontAttributes="Bold" />
    <Label Text="Subtitle" FontSize="14" TextColor="Gray" />
    <Entry Placeholder="Enter your name" />
    <Button Text="Submit" BackgroundColor="Blue" TextColor="White" />
</VerticalStackLayout>
```

### 2. Styling Changes

**Original**:
```xml
<Frame BorderColor="Gray" CornerRadius="5">
    <Label Text="Card content" />
</Frame>
```

**Restyle** (instant feedback):
```xml
<Frame BorderColor="LightBlue" 
       CornerRadius="12" 
       Padding="15"
       BackgroundColor="#f0f8ff">
    <Label Text="Card content" FontSize="16" />
</Frame>
```

### 3. Method Logic Updates

**Original**:
```csharp
[RelayCommand]
private void Calculate()
{
    Result = Value1 + Value2;
}
```

**Update** (no restart):
```csharp
[RelayCommand]
private void Calculate()
{
    Result = Value1 + Value2;
    ResultText = $"Total: {Result}";
    Debug.WriteLine($"Calculated: {Result}");
}
```

### 4. Adding UI Elements

Start with:
```xml
<VerticalStackLayout>
    <Label Text="Hello" />
</VerticalStackLayout>
```

Add more (hot reloads):
```xml
<VerticalStackLayout>
    <Label Text="Hello" />
    <Label Text="Welcome to MAUI" FontSize="18" />
    <Button Text="Click Me" />
</VerticalStackLayout>
```

## Hot Reload Limitations

### Can't Hot Reload

1. **New Observable Properties**:
```csharp
// Requires restart
[ObservableProperty]
private string newProperty;  // ❌ Can't add with Hot Reload
```

2. **Constructor Changes**:
```csharp
public MainViewModel(IDataService service, ILogger logger)  // ❌ Can't change signature
{
}
```

3. **New Class Members**:
```csharp
// Adding new public properties/fields requires restart
public string NewPublicProperty { get; set; }  // ❌
```

4. **DI Registration**:
```csharp
// Changes in MauiProgram.cs require restart
builder.Services.AddSingleton<INewService, NewService>();  // ❌
```

### Workarounds

**For Observable Properties**: Use existing property temporarily:
```csharp
// Quick test: Reuse existing property
Title = "Test value";  // ✅ Works

// Later: Add new property properly with restart
```

**For new methods**: Add as private first:
```csharp
// Add private method (might work)
private void NewHelperMethod()
{
    // Implementation
}

// Call from existing method
[RelayCommand]
private void ExistingMethod()
{
    NewHelperMethod();  // ✅ Works
}
```

## Hot Reload Best Practices

1. ✅ **Save frequently**: Trigger Hot Reload with each save
2. ✅ **Test incrementally**: Make small changes and verify
3. ✅ **Use for UI iteration**: Perfect for layout/styling
4. ✅ **Watch output window**: See Hot Reload success/failure messages
5. ✅ **Restart when needed**: Don't fight Hot Reload limitations

## Troubleshooting Hot Reload

### Hot Reload Not Working

**Check**:
1. ✅ Running in **Debug** mode (not Release)
2. ✅ **Auto-apply** enabled in settings
3. ✅ File is **saved** after changes
4. ✅ Change is **supported** (see limitations above)

**Solutions**:
- Clean and rebuild: `dotnet clean && dotnet build`
- Restart debugging session
- Check Output window for errors
- Update .NET MAUI workload: `dotnet workload update`

### Changes Not Appearing

**Try**:
1. Click **Hot Reload** button manually
2. Stop debugging, rebuild, restart
3. Check for syntax errors in Output window
4. Verify file is part of the project (.csproj)

### Partial Changes Applied

Some changes might require **two saves**:
1. First save: Basic change
2. Second save: Complete update

Or just restart debugging session.

## Hot Reload in Practice

### UI Design Workflow

```xml
<!-- Start with basic layout -->
<VerticalStackLayout Padding="20">
    <Label Text="Title" />
    <Entry />
    <Button Text="Submit" />
</VerticalStackLayout>

<!-- Refine with Hot Reload (no restarts!) -->
<!-- Save 1: Add spacing -->
<VerticalStackLayout Padding="20" Spacing="15">
    <Label Text="Title" />
    <Entry />
    <Button Text="Submit" />
</VerticalStackLayout>

<!-- Save 2: Style title -->
<VerticalStackLayout Padding="20" Spacing="15">
    <Label Text="Title" FontSize="24" FontAttributes="Bold" />
    <Entry />
    <Button Text="Submit" />
</VerticalStackLayout>

<!-- Save 3: Polish button -->
<VerticalStackLayout Padding="20" Spacing="15">
    <Label Text="Title" FontSize="24" FontAttributes="Bold" />
    <Entry Placeholder="Enter name" />
    <Button Text="Submit" 
            BackgroundColor="Blue" 
            TextColor="White"
            CornerRadius="8" />
</VerticalStackLayout>
```

All updates appear **instantly**! 🚀

### Logic Refinement

```csharp
// Start simple
[RelayCommand]
private void Submit()
{
    Result = Input;
}

// Hot Reload: Add validation (save)
[RelayCommand]
private void Submit()
{
    if (string.IsNullOrEmpty(Input))
        return;
    
    Result = Input;
}

// Hot Reload: Add feedback (save)
[RelayCommand]
private void Submit()
{
    if (string.IsNullOrEmpty(Input))
    {
        StatusMessage = "Input required";
        return;
    }
    
    Result = Input;
    StatusMessage = "Success!";
}
```

## Hot Reload Keyboard Shortcuts

| Action | Windows/Linux | macOS |
|--------|---------------|-------|
| Save file | `Ctrl+S` | `Cmd+S` |
| Hot Reload (manual) | `Ctrl+Alt+F5` | `Cmd+Option+F5` |
| Restart debugging | `Ctrl+Shift+F5` | `Cmd+Shift+F5` |

## Performance Considerations

- Hot Reload is **fast** for small changes
- Large UI changes might take a few seconds
- Complex layouts: Consider simplifying during development
- **No impact** on release builds (debugging only)

## Hot Reload Output

Watch **Output** window for messages:

```
Hot reload succeeded for 'MyApp.dll'
Hot reload applied: ProfilePage.xaml
Hot reload applied: MainViewModel.cs
```

Or errors:

```
Hot reload failed: Rude edit detected (method signature changed)
Hot reload failed: File not found
```

## Key Takeaways

- **XAML Hot Reload**: Instant UI updates on save
- **C# Hot Reload**: Method body changes without restart
- **Limitations**: Can't add properties, change signatures
- **Best for**: UI tweaking, method logic refinement
- **Saves time**: No more restart cycles!
- **State preserved**: App state maintained across changes
- **Debug only**: Only works in Debug mode
- **Watch Output**: Check for Hot Reload status messages

## Next Steps

- Learn [Debugging](12-Debugging-MAUI.md) for troubleshooting
- See [Deployment](14-Deployment.md) for release builds
- Practice Hot Reload while building projects
- Master XAML/C# Hot Reload for faster development
