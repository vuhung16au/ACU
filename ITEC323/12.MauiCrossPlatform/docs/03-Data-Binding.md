# Data Binding in .NET MAUI

## What is Data Binding?

Data binding connects UI controls (View) to data sources (ViewModel) automatically. When data changes, UI updates; when user interacts, data updates.

```
┌──────────────┐         Binding         ┌──────────────┐
│   XAML UI    │ ◄───────────────────► │  ViewModel   │
│  (Entry)     │    (TwoWay/OneWay)     │  (Property)  │
└──────────────┘                         └──────────────┘
```

**Without Binding** (Manual):
```csharp
nameLabel.Text = viewModel.Name;  // Update manually
```

**With Binding** (Automatic):
```xml
<Label Text="{Binding Name}" />   <!-- Updates automatically -->
```

## Binding Syntax

### Basic Binding
```xml
<Label Text="{Binding PropertyName}" />
```

### Binding with Path
```xml
<!-- Bind to nested property -->
<Label Text="{Binding User.Address.City}" />
```

### Binding to Parent BindingContext
```xml
<!-- Inside CollectionView item template -->
<Button Command="{Binding Source={RelativeSource AncestorType={x:Type vm:MainViewModel}}, Path=DeleteCommand}"
        CommandParameter="{Binding .}" />
```

## Binding Modes

| Mode | Direction | Use Case |
|------|-----------|----------|
| `OneWay` | Source → Target | Labels, read-only displays |
| `TwoWay` | Source ↔ Target | Entry, Editor, input controls |
| `OneWayToSource` | Target → Source | Rare, special cases |
| `OneTime` | Source → Target (once) | Static data that never changes |
| `Default` | Control-dependent | Entry=TwoWay, Label=OneWay |

### OneWay (Source → Target)

```xml
<!-- Label updates when Title changes, but Title doesn't change from Label -->
<Label Text="{Binding Title, Mode=OneWay}" />
```

**Use for**: Labels, images, read-only displays

### TwoWay (Source ↔ Target)

```xml
<!-- Entry updates Name, Name updates Entry -->
<Entry Text="{Binding Name, Mode=TwoWay}" />
```

**Use for**: Entry, Editor, CheckBox, Slider (user input)

**Default for**: Most input controls

### OneTime (One-Time Copy)

```xml
<!-- Only reads value once at initialization -->
<Label Text="{Binding Title, Mode=OneTime}" />
```

**Use for**: Static configuration values

## Common Binding Scenarios

### 1. Label (Display Text)
```xml
<Label Text="{Binding FullName}" 
       FontSize="18" 
       TextColor="{Binding IsActive, Converter={StaticResource BoolToColorConverter}}" />
```

### 2. Entry (Text Input)
```xml
<Entry Text="{Binding Username}" 
       Placeholder="Enter username"
       MaxLength="20" />
```

### 3. Button Command
```xml
<Button Text="Save" 
        Command="{Binding SaveCommand}"
        CommandParameter="{Binding SelectedItem}" />
```

### 4. CheckBox
```xml
<CheckBox IsChecked="{Binding IsAccepted}" />
```

### 5. Slider
```xml
<Slider Value="{Binding Volume}" 
        Minimum="0" 
        Maximum="100" />
<Label Text="{Binding Volume, StringFormat='Volume: {0:F0}%'}" />
```

### 6. CollectionView
```xml
<CollectionView ItemsSource="{Binding Items}">
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:TodoItem">
            <Label Text="{Binding Title}" />
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

## StringFormat

Format data for display without converters:

```xml
<!-- Currency -->
<Label Text="{Binding Price, StringFormat='${0:F2}'}" />
<!-- Result: $19.99 -->

<!-- Date -->
<Label Text="{Binding CreatedDate, StringFormat='{0:MMMM dd, yyyy}'}" />
<!-- Result: March 08, 2026 -->

<!-- Percentage -->
<Label Text="{Binding Progress, StringFormat='Progress: {0:P0}'}" />
<!-- Result: Progress: 75% -->

<!-- Custom text -->
<Label Text="{Binding Count, StringFormat='Total: {0} items'}" />
<!-- Result: Total: 42 items -->
```

## Value Converters

Convert data between ViewModel and View when direct binding isn't enough.

### Example: Bool to Color Converter

**1. Create Converter**
```csharp
using System.Globalization;

public class BoolToColorConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        return (bool)value ? Colors.Green : Colors.Red;
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}
```

**2. Register in Resources**
```xml
<ContentPage.Resources>
    <converters:BoolToColorConverter x:Key="BoolToColor" />
</ContentPage.Resources>
```

**3. Use in Binding**
```xml
<Label Text="Status" 
       TextColor="{Binding IsActive, Converter={StaticResource BoolToColor}}" />
```

### Common Converters

**Invert Boolean**
```csharp
public class InvertBoolConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        return !(bool)value;
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        return !(bool)value;
    }
}
```

**Null to Bool (Check if object exists)**
```csharp
public class NullToBoolConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        return value != null;
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}
```

**Usage**:
```xml
<Button Text="View Details" 
        IsEnabled="{Binding SelectedItem, Converter={StaticResource NullToBool}}" />
```

## FallbackValue

Provide a default when binding fails:

```xml
<Label Text="{Binding Description, FallbackValue='No description available'}" />

<Image Source="{Binding PhotoUrl, FallbackValue='placeholder.png'}" />
```

## TargetNullValue

Specify value when source is `null`:

```xml
<Label Text="{Binding Username, TargetNullValue='Guest'}" />
<!-- Shows "Guest" when Username is null -->
```

## x:DataType (Compiled Bindings)

Compiled bindings provide **compile-time validation** and **better performance**.

### Without x:DataType (Runtime Binding)
```xml
<Label Text="{Binding Title}" />
<!-- Error only at runtime if property doesn't exist -->
```

### With x:DataType (Compiled Binding)
```xml
<ContentPage xmlns:vm="clr-namespace:MyApp.ViewModels"
             x:DataType="vm:MainViewModel">
    <Label Text="{Binding Title}" />
    <!-- Compile error if Title doesn't exist in MainViewModel -->
</ContentPage>
```

**Always use `x:DataType` for**:
- Better performance (10-20% faster)
- Compile-time safety
- IntelliSense support

## Binding Relative Sources

### Bind to Page's BindingContext
```xml
<CollectionView ItemsSource="{Binding Items}">
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:Item">
            <!-- Bind to ViewModel command, not Item -->
            <Button Command="{Binding Source={RelativeSource AncestorType={x:Type vm:MainViewModel}}, Path=DeleteCommand}"
                    CommandParameter="{Binding .}" />
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

### Bind to Self (Control's own property)
```xml
<Entry x:Name="searchEntry" 
       Placeholder="Search..." />
<Label Text="{Binding Source={x:Reference searchEntry}, Path=Text}" />
```

## Binding Context Hierarchy

```xml
<ContentPage x:DataType="vm:MainViewModel">
    <!-- BindingContext: MainViewModel -->
    <Label Text="{Binding Title}" />
    
    <VerticalStackLayout BindingContext="{Binding CurrentUser}">
        <!-- BindingContext: User object -->
        <Label Text="{Binding Name}" />
        <Label Text="{Binding Email}" />
    </VerticalStackLayout>
</ContentPage>
```

## Multi-Binding

Combine multiple bindings (MAUI .NET 7+):

```xml
<Label>
    <Label.Text>
        <MultiBinding StringFormat="{}{0} {1}">
            <Binding Path="FirstName" />
            <Binding Path="LastName" />
        </MultiBinding>
    </Label.Text>
</Label>
<!-- Result: "John Doe" -->
```

## Complete Example

**ViewModel**:
```csharp
public partial class LoginViewModel : ObservableObject
{
    [ObservableProperty]
    private string username;

    [ObservableProperty]
    private string password;

    [ObservableProperty]
    [NotifyPropertyChangedFor(nameof(StatusMessage))]
    private bool isLoggedIn;

    public string StatusMessage => IsLoggedIn ? "Logged In" : "Please log in";

    [RelayCommand(CanExecute = nameof(CanLogin))]
    private async Task Login()
    {
        await Task.Delay(1000); // Simulate API call
        IsLoggedIn = true;
    }

    private bool CanLogin()
    {
        return !string.IsNullOrWhiteSpace(Username) && 
               !string.IsNullOrWhiteSpace(Password);
    }
}
```

**View**:
```xml
<ContentPage xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:Class="MyApp.LoginPage"
             x:DataType="vm:LoginViewModel">
    
    <VerticalStackLayout Padding="20" Spacing="10">
        <Label Text="{Binding StatusMessage}" 
               FontSize="18" 
               FontAttributes="Bold" />
        
        <Entry Placeholder="Username" 
               Text="{Binding Username}" />
        
        <Entry Placeholder="Password" 
               Text="{Binding Password}" 
               IsPassword="True" />
        
        <Button Text="Login" 
                Command="{Binding LoginCommand}" />
        
        <ActivityIndicator IsRunning="{Binding LoginCommand.IsRunning}" />
    </VerticalStackLayout>
</ContentPage>
```

## Debugging Bindings

### Enable Binding Diagnostics
```csharp
// In MauiProgram.cs
builder.Logging.AddDebug();
```

### Check Output Window
Binding errors appear in debug output:
```
[0:] Binding: 'UserName' property not found on 'MainViewModel'
```

### Common Errors

❌ **Typo in property name**
```xml
<Label Text="{Binding UserName}" />  <!-- Property is "Username" -->
```

❌ **Missing BindingContext**
```csharp
// Forgot to set BindingContext
public MainPage()
{
    InitializeComponent();  // BindingContext is null!
}
```

❌ **Wrong DataType**
```xml
<ContentPage x:DataType="vm:WrongViewModel">
    <Label Text="{Binding PropertyFromDifferentViewModel}" />
</ContentPage>
```

## Best Practices

1. ✅ **Always use `x:DataType`**: Compile-time safety + performance
2. ✅ **Use `StringFormat` before converters**: Simpler for formatting
3. ✅ **Avoid code-behind subscriptions**: Let bindings handle updates
4. ✅ **Use `RelativeSource` for nested bindings**: Access parent context
5. ✅ **Provide `FallbackValue`**: Graceful degradation

## Performance Tips

- Use `OneTime` for static data
- Use `OneWay` for read-only displays
- Avoid complex converters in lists (slow scrolling)
- Use `x:DataType` for compiled bindings

## Key Takeaways

- Data binding connects UI to ViewModel automatically
- Binding modes: `OneWay` (display), `TwoWay` (input)
- Use `x:DataType` for compile-time safety and performance
- `StringFormat` for simple formatting, converters for complex logic
- `FallbackValue` and `TargetNullValue` for null safety
- Compiled bindings are 10-20% faster than runtime bindings

## Next Steps

- Learn [Shell Navigation](04-Shell-Navigation.md) for multi-page apps
- Explore [Layouts Guide](05-Layouts-Guide.md) for UI structure
- Practice with [02.MvvmDependencyInjection](../02.MvvmDependencyInjection/)
