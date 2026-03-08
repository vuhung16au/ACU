# FRD-Copilot: AI Agent Instructions for .NET MAUI Development

## Context
This module teaches students how to build cross-platform mobile and desktop applications using .NET MAUI (Multi-platform App UI). Students are first-time learners of mobile app development who have completed ASP.NET Core Razor Pages coursework.

## Target Audience
- Beginner mobile developers with .NET web development background
- Students familiar with C#, MVVM patterns, and dependency injection from ASP.NET Core
- Learners transitioning from web to mobile/desktop app development

## MAUI-Specific Guidelines

### Project Structure
- Use single-project architecture with multi-targeting
- Keep `Platforms/` folder for platform-specific code
- Organize shared code in `Views/`, `ViewModels/`, `Models/`, `Services/`
- Use `Resources/` for images, fonts, colors, and styles

### XAML and Code Patterns
- **XAML UI**: Use XAML for layouts with data binding to ViewModels
- **MVVM**: Implement Model-View-ViewModel pattern using .NET Community Toolkit
- **Observable Properties**: Use `[ObservableProperty]` attribute for bindable properties
- **Commands**: Use `[RelayCommand]` for UI actions
- **Dependency Injection**: Register services and ViewModels in `MauiProgram.cs`

### Naming Conventions
- **Pages**: Suffix with `Page` (e.g., `MainPage.xaml`, `DetailPage.xaml`)
- **ViewModels**: Suffix with `ViewModel` (e.g., `MainViewModel.cs`)
- **Services**: Suffix with `Service` (e.g., `DataService.cs`)
- **Models**: Use domain names (e.g., `Task.cs`, `TodoItem.cs`)

### Documentation Requirements
All MAUI code must include:
- XML comments on ViewModels, services, and public methods
- Inline comments explaining XAML binding expressions
- Comments on platform-specific code explaining why it's needed
- README explaining how to run on different platforms (Android, macOS, Windows)

### Common MAUI Patterns

#### ViewModel with Community Toolkit
```csharp
/// <summary>
/// ViewModel for managing a list of tasks.
/// Demonstrates MVVM pattern with observable collections and commands.
/// </summary>
public partial class TaskListViewModel : ObservableObject
{
    /// <summary>
    /// Collection of tasks displayed in the UI.
    /// </summary>
    [ObservableProperty]
    private ObservableCollection<TodoItem> tasks;

    /// <summary>
    /// Adds a new task to the collection.
    /// </summary>
    [RelayCommand]
    private async Task AddTask()
    {
        // Implementation
    }
}
```

#### Shell Navigation
```csharp
// Register routes in AppShell.xaml.cs
Routing.RegisterRoute("taskdetail", typeof(TaskDetailPage));

// Navigate with parameters
await Shell.Current.GoToAsync($"taskdetail?taskId={taskId}");

// Receive parameters in ViewModel
[QueryProperty(nameof(TaskId), "taskId")]
public partial class TaskDetailViewModel : ObservableObject
{
    [ObservableProperty]
    private int taskId;
}
```

#### Dependency Injection in MauiProgram.cs
```csharp
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
    
    // Register ViewModels
    builder.Services.AddTransient<MainViewModel>();
    
    // Register Pages
    builder.Services.AddTransient<MainPage>();

    return builder.Build();
}
```

### Platform-Specific Code
- Use `#if ANDROID`, `#if IOS`, `#if WINDOWS`, `#if MACCATALYST` directives
- Keep platform code minimal and well-documented
- Explain why platform-specific code is needed

### Educational Focus
- **Progressive Complexity**: Start with simple pages, add MVVM, then navigation
- **Explain Differences**: Highlight differences between MAUI and Razor Pages
- **Cross-Platform Concepts**: Emphasize single codebase, shared resources
- **Practical Examples**: Use relatable scenarios (task lists, photo apps)

### Testing on macOS
- Primary targets: **macOS (Mac Catalyst)** and **Android emulator**
- Windows target can be shown but may not be testable on student Macs
- iOS requires Xcode and can be optional for students without Apple Developer accounts

### Common Student Challenges
1. **XAML Syntax**: Students may struggle with XML-based UI; provide clear examples
2. **Data Binding**: Explain how `{Binding}` connects XAML to ViewModels
3. **Async Commands**: Show how async/await works in UI commands
4. **Platform Setup**: Guide through emulator setup (Android SDK, Xcode)
5. **Navigation**: Clarify route registration and parameter passing

### Security and Best Practices
- **No Hardcoded Secrets**: Use secure storage APIs (`SecureStorage.SetAsync`)
- **Permissions**: Always request and handle permissions properly
- **Error Handling**: Wrap async commands in try-catch blocks
- **Null Safety**: Use nullable reference types and null checks

### Project Build Requirements
Each project must:
- Build successfully with `dotnet build`
- Run on at least one platform (macOS or Android)
- Include QUICKSTART.md with platform-specific run instructions
- Have README.md with learning objectives
- Follow the repository structure from AGENTS.md

### MAUI Workload
Ensure students have MAUI workload installed:
```bash
dotnet workload install maui
```

## Resources
- [Microsoft MAUI Documentation](https://learn.microsoft.com/en-us/dotnet/maui/)
- [.NET Community Toolkit MVVM](https://learn.microsoft.com/en-us/dotnet/communitytoolkit/mvvm/)
- [MAUI Shell Navigation](https://learn.microsoft.com/en-us/dotnet/maui/fundamentals/shell/navigation)

## Alignment with Course
This module builds on:
- **Week 3-6**: C# programming, Razor Pages, MVVM patterns
- **Week 7-8**: Dependency injection, services
- **Week 9**: Data persistence (applicable to mobile SQLite)

Students apply familiar .NET patterns in a new cross-platform mobile/desktop context.
