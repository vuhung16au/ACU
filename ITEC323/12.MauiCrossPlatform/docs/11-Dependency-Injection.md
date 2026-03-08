# Dependency Injection in .NET MAUI

## What is Dependency Injection?

**Dependency Injection (DI)** is a design pattern where objects receive their dependencies from external sources rather than creating them internally.

**Benefits**:
- ✅ Testable code (mock dependencies)
- ✅ Loose coupling
- ✅ Centralized configuration
- ✅ Easier maintenance

**Without DI**:
```csharp
public class MainViewModel
{
    private readonly DataService _service = new DataService(); // Tightly coupled
}
```

**With DI**:
```csharp
public class MainViewModel
{
    private readonly IDataService _service;
    
    public MainViewModel(IDataService service) // Injected
    {
        _service = service;
    }
}
```

## MauiProgram.cs - DI Container

MAUI uses **Microsoft.Extensions.DependencyInjection** (same as ASP.NET Core).

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

        // Register services here
        RegisterServices(builder.Services);

        return builder.Build();
    }

    private static void RegisterServices(IServiceCollection services)
    {
        // Services
        services.AddSingleton<IDataService, DataService>();
        services.AddTransient<IApiClient, ApiClient>();
        
        // ViewModels
        services.AddTransient<MainViewModel>();
        services.AddTransient<DetailsViewModel>();
        
        // Pages
        services.AddTransient<MainPage>();
        services.AddTransient<DetailsPage>();
    }
}
```

## Service Lifetimes

| Lifetime | Behavior | Use Case |
|----------|----------|----------|
| **Singleton** | One instance for app lifetime | Database, API client, shared state |
| **Transient** | New instance every time | ViewModels, Pages, lightweight services |
| **Scoped** | One instance per scope | Not commonly used in MAUI |

### Singleton
```csharp
// One instance shared across entire app
services.AddSingleton<IDatabaseService, DatabaseService>();
services.AddSingleton<IUserPreferences, UserPreferencesService>();
```

**When to use**:
- Database connections
- Configuration
- Caching services
- Shared state

### Transient
```csharp
// New instance every time it's requested
services.AddTransient<MainViewModel>();
services.AddTransient<MainPage>();
```

**When to use**:
- ViewModels (each page gets fresh instance)
- Pages
- Short-lived services

### Scoped
```csharp
// One instance per scope (rarely used in MAUI)
services.AddScoped<IOrderService, OrderService>();
```

**When to use**: Only in specialized scenarios (e.g., multi-window apps)

## Registering Services

### Interface + Implementation
```csharp
// Recommended: Allows mocking for tests
services.AddSingleton<IApiClient, ApiClient>();
services.AddTransient<IDataService, DataService>();
```

### Concrete Type
```csharp
// Direct registration (harder to test)
services.AddSingleton<ApiClient>();
services.AddTransient<DataService>();
```

### Factory Method
```csharp
// Custom initialization logic
services.AddSingleton<IDatabaseService>(sp => 
{
    var dbPath = Path.Combine(FileSystem.AppDataDirectory, "app.db");
    return new DatabaseService(dbPath);
});
```

### Pre-existing Instance
```csharp
// Use specific instance
var config = new AppConfiguration { ApiUrl = "https://api.example.com" };
services.AddSingleton(config);
```

## Constructor Injection

### In ViewModels
```csharp
public partial class MainViewModel : ObservableObject
{
    private readonly IDataService _dataService;
    private readonly INavigationService _navigationService;

    public MainViewModel(
        IDataService dataService,
        INavigationService navigationService)
    {
        _dataService = dataService;
        _navigationService = navigationService;
    }

    [RelayCommand]
    private async Task LoadData()
    {
        var data = await _dataService.GetDataAsync();
        Items = new ObservableCollection<Item>(data);
    }
}
```

### In Pages
```csharp
public partial class MainPage : ContentPage
{
    public MainPage(MainViewModel viewModel)
    {
        InitializeComponent();
        BindingContext = viewModel;  // ViewModel injected automatically
    }
}
```

### Chained Dependencies
```csharp
// Service depends on another service
public class DataService : IDataService
{
    private readonly IApiClient _apiClient;
    private readonly IDatabaseService _database;

    public DataService(IApiClient apiClient, IDatabaseService database)
    {
        _apiClient = apiClient;
        _database = database;
    }
}
```

## Complete Example

### 1. Define Service Interface
```csharp
public interface ITodoService
{
    Task<List<TodoItem>> GetTodosAsync();
    Task AddTodoAsync(TodoItem item);
    Task DeleteTodoAsync(int id);
}
```

### 2. Implement Service
```csharp
public class TodoService : ITodoService
{
    private readonly List<TodoItem> _todos = new();

    public Task<List<TodoItem>> GetTodosAsync()
    {
        return Task.FromResult(_todos.ToList());
    }

    public Task AddTodoAsync(TodoItem item)
    {
        _todos.Add(item);
        return Task.CompletedTask;
    }

    public Task DeleteTodoAsync(int id)
    {
        var item = _todos.FirstOrDefault(t => t.Id == id);
        if (item != null)
            _todos.Remove(item);
        return Task.CompletedTask;
    }
}
```

### 3. Register in MauiProgram.cs
```csharp
private static void RegisterServices(IServiceCollection services)
{
    // Service (Singleton - shared across app)
    services.AddSingleton<ITodoService, TodoService>();
    
    // ViewModel (Transient - new instance per page)
    services.AddTransient<TodoListViewModel>();
    
    // Page (Transient)
    services.AddTransient<TodoListPage>();
}
```

### 4. Inject into ViewModel
```csharp
public partial class TodoListViewModel : ObservableObject
{
    private readonly ITodoService _todoService;

    [ObservableProperty]
    private ObservableCollection<TodoItem> todos = new();

    public TodoListViewModel(ITodoService todoService)
    {
        _todoService = todoService;
    }

    [RelayCommand]
    private async Task LoadTodos()
    {
        var items = await _todoService.GetTodosAsync();
        Todos = new ObservableCollection<TodoItem>(items);
    }

    [RelayCommand]
    private async Task DeleteTodo(int id)
    {
        await _todoService.DeleteTodoAsync(id);
        await LoadTodos();
    }
}
```

### 5. Inject into Page
```csharp
public partial class TodoListPage : ContentPage
{
    public TodoListPage(TodoListViewModel viewModel)
    {
        InitializeComponent();
        BindingContext = viewModel;
    }

    protected override async void OnAppearing()
    {
        base.OnAppearing();
        
        // Trigger data load when page appears
        var vm = (TodoListViewModel)BindingContext;
        await vm.LoadTodosCommand.ExecuteAsync(null);
    }
}
```

## Navigation with DI

### Shell Navigation
```csharp
// Register pages and routes
private static void RegisterServices(IServiceCollection services)
{
    // Pages
    services.AddTransient<MainPage>();
    services.AddTransient<DetailsPage>();
    
    // Register routes in AppShell.xaml.cs constructor
    Routing.RegisterRoute("details", typeof(DetailsPage));
}
```

Navigate:
```csharp
[RelayCommand]
private async Task GoToDetails(int itemId)
{
    await Shell.Current.GoToAsync($"details?id={itemId}");
}
```

## Accessing Services Manually (When Needed)

Sometimes you need to resolve services outside of constructors:

```csharp
// In App.xaml.cs or elsewhere
var dataService = Application.Current.Handler.MauiContext.Services
    .GetRequiredService<IDataService>();
```

**Avoid this when possible** - prefer constructor injection.

## Testing with DI

DI makes testing easy by allowing mock services:

```csharp
// Test setup
var mockService = new Mock<ITodoService>();
mockService.Setup(s => s.GetTodosAsync())
    .ReturnsAsync(new List<TodoItem>
    {
        new TodoItem { Id = 1, Title = "Test" }
    });

var viewModel = new TodoListViewModel(mockService.Object);

// Test
await viewModel.LoadTodosCommand.ExecuteAsync(null);

Assert.Single(viewModel.Todos);
```

## Common DI Patterns

### Configuration Service
```csharp
public class AppConfiguration
{
    public string ApiBaseUrl { get; set; }
    public string ApiKey { get; set; }
}

// In MauiProgram.cs
services.AddSingleton(new AppConfiguration
{
    ApiBaseUrl = "https://api.example.com",
    ApiKey = "your-api-key-from-config"
});
```

### Platform-Specific Services
```csharp
// Interface
public interface IPlatformService
{
    string GetPlatformName();
}

// Android Implementation (in Platforms/Android)
public class AndroidPlatformService : IPlatformService
{
    public string GetPlatformName() => "Android";
}

// iOS Implementation (in Platforms/iOS)
public class IosPlatformService : IPlatformService
{
    public string GetPlatformName() => "iOS";
}

// Register conditionally
#if ANDROID
services.AddSingleton<IPlatformService, AndroidPlatformService>();
#elif IOS
services.AddSingleton<IPlatformService, IosPlatformService>();
#endif
```

### HttpClient Registration
```csharp
services.AddHttpClient<IApiClient, ApiClient>(client =>
{
    client.BaseAddress = new Uri("https://api.example.com");
    client.DefaultRequestHeaders.Add("Accept", "application/json");
    client.Timeout = TimeSpan.FromSeconds(30);
});
```

## Logging Integration

```csharp
// In MauiProgram.cs
builder.Logging.AddDebug();

// In services
public class DataService : IDataService
{
    private readonly ILogger<DataService> _logger;

    public DataService(ILogger<DataService> logger)
    {
        _logger = logger;
    }

    public async Task<List<Item>> GetDataAsync()
    {
        _logger.LogInformation("Loading data...");
        
        try
        {
            var data = await LoadDataFromApi();
            _logger.LogInformation("Loaded {Count} items", data.Count);
            return data;
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Failed to load data");
            throw;
        }
    }
}
```

## Best Practices

1. ✅ **Register all dependencies in `MauiProgram.cs`**: Centralized configuration
2. ✅ **Use interfaces for services**: Enables testing and flexibility
3. ✅ **Singleton for shared state**: Database, cache, configuration
4. ✅ **Transient for ViewModels**: Each page gets fresh instance
5. ✅ **Inject Pages into Shell**: Enable navigation with DI
6. ✅ **Constructor injection over property injection**: More explicit

## Common Mistakes

❌ **Not registering Page**
```csharp
// Page not registered - navigation fails
await Shell.Current.GoToAsync("details");
```

❌ **Wrong lifetime**
```csharp
// ViewModel as Singleton - state shared between pages (usually wrong)
services.AddSingleton<MainViewModel>();
```

❌ **Circular dependencies**
```csharp
// Service A depends on B, B depends on A - DI fails
```

## Key Takeaways

- MAUI uses same DI system as ASP.NET Core
- Register services in `MauiProgram.cs`
- Service lifetimes: Singleton (shared), Transient (new each time)
- Constructor injection for ViewModels and Pages
- Interfaces enable testing with mocks
- Register both Pages and ViewModels for Shell navigation

## Next Steps

- Review [MVVM Pattern](02-MVVM-Pattern.md) for ViewModel design
- Learn [Shell Navigation](04-Shell-Navigation.md) with DI
- Practice with [02.MvvmDependencyInjection](../02.MvvmDependencyInjection/)
