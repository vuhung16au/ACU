# Shell Navigation in .NET MAUI

## What is Shell?

**Shell** is a container that provides app navigation structure with tabs, flyouts, and URI-based routing.

**Benefits**:
- ✅ URI-based navigation (like web URLs)
- ✅ Tabs and flyout menus built-in
- ✅ Navigation bar with back button
- ✅ Query parameter support
- ✅ Hierarchical navigation

## AppShell.xaml Structure

```xml
<Shell xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
       xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
       xmlns:local="clr-namespace:MyApp"
       x:Class="MyApp.AppShell">

    <!-- Define what appears in your app -->
    
</Shell>
```

## Navigation Patterns

### 1. Tab Bar (Bottom Tabs)

```xml
<Shell>
    <TabBar>
        <ShellContent Title="Home" 
                      Icon="home.png"
                      ContentTemplate="{DataTemplate local:HomePage}" />
        
        <ShellContent Title="Search" 
                      Icon="search.png"
                      ContentTemplate="{DataTemplate local:SearchPage}" />
        
        <ShellContent Title="Profile" 
                      Icon="profile.png"
                      ContentTemplate="{DataTemplate local:ProfilePage}" />
    </TabBar>
</Shell>
```

### 2. Flyout Menu (Sidebar)

```xml
<Shell FlyoutBehavior="Flyout">
    <FlyoutItem Title="Home" Icon="home.png">
        <ShellContent ContentTemplate="{DataTemplate local:HomePage}" />
    </FlyoutItem>
    
    <FlyoutItem Title="Settings" Icon="settings.png">
        <ShellContent ContentTemplate="{DataTemplate local:SettingsPage}" />
    </FlyoutItem>
    
    <FlyoutItem Title="About" Icon="info.png">
        <ShellContent ContentTemplate="{DataTemplate local:AboutPage}" />
    </FlyoutItem>
</Shell>
```

### 3. Single Page (No Tabs/Flyout)

```xml
<Shell>
    <ShellContent ContentTemplate="{DataTemplate local:MainPage}" />
</Shell>
```

## URI-Based Navigation

Navigate using route strings (like web URLs):

```csharp
// Navigate forward
await Shell.Current.GoToAsync("detailspage");

// Navigate with parameter
await Shell.Current.GoToAsync($"detailspage?id={itemId}");

// Navigate back
await Shell.Current.GoToAsync("..");

// Navigate to root
await Shell.Current.GoToAsync("///mainpage");
```

## Registering Routes

### In AppShell.xaml.cs Constructor
```csharp
public partial class AppShell : Shell
{
    public AppShell()
    {
        InitializeComponent();
        
        // Register routes not in Shell visual hierarchy
        Routing.RegisterRoute("details", typeof(DetailsPage));
        Routing.RegisterRoute("edit", typeof(EditPage));
        Routing.RegisterRoute("settings/profile", typeof(ProfileEditPage));
    }
}
```

### Navigation Examples

```csharp
// From ViewModel
[RelayCommand]
private async Task GoToDetails(int id)
{
    await Shell.Current.GoToAsync($"details?id={id}");
}

// Nested route
await Shell.Current.GoToAsync("settings/profile");
```

## Passing Parameters

### 1. Query Parameters (Simple Data)

**Pass**:
```csharp
await Shell.Current.GoToAsync($"details?id={itemId}&name={itemName}");
```

**Receive with `[QueryProperty]`**:
```csharp
[QueryProperty(nameof(ItemId), "id")]
[QueryProperty(nameof(ItemName), "name")]
public partial class DetailsViewModel : ObservableObject
{
    [ObservableProperty]
    private int itemId;

    [ObservableProperty]
    private string itemName;

    // Called automatically when ItemId is set
    partial void OnItemIdChanged(int value)
    {
        LoadItemDetails(value);
    }
}
```

### 2. Navigation Parameters (Complex Objects)

**Pass**:
```csharp
var parameters = new Dictionary<string, object>
{
    { "item", selectedItem }
};

await Shell.Current.GoToAsync("details", parameters);
```

**Receive**:
```csharp
[QueryProperty(nameof(Item), "item")]
public partial class DetailsViewModel : ObservableObject
{
    [ObservableProperty]
    private TodoItem item;
}
```

## Navigation Syntax

### Relative Navigation

```csharp
// Go back one level
await Shell.Current.GoToAsync("..");

// Go back two levels
await Shell.Current.GoToAsync("../..");

// Navigate from current location
await Shell.Current.GoToAsync("details");
```

### Absolute Navigation

```csharp
// Navigate to root then to page
await Shell.Current.GoToAsync("///home");

// Full absolute path
await Shell.Current.GoToAsync("//home/details");
```

### Animated Navigation

```csharp
// Without animation
await Shell.Current.GoToAsync("details", animate: false);

// With animation (default)
await Shell.Current.GoToAsync("details", animate: true);
```

## Navigation Lifecycle

### OnNavigatedTo / OnNavigatedFrom

```csharp
public partial class DetailsPage : ContentPage
{
    protected override void OnNavigatedTo(NavigatedToEventArgs args)
    {
        base.OnNavigatedTo(args);
        // Page appeared - reload data if needed
    }

    protected override void OnNavigatedFrom(NavigatedFromEventArgs args)
    {
        base.OnNavigatedFrom(args);
        // Page is leaving - cleanup if needed
    }
}
```

### IQueryAttributable Interface

Alternative to `[QueryProperty]`:

```csharp
public partial class DetailsViewModel : ObservableObject, IQueryAttributable
{
    public void ApplyQueryAttributes(IDictionary<string, object> query)
    {
        if (query.ContainsKey("id"))
        {
            var id = int.Parse(query["id"].ToString());
            LoadDetails(id);
        }
    }
}
```

## Complete Example

### 1. Define AppShell

```xml
<Shell xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
       xmlns:local="clr-namespace:TodoApp"
       x:Class="TodoApp.AppShell">

    <TabBar>
        <ShellContent Title="Tasks" 
                      Icon="tasks.png"
                      Route="tasks"
                      ContentTemplate="{DataTemplate local:TaskListPage}" />
        
        <ShellContent Title="Settings" 
                      Icon="settings.png"
                      Route="settings"
                      ContentTemplate="{DataTemplate local:SettingsPage}" />
    </TabBar>
</Shell>
```

### 2. Register Routes

```csharp
public partial class AppShell : Shell
{
    public AppShell()
    {
        InitializeComponent();
        
        Routing.RegisterRoute("taskdetail", typeof(TaskDetailPage));
        Routing.RegisterRoute("taskedit", typeof(TaskEditPage));
    }
}
```

### 3. Navigate from List to Detail

**TaskListViewModel**:
```csharp
public partial class TaskListViewModel : ObservableObject
{
    [ObservableProperty]
    private ObservableCollection<TodoItem> tasks = new();

    [RelayCommand]
    private async Task SelectTask(TodoItem task)
    {
        await Shell.Current.GoToAsync($"taskdetail?id={task.Id}");
    }
}
```

### 4. Receive Parameters in Detail

**TaskDetailViewModel**:
```csharp
[QueryProperty(nameof(TaskId), "id")]
public partial class TaskDetailViewModel : ObservableObject
{
    private readonly ITodoService _todoService;

    [ObservableProperty]
    private int taskId;

    [ObservableProperty]
    private TodoItem task;

    public TaskDetailViewModel(ITodoService todoService)
    {
        _todoService = todoService;
    }

    partial void OnTaskIdChanged(int value)
    {
        LoadTask(value);
    }

    private async void LoadTask(int id)
    {
        Task = await _todoService.GetTaskByIdAsync(id);
    }

    [RelayCommand]
    private async Task Edit()
    {
        await Shell.Current.GoToAsync($"taskedit?id={TaskId}");
    }

    [RelayCommand]
    private async Task GoBack()
    {
        await Shell.Current.GoToAsync("..");
    }
}
```

## Shell Features

### Navigation Bar Customization

```xml
<ShellContent Title="Home">
    <ShellContent.ContentTemplate>
        <DataTemplate>
            <ContentPage
                Shell.NavBarIsVisible="True"
                Shell.BackButtonBehavior="{Binding BackButtonBehavior}"
                Shell.TitleView="{Binding CustomTitleView}">
                <!-- Page content -->
            </ContentPage>
        </DataTemplate>
    </ShellContent.ContentTemplate>
</ShellContent>
```

### Back Button Behavior

```xml
<ContentPage Shell.BackButtonBehavior="{Binding BackButtonBehavior}">
    <!-- Content -->
</ContentPage>
```

```csharp
public BackButtonBehavior BackButtonBehavior => new BackButtonBehavior
{
    Command = new Command(async () => await OnBackPressed()),
    TextOverride = "Cancel"
};
```

### Custom Title View

```xml
<ContentPage>
    <Shell.TitleView>
        <HorizontalStackLayout>
            <Image Source="logo.png" HeightRequest="30" />
            <Label Text="My App" VerticalOptions="Center" />
        </HorizontalStackLayout>
    </Shell.TitleView>
</ContentPage>
```

## Deep Linking

Handle external URLs:

```csharp
// In MauiProgram.cs or App.xaml.cs
protected override async void OnAppLinkRequestReceived(Uri uri)
{
    base.OnAppLinkRequestReceived(uri);
    
    if (uri.Host == "myapp.com" && uri.Segments[1] == "task")
    {
        var taskId = uri.Segments[2];
        await Shell.Current.GoToAsync($"taskdetail?id={taskId}");
    }
}
```

## Best Practices

1. ✅ **Register all routes in `AppShell.xaml.cs`**: Centralized navigation config
2. ✅ **Use `[QueryProperty]` for parameters**: Type-safe and automatic
3. ✅ **Relative navigation for back** (`.`): Flexible and maintainable
4. ✅ **Absolute navigation for root** (`///`): Reset navigation stack
5. ✅ **Avoid manual `Navigation.PushAsync`**: Use Shell navigation
6. ✅ **DI inject Pages**: Enable Shell to resolve dependencies

## Common Mistakes

❌ **Route not registered**
```csharp
await Shell.Current.GoToAsync("details"); // Crashes if route not registered
```

❌ **Wrong parameter name**
```csharp
// Passing "id", but receiving "itemId"
await Shell.Current.GoToAsync($"details?id={id}");

[QueryProperty(nameof(ItemId), "itemId")] // Mismatch!
```

❌ **Forgetting `partial` keyword**
```csharp
public class MainViewModel // Missing 'partial' - [QueryProperty] won't work
```

## Navigation Flow Example

```
TaskListPage (tasks)
    ↓ Select task
TaskDetailPage (taskdetail?id=5)
    ↓ Edit
TaskEditPage (taskedit?id=5)
    ↓ Save & Go Back (..)
TaskDetailPage (taskdetail?id=5)
    ↓ Go Back (..)
TaskListPage (tasks)
```

## Key Takeaways

- Shell provides URI-based navigation (like web)
- Register routes in `AppShell.xaml.cs`
- Use `GoToAsync()` with route strings
- Pass parameters via query strings or navigation dictionary
- Receive parameters with `[QueryProperty]` attribute
- Relative navigation: `..` (back), Absolute: `///` (root)
- Shell supports tabs, flyout, and nested navigation

## Next Steps

- Learn [Layouts Guide](05-Layouts-Guide.md) for UI structure
- Explore [CollectionView](06-CollectionView.md) for lists
- Practice with [03.ShellNavigation](../03.ShellNavigation/)
