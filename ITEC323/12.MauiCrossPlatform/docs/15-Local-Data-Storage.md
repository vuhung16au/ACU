# Local Data Storage in .NET MAUI

## Storage Options Overview

MAUI provides multiple ways to store data locally:

| Method | Use Case | Persistence | Size Limit |
|--------|----------|-------------|------------|
| **Preferences** | Simple key-value pairs | App lifetime | Small (KB) |
| **SecureStorage** | Passwords, tokens | App lifetime | Small (KB) |
| **FileSystem** | Files, JSON, images | Permanent | Large (GB) |
| **SQLite** | Structured data, queries | Permanent | Large (GB) |

## 1. Preferences (Key-Value Storage)

Store simple settings and flags.

### Save Data
```csharp
// Save different types
Preferences.Default.Set("username", "john_doe");
Preferences.Default.Set("counter", 42);
Preferences.Default.Set("is_logged_in", true);
Preferences.Default.Set("last_updated", DateTime.Now.ToString());
```

### Read Data
```csharp
// Read with default values
var username = Preferences.Default.Get("username", "Guest");
var counter = Preferences.Default.Get("counter", 0);
var isLoggedIn = Preferences.Default.Get("is_logged_in", false);
```

### Remove Data
```csharp
// Remove single key
Preferences.Default.Remove("username");

// Clear all preferences
Preferences.Default.Clear();
```

### Check if Key Exists
```csharp
bool hasUsername = Preferences.Default.ContainsKey("username");
```

### Example: Settings ViewModel
```csharp
public partial class SettingsViewModel : ObservableObject
{
    [ObservableProperty]
    private bool notificationsEnabled;
    
    [ObservableProperty]
    private string displayName;
    
    public SettingsViewModel()
    {
        LoadSettings();
    }
    
    private void LoadSettings()
    {
        NotificationsEnabled = Preferences.Default.Get("notifications_enabled", true);
        DisplayName = Preferences.Default.Get("display_name", "User");
    }
    
    [RelayCommand]
    private void SaveSettings()
    {
        Preferences.Default.Set("notifications_enabled", NotificationsEnabled);
        Preferences.Default.Set("display_name", DisplayName);
        
        Shell.Current.DisplayAlert("Success", "Settings saved", "OK");
    }
}
```

**Best for**: Flags, user preferences, small strings  
**Not for**: Sensitive data (use SecureStorage), large data (use FileSystem)

## 2. SecureStorage (Encrypted Storage)

Store sensitive data like passwords and tokens securely.

### Save Secure Data
```csharp
await SecureStorage.Default.SetAsync("auth_token", "abc123xyz789");
await SecureStorage.Default.SetAsync("api_key", "sk_live_xyz");
```

### Read Secure Data
```csharp
var token = await SecureStorage.Default.GetAsync("auth_token");
if (token != null)
{
    // Use token
}
```

### Remove Secure Data
```csharp
// Remove single key
SecureStorage.Default.Remove("auth_token");

// Remove all
SecureStorage.Default.RemoveAll();
```

### Example: Auth Service
```csharp
public class AuthService
{
    private const string TokenKey = "auth_token";
    private const string UserIdKey = "user_id";
    
    public async Task<bool> IsLoggedInAsync()
    {
        var token = await SecureStorage.Default.GetAsync(TokenKey);
        return !string.IsNullOrEmpty(token);
    }
    
    public async Task SaveTokenAsync(string token, string userId)
    {
        await SecureStorage.Default.SetAsync(TokenKey, token);
        await SecureStorage.Default.SetAsync(UserIdKey, userId);
    }
    
    public async Task<string> GetTokenAsync()
    {
        return await SecureStorage.Default.GetAsync(TokenKey);
    }
    
    public void Logout()
    {
        SecureStorage.Default.Remove(TokenKey);
        SecureStorage.Default.Remove(UserIdKey);
    }
}
```

**Platform Storage**:
- **Android**: Android KeyStore
- **iOS**: Keychain
- **Windows**: Windows Credential Locker

**Best for**: Passwords, API tokens, sensitive keys  
**Not for**: Large data, non-sensitive data

## 3. FileSystem (File Storage)

Store files, JSON data, images permanently.

### App Directories
```csharp
// App data directory (persistent)
var appDataPath = FileSystem.AppDataDirectory;
// iOS: /Documents
// Android: /data/user/0/[package]/files

// Cache directory (can be cleared by system)
var cachePath = FileSystem.CacheDirectory;
// iOS: /Library/Caches
// Android: /cache
```

### Save Text File
```csharp
public async Task SaveTextAsync(string filename, string content)
{
    var filePath = Path.Combine(FileSystem.AppDataDirectory, filename);
    await File.WriteAllTextAsync(filePath, content);
}
```

### Read Text File
```csharp
public async Task<string> ReadTextAsync(string filename)
{
    var filePath = Path.Combine(FileSystem.AppDataDirectory, filename);
    
    if (File.Exists(filePath))
    {
        return await File.ReadAllTextAsync(filePath);
    }
    
    return null;
}
```

### Save JSON
```csharp
public async Task SaveTodosAsync(List<TodoItem> todos)
{
    var json = JsonSerializer.Serialize(todos, new JsonSerializerOptions
    {
        WriteIndented = true
    });
    
    var filePath = Path.Combine(FileSystem.AppDataDirectory, "todos.json");
    await File.WriteAllTextAsync(filePath, json);
}

public async Task<List<TodoItem>> LoadTodosAsync()
{
    var filePath = Path.Combine(FileSystem.AppDataDirectory, "todos.json");
    
    if (!File.Exists(filePath))
        return new List<TodoItem>();
    
    var json = await File.ReadAllTextAsync(filePath);
    return JsonSerializer.Deserialize<List<TodoItem>>(json) ?? new List<TodoItem>();
}
```

### Save Image
```csharp
public async Task SaveImageAsync(Stream imageStream, string filename)
{
    var filePath = Path.Combine(FileSystem.AppDataDirectory, filename);
    
    using var fileStream = File.Create(filePath);
    await imageStream.CopyToAsync(fileStream);
}

public ImageSource LoadImageSource(string filename)
{
    var filePath = Path.Combine(FileSystem.AppDataDirectory, filename);
    
    if (File.Exists(filePath))
    {
        return ImageSource.FromFile(filePath);
    }
    
    return null;
}
```

### Example: Data Service
```csharp
public class TodoDataService
{
    private const string FileName = "todos.json";
    private readonly string _filePath;
    
    public TodoDataService()
    {
        _filePath = Path.Combine(FileSystem.AppDataDirectory, FileName);
    }
    
    public async Task<List<TodoItem>> GetAllAsync()
    {
        if (!File.Exists(_filePath))
            return new List<TodoItem>();
        
        var json = await File.ReadAllTextAsync(_filePath);
        return JsonSerializer.Deserialize<List<TodoItem>>(json) ?? new List<TodoItem>();
    }
    
    public async Task SaveAllAsync(List<TodoItem> todos)
    {
        var json = JsonSerializer.Serialize(todos, new JsonSerializerOptions
        {
            WriteIndented = true
        });
        
        await File.WriteAllTextAsync(_filePath, json);
    }
    
    public async Task AddAsync(TodoItem todo)
    {
        var todos = await GetAllAsync();
        todo.Id = todos.Any() ? todos.Max(t => t.Id) + 1 : 1;
        todos.Add(todo);
        await SaveAllAsync(todos);
    }
    
    public async Task UpdateAsync(TodoItem todo)
    {
        var todos = await GetAllAsync();
        var index = todos.FindIndex(t => t.Id == todo.Id);
        if (index >= 0)
        {
            todos[index] = todo;
            await SaveAllAsync(todos);
        }
    }
    
    public async Task DeleteAsync(int id)
    {
        var todos = await GetAllAsync();
        todos.RemoveAll(t => t.Id == id);
        await SaveAllAsync(todos);
    }
}
```

**Best for**: JSON data, images, documents  
**Not for**: Complex queries (use SQLite)

## 4. SQLite Database

Structured data with queries and relationships.

### Install Package
Add to `.csproj`:
```xml
<ItemGroup>
    <PackageReference Include="sqlite-net-pcl" Version="1.9.172" />
    <PackageReference Include="SQLitePCLRaw.bundle_green" Version="2.1.8" />
</ItemGroup>
```

### Define Model
```csharp
using SQLite;

public class TodoItem
{
    [PrimaryKey, AutoIncrement]
    public int Id { get; set; }
    
    [MaxLength(250), Unique]
    public string Title { get; set; }
    
    public string Description { get; set; }
    
    public bool IsCompleted { get; set; }
    
    public DateTime CreatedAt { get; set; }
    
    public DateTime? DueDate { get; set; }
}
```

### Database Service
```csharp
public class TodoDatabase
{
    private readonly SQLiteAsyncConnection _database;
    
    public TodoDatabase()
    {
        var dbPath = Path.Combine(FileSystem.AppDataDirectory, "todos.db");
        _database = new SQLiteAsyncConnection(dbPath);
        _database.CreateTableAsync<TodoItem>().Wait();
    }
    
    // Get all
    public Task<List<TodoItem>> GetAllAsync()
    {
        return _database.Table<TodoItem>().ToListAsync();
    }
    
    // Get by ID
    public Task<TodoItem> GetByIdAsync(int id)
    {
        return _database.Table<TodoItem>()
            .Where(t => t.Id == id)
            .FirstOrDefaultAsync();
    }
    
    // Insert
    public Task<int> SaveAsync(TodoItem todo)
    {
        if (todo.Id != 0)
        {
            return _database.UpdateAsync(todo);
        }
        else
        {
            return _database.InsertAsync(todo);
        }
    }
    
    // Delete
    public Task<int> DeleteAsync(TodoItem todo)
    {
        return _database.DeleteAsync(todo);
    }
    
    // Query
    public Task<List<TodoItem>> GetActiveAsync()
    {
        return _database.Table<TodoItem>()
            .Where(t => !t.IsCompleted)
            .OrderByDescending(t => t.CreatedAt)
            .ToListAsync();
    }
    
    public Task<List<TodoItem>> SearchAsync(string query)
    {
        return _database.Table<TodoItem>()
            .Where(t => t.Title.Contains(query) || t.Description.Contains(query))
            .ToListAsync();
    }
}
```

### Register in DI
```csharp
// MauiProgram.cs
builder.Services.AddSingleton<TodoDatabase>();
```

### Use in ViewModel
```csharp
public partial class TodoListViewModel : ObservableObject
{
    private readonly TodoDatabase _database;
    
    [ObservableProperty]
    private ObservableCollection<TodoItem> todos;
    
    public TodoListViewModel(TodoDatabase database)
    {
        _database = database;
    }
    
    [RelayCommand]
    private async Task LoadTodos()
    {
        var items = await _database.GetAllAsync();
        Todos = new ObservableCollection<TodoItem>(items);
    }
    
    [RelayCommand]
    private async Task AddTodo(string title)
    {
        var todo = new TodoItem
        {
            Title = title,
            CreatedAt = DateTime.Now
        };
        
        await _database.SaveAsync(todo);
        await LoadTodos();
    }
    
    [RelayCommand]
    private async Task DeleteTodo(TodoItem todo)
    {
        await _database.DeleteAsync(todo);
        Todos.Remove(todo);
    }
}
```

**Best for**: Structured data, queries, large datasets  
**Not for**: Small settings (use Preferences)

## Comparison Table

| Feature | Preferences | SecureStorage | FileSystem | SQLite |
|---------|-------------|---------------|------------|--------|
| **Setup** | None | None | None | NuGet package |
| **Data Size** | Small | Small | Large | Large |
| **Security** | None | Encrypted | None | None |
| **Queries** | No | No | No | Yes |
| **Async** | No | Yes | Yes | Yes |
| **Best For** | Settings | Passwords | Files, JSON | Structured data |

## Best Practices

1. ✅ **Use correct storage type**: Match storage to data needs
2. ✅ **Preferences for settings**: Don't overuse Preferences
3. ✅ **SecureStorage for secrets**: Never store passwords in Preferences
4. ✅ **Wrap in service**: Abstract storage behind interface
5. ✅ **Handle errors**: File operations can fail
6. ✅ **Async for large data**: Don't block UI thread
7. ✅ **Clean up old data**: Remove unused files periodically

## Common Mistakes

❌ **Storing sensitive data in Preferences**
```csharp
Preferences.Set("password", "secret123");  // ❌ Not secure!

await SecureStorage.SetAsync("password", "secret123");  // ✅ Secure
```

❌ **Not handling file not found**
```csharp
var json = await File.ReadAllTextAsync(path);  // ❌ Crashes if file missing

if (File.Exists(path))  // ✅ Check first
{
    var json = await File.ReadAllTextAsync(path);
}
```

❌ **Blocking UI with sync I/O**
```csharp
var data = File.ReadAllText(path);  // ❌ Blocks UI

var data = await File.ReadAllTextAsync(path);  // ✅ Async
```

## Complete Example: Todo App with SQLite

**Model**:
```csharp
public class TodoItem
{
    [PrimaryKey, AutoIncrement]
    public int Id { get; set; }
    
    public string Title { get; set; }
    public bool IsCompleted { get; set; }
    public DateTime CreatedAt { get; set; } = DateTime.Now;
}
```

**Database**:
```csharp
public class TodoDatabase
{
    private readonly SQLiteAsyncConnection _db;
    
    public TodoDatabase()
    {
        var path = Path.Combine(FileSystem.AppDataDirectory, "todos.db");
        _db = new SQLiteAsyncConnection(path);
        _db.CreateTableAsync<TodoItem>().Wait();
    }
    
    public Task<List<TodoItem>> GetAllAsync() => _db.Table<TodoItem>().ToListAsync();
    public Task<int> SaveAsync(TodoItem todo) => todo.Id == 0 ? _db.InsertAsync(todo) : _db.UpdateAsync(todo);
    public Task<int> DeleteAsync(TodoItem todo) => _db.DeleteAsync(todo);
}
```

**ViewModel**:
```csharp
public partial class TodoViewModel : ObservableObject
{
    private readonly TodoDatabase _db;
    
    [ObservableProperty]
    private ObservableCollection<TodoItem> todos;
    
    public TodoViewModel(TodoDatabase db)
    {
        _db = db;
        LoadTodos();
    }
    
    [RelayCommand]
    private async Task LoadTodos()
    {
        var items = await _db.GetAllAsync();
        Todos = new ObservableCollection<TodoItem>(items);
    }
    
    [RelayCommand]
    private async Task AddTodo(string title)
    {
        if (string.IsNullOrWhiteSpace(title)) return;
        
        await _db.SaveAsync(new TodoItem { Title = title });
        await LoadTodos();
    }
    
    [RelayCommand]
    private async Task DeleteTodo(TodoItem todo)
    {
        await _db.DeleteAsync(todo);
        Todos.Remove(todo);
    }
}
```

## Key Takeaways

- **Preferences**: Simple key-value storage for settings
- **SecureStorage**: Encrypted storage for sensitive data
- **FileSystem**: File-based storage for JSON, images
- **SQLite**: Relational database for structured data with queries
- Choose storage based on data type and size
- Always handle file operation errors
- Use async methods for I/O operations
- Wrap storage in services for testability

## Next Steps

- Learn [Deployment](14-Deployment.md) for publishing apps
- See [MVVM Pattern](02-MVVM-Pattern.md) for architecture
- Practice with [06.ComprehensiveTaskListApp](../06.ComprehensiveTaskListApp/)
