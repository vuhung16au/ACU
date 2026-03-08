# Styling and Theming in .NET MAUI

## What is Styling?

**Styling** defines reusable visual properties for UI elements. Benefits:
- **Consistency**: Same look across the app
- **Maintainability**: Change once, update everywhere
- **Efficiency**: Write less XAML

## ResourceDictionary Basics

Store reusable resources (colors, styles, values) in `ResourceDictionary`.

### App.xaml (Application-Level)
```xml
<Application xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="MyApp.App">
    <Application.Resources>
        <ResourceDictionary>
            <!-- Colors -->
            <Color x:Key="PrimaryColor">#007bff</Color>
            <Color x:Key="SecondaryColor">#6c757d</Color>
            
            <!-- Values -->
            <x:Double x:Key="LargeFontSize">24</x:Double>
            <x:String x:Key="AppName">My MAUI App</x:String>
        </ResourceDictionary>
    </Application.Resources>
</Application>
```

### Page-Level Resources
```xml
<ContentPage xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:Class="MyApp.HomePage">
    <ContentPage.Resources>
        <ResourceDictionary>
            <Color x:Key="PageBackgroundColor">#f8f9fa</Color>
        </ResourceDictionary>
    </ContentPage.Resources>
    
    <VerticalStackLayout BackgroundColor="{StaticResource PageBackgroundColor}">
        <!-- Content -->
    </VerticalStackLayout>
</ContentPage>
```

## Using Resources

### StaticResource
Lookup happens once at build time (faster):
```xml
<Label TextColor="{StaticResource PrimaryColor}" />
```

### DynamicResource
Lookup happens at runtime (responds to theme changes):
```xml
<Label TextColor="{DynamicResource PrimaryColor}" />
```

**When to use**:
- `StaticResource`: Fixed colors/values
- `DynamicResource`: Theme-aware colors (light/dark mode)

## Styles

### Basic Style
```xml
<Application.Resources>
    <Style x:Key="PrimaryButtonStyle" TargetType="Button">
        <Setter Property="BackgroundColor" Value="#007bff" />
        <Setter Property="TextColor" Value="White" />
        <Setter Property="FontSize" Value="18" />
        <Setter Property="CornerRadius" Value="8" />
        <Setter Property="Padding" Value="20,10" />
    </Style>
</Application.Resources>
```

**Usage**:
```xml
<Button Text="Submit" Style="{StaticResource PrimaryButtonStyle}" />
```

### Implicit Style (Auto-Apply)
Remove `x:Key` to apply to all buttons automatically:
```xml
<Style TargetType="Button">
    <Setter Property="BackgroundColor" Value="#007bff" />
    <Setter Property="TextColor" Value="White" />
</Style>

<!-- All buttons get this style automatically -->
<Button Text="Button 1" />
<Button Text="Button 2" />
```

### Style Inheritance (`BasedOn`)
```xml
<!-- Base button style -->
<Style x:Key="BaseButtonStyle" TargetType="Button">
    <Setter Property="FontSize" Value="16" />
    <Setter Property="CornerRadius" Value="8" />
</Style>

<!-- Primary button extends base -->
<Style x:Key="PrimaryButton" TargetType="Button" BasedOn="{StaticResource BaseButtonStyle}">
    <Setter Property="BackgroundColor" Value="#007bff" />
    <Setter Property="TextColor" Value="White" />
</Style>

<!-- Danger button extends base -->
<Style x:Key="DangerButton" TargetType="Button" BasedOn="{StaticResource BaseButtonStyle}">
    <Setter Property="BackgroundColor" Value="#dc3545" />
    <Setter Property="TextColor" Value="White" />
</Style>
```

## Organizing Styles

Create separate files for colors and styles:

### Resources/Styles/Colors.xaml
```xml
<ResourceDictionary xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
                    xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml">
    
    <!-- Brand Colors -->
    <Color x:Key="Primary">#007bff</Color>
    <Color x:Key="Secondary">#6c757d</Color>
    <Color x:Key="Success">#28a745</Color>
    <Color x:Key="Danger">#dc3545</Color>
    <Color x:Key="Warning">#ffc107</Color>
    
    <!-- Gray Scale -->
    <Color x:Key="White">#ffffff</Color>
    <Color x:Key="Gray100">#f8f9fa</Color>
    <Color x:Key="Gray200">#e9ecef</Color>
    <Color x:Key="Gray800">#343a40</Color>
    <Color x:Key="Black">#000000</Color>
</ResourceDictionary>
```

### Resources/Styles/Styles.xaml
```xml
<ResourceDictionary xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
                    xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml">
    
    <!-- Buttons -->
    <Style x:Key="PrimaryButton" TargetType="Button">
        <Setter Property="BackgroundColor" Value="{StaticResource Primary}" />
        <Setter Property="TextColor" Value="{StaticResource White}" />
        <Setter Property="FontSize" Value="16" />
        <Setter Property="CornerRadius" Value="8" />
    </Style>
    
    <!-- Labels -->
    <Style x:Key="HeaderLabel" TargetType="Label">
        <Setter Property="FontSize" Value="24" />
        <Setter Property="FontAttributes" Value="Bold" />
        <Setter Property="TextColor" Value="{StaticResource Gray800}" />
    </Style>
    
    <Style x:Key="BodyLabel" TargetType="Label">
        <Setter Property="FontSize" Value="16" />
        <Setter Property="TextColor" Value="{StaticResource Gray800}" />
    </Style>
</ResourceDictionary>
```

### App.xaml (Merge Dictionaries)
```xml
<Application xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:Class="MyApp.App">
    <Application.Resources>
        <ResourceDictionary>
            <ResourceDictionary.MergedDictionaries>
                <ResourceDictionary Source="Resources/Styles/Colors.xaml" />
                <ResourceDictionary Source="Resources/Styles/Styles.xaml" />
            </ResourceDictionary.MergedDictionaries>
        </ResourceDictionary>
    </Application.Resources>
</Application>
```

## Light and Dark Themes

### AppThemeBinding
Automatically switch colors based on system theme:

```xml
<Application.Resources>
    <Color x:Key="PageBackgroundLight">#ffffff</Color>
    <Color x:Key="PageBackgroundDark">#121212</Color>
    
    <Color x:Key="TextColorLight">#000000</Color>
    <Color x:Key="TextColorDark">#ffffff</Color>
</Application.Resources>

<!-- Usage with AppThemeBinding -->
<Label Text="Hello"
       TextColor="{AppThemeBinding Light={StaticResource TextColorLight}, 
                                    Dark={StaticResource TextColorDark}}" />
```

### Shortcut Syntax
```xml
<Label TextColor="{AppThemeBinding Light=#000000, Dark=#ffffff}" />
```

### Theme-Aware Styles
```xml
<Style TargetType="ContentPage">
    <Setter Property="BackgroundColor" 
            Value="{AppThemeBinding Light=#ffffff, Dark=#121212}" />
</Style>

<Style TargetType="Label">
    <Setter Property="TextColor" 
            Value="{AppThemeBinding Light=#000000, Dark=#ffffff}" />
</Style>
```

### Complete Theme Setup

**Colors.xaml**:
```xml
<ResourceDictionary xmlns="http://schemas.microsoft.com/dotnet/2021/maui">
    
    <!-- Light Theme Colors -->
    <Color x:Key="BackgroundLight">#ffffff</Color>
    <Color x:Key="SurfaceLight">#f5f5f5</Color>
    <Color x:Key="TextLight">#000000</Color>
    <Color x:Key="TextSecondaryLight">#666666</Color>
    
    <!-- Dark Theme Colors -->
    <Color x:Key="BackgroundDark">#121212</Color>
    <Color x:Key="SurfaceDark">#1e1e1e</Color>
    <Color x:Key="TextDark">#ffffff</Color>
    <Color x:Key="TextSecondaryDark">#aaaaaa</Color>
    
    <!-- Semantic Colors (theme-aware) -->
    <Color x:Key="PageBackground">{AppThemeBinding Light={StaticResource BackgroundLight}, Dark={StaticResource BackgroundDark}}</Color>
    <Color x:Key="CardBackground">{AppThemeBinding Light={StaticResource SurfaceLight}, Dark={StaticResource SurfaceDark}}</Color>
    <Color x:Key="TextPrimary">{AppThemeBinding Light={StaticResource TextLight}, Dark={StaticResource TextDark}}</Color>
    <Color x:Key="TextSecondary">{AppThemeBinding Light={StaticResource TextSecondaryLight}, Dark={StaticResource TextSecondaryDark}}</Color>
</ResourceDictionary>
```

**Styles.xaml**:
```xml
<ResourceDictionary xmlns="http://schemas.microsoft.com/dotnet/2021/maui">
    
    <!-- Page Style -->
    <Style TargetType="ContentPage" ApplyToDerivedTypes="True">
        <Setter Property="BackgroundColor" Value="{DynamicResource PageBackground}" />
    </Style>
    
    <!-- Card Style -->
    <Style x:Key="CardFrame" TargetType="Frame">
        <Setter Property="BackgroundColor" Value="{DynamicResource CardBackground}" />
        <Setter Property="BorderColor" Value="Transparent" />
        <Setter Property="CornerRadius" Value="12" />
        <Setter Property="Padding" Value="16" />
        <Setter Property="HasShadow" Value="True" />
    </Style>
    
    <!-- Text Styles -->
    <Style TargetType="Label">
        <Setter Property="TextColor" Value="{DynamicResource TextPrimary}" />
    </Style>
</ResourceDictionary>
```

## Manually Switching Themes

```csharp
// Get current theme
var currentTheme = Application.Current.UserAppTheme;

// Set theme
Application.Current.UserAppTheme = AppTheme.Dark;  // Force dark
Application.Current.UserAppTheme = AppTheme.Light; // Force light
Application.Current.UserAppTheme = AppTheme.Unspecified; // Follow system
```

**ViewModel with Theme Toggle**:
```csharp
public partial class SettingsViewModel : ObservableObject
{
    [ObservableProperty]
    private bool isDarkMode;
    
    public SettingsViewModel()
    {
        IsDarkMode = Application.Current.UserAppTheme == AppTheme.Dark;
    }
    
    [RelayCommand]
    private void ToggleTheme()
    {
        IsDarkMode = !IsDarkMode;
        Application.Current.UserAppTheme = IsDarkMode ? AppTheme.Dark : AppTheme.Light;
    }
}
```

**View**:
```xml
<Switch IsToggled="{Binding IsDarkMode}"
        OnColor="{StaticResource Primary}"
        ThumbColor="White"
        Toggled="OnThemeToggled" />
```

## Custom Fonts

### 1. Add Font Files
Place `.ttf` or `.otf` files in `Resources/Fonts/` folder.

### 2. Register in MauiProgram.cs
```csharp
public static MauiApp CreateMauiApp()
{
    var builder = MauiApp.CreateBuilder();
    builder
        .UseMauiApp<App>()
        .ConfigureFonts(fonts =>
        {
            fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
            fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
            fonts.AddFont("Roboto-Bold.ttf", "RobotoBold");
        });
    
    return builder.Build();
}
```

### 3. Use in XAML
```xml
<Label Text="Custom Font" 
       FontFamily="RobotoBold" 
       FontSize="24" />
```

### 4. Font Styles
```xml
<Style x:Key="TitleStyle" TargetType="Label">
    <Setter Property="FontFamily" Value="OpenSansSemibold" />
    <Setter Property="FontSize" Value="24" />
</Style>

<Style x:Key="BodyStyle" TargetType="Label">
    <Setter Property="FontFamily" Value="OpenSansRegular" />
    <Setter Property="FontSize" Value="16" />
</Style>
```

## Platform-Specific Styling

Use `OnPlatform` for different styles per platform:

```xml
<Button Text="Submit">
    <Button.HeightRequest>
        <OnPlatform x:TypeArguments="x:Double">
            <On Platform="iOS" Value="50" />
            <On Platform="Android" Value="48" />
            <On Platform="WinUI" Value="40" />
        </OnPlatform>
    </Button.HeightRequest>
</Button>
```

**In Styles**:
```xml
<Style TargetType="Button">
    <Setter Property="CornerRadius">
        <Setter.Value>
            <OnPlatform x:TypeArguments="x:Int32">
                <On Platform="iOS" Value="12" />
                <On Platform="Android" Value="8" />
            </OnPlatform>
        </Setter.Value>
    </Setter>
</Style>
```

## Complete Example

**Colors.xaml**:
```xml
<ResourceDictionary xmlns="http://schemas.microsoft.com/dotnet/2021/maui">
    <!-- Brand -->
    <Color x:Key="Primary">#007bff</Color>
    
    <!-- Light Theme -->
    <Color x:Key="BackgroundLight">#ffffff</Color>
    <Color x:Key="TextLight">#000000</Color>
    
    <!-- Dark Theme -->
    <Color x:Key="BackgroundDark">#121212</Color>
    <Color x:Key="TextDark">#ffffff</Color>
    
    <!-- Semantic (Theme-Aware) -->
    <Color x:Key="PageBg">{AppThemeBinding Light={StaticResource BackgroundLight}, Dark={StaticResource BackgroundDark}}</Color>
    <Color x:Key="TextColor">{AppThemeBinding Light={StaticResource TextLight}, Dark={StaticResource TextDark}}</Color>
</ResourceDictionary>
```

**Styles.xaml**:
```xml
<ResourceDictionary xmlns="http://schemas.microsoft.com/dotnet/2021/maui">
    
    <!-- Page -->
    <Style TargetType="ContentPage">
        <Setter Property="BackgroundColor" Value="{DynamicResource PageBg}" />
    </Style>
    
    <!-- Labels -->
    <Style TargetType="Label">
        <Setter Property="TextColor" Value="{DynamicResource TextColor}" />
        <Setter Property="FontSize" Value="16" />
    </Style>
    
    <Style x:Key="Header1" TargetType="Label" BasedOn="{StaticResource BaseLabel}">
        <Setter Property="FontSize" Value="32" />
        <Setter Property="FontAttributes" Value="Bold" />
    </Style>
    
    <Style x:Key="Header2" TargetType="Label">
        <Setter Property="FontSize" Value="24" />
        <Setter Property="FontAttributes" Value="Bold" />
    </Style>
    
    <!-- Buttons -->
    <Style x:Key="PrimaryButton" TargetType="Button">
        <Setter Property="BackgroundColor" Value="{StaticResource Primary}" />
        <Setter Property="TextColor" Value="White" />
        <Setter Property="FontSize" Value="16" />
        <Setter Property="CornerRadius" Value="8" />
        <Setter Property="Padding" Value="20,12" />
    </Style>
    
    <Style x:Key="SecondaryButton" TargetType="Button">
        <Setter Property="BackgroundColor" Value="Transparent" />
        <Setter Property="TextColor" Value="{StaticResource Primary}" />
        <Setter Property="BorderColor" Value="{StaticResource Primary}" />
        <Setter Property="BorderWidth" Value="2" />
        <Setter Property="CornerRadius" Value="8" />
    </Style>
    
    <!-- Card -->
    <Style x:Key="Card" TargetType="Frame">
        <Setter Property="BackgroundColor" 
                Value="{AppThemeBinding Light=#f8f9fa, Dark=#1e1e1e}" />
        <Setter Property="BorderColor" Value="Transparent" />
        <Setter Property="CornerRadius" Value="12" />
        <Setter Property="Padding" Value="16" />
        <Setter Property="HasShadow" Value="True" />
    </Style>
</ResourceDictionary>
```

**App.xaml**:
```xml
<Application xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:Class="MyApp.App">
    <Application.Resources>
        <ResourceDictionary>
            <ResourceDictionary.MergedDictionaries>
                <ResourceDictionary Source="Resources/Styles/Colors.xaml" />
                <ResourceDictionary Source="Resources/Styles/Styles.xaml" />
            </ResourceDictionary.MergedDictionaries>
        </ResourceDictionary>
    </Application.Resources>
</Application>
```

**Usage**:
```xml
<ContentPage>
    <ScrollView>
        <VerticalStackLayout Padding="20" Spacing="15">
            <Label Text="Welcome" Style="{StaticResource Header1}" />
            <Label Text="Subtitle" Style="{StaticResource Header2}" />
            
            <Frame Style="{StaticResource Card}">
                <VerticalStackLayout Spacing="10">
                    <Label Text="Card Title" FontAttributes="Bold" />
                    <Label Text="Card content goes here..." />
                </VerticalStackLayout>
            </Frame>
            
            <Button Text="Primary Action" Style="{StaticResource PrimaryButton}" />
            <Button Text="Secondary Action" Style="{StaticResource SecondaryButton}" />
        </VerticalStackLayout>
    </ScrollView>
</ContentPage>
```

## Best Practices

1. ✅ **Organize styles in separate files**: Colors.xaml, Styles.xaml
2. ✅ **Use meaningful names**: `PrimaryButton`, not `Button1`
3. ✅ **Support light/dark themes**: Use `AppThemeBinding`
4. ✅ **Use `DynamicResource` for themes**: Responds to theme changes
5. ✅ **Leverage `BasedOn`**: Reduce duplication
6. ✅ **Define semantic colors**: `PageBackground`, `TextPrimary`

## Common Mistakes

❌ **Hardcoding colors everywhere**
```xml
<Label TextColor="#000000" />  <!-- Use resource instead -->
```

❌ **Forgetting `DynamicResource` for themes**
```xml
<Label TextColor="{StaticResource TextColor}" />  <!-- Won't update on theme change -->
```

❌ **Not organizing styles**
```xml
<!-- Everything in App.xaml becomes messy quickly -->
```

## Key Takeaways

- Use `ResourceDictionary` for reusable colors/values
- `Style` defines reusable visual properties
- `StaticResource` for fixed values, `DynamicResource` for themes
- `AppThemeBinding` for light/dark theme support
- Organize styles in separate files (Colors.xaml, Styles.xaml)
- `BasedOn` for style inheritance
- Register custom fonts in `MauiProgram.cs`
- `OnPlatform` for platform-specific styling

## Next Steps

- Learn [Data Binding](03-Data-Binding.md) for dynamic UIs
- Explore [CollectionView](06-CollectionView.md) with styled items
- See [MVVM Pattern](02-MVVM-Pattern.md) for architecture
- Practice with [02.MvvmDependencyInjection](../02.MvvmDependencyInjection/)
