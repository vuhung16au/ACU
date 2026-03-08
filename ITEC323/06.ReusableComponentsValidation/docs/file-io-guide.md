# File I/O Guide

## Overview

Work with text files (CSV, TXT, JSON) for simple data storage without a database.

**Use Cases:** Import/export data, configuration files, logging, CSV reports

## Key Concepts

### 1. Always Use Async Methods

```csharp
// ❌ Bad: Blocks web server threads
var data = File.ReadAllText("file.txt");

// ✅ Good: Non-blocking
var data = await File.ReadAllTextAsync("file.txt");
```

### 2. Cross-Platform Paths

```csharp
// ❌ Windows-only
var path = "C:\\Data\\users.csv";

// ✅ Works everywhere
var path = Path.Combine("Data", "users.csv");
```

### 3. Get Application Root Path

```csharp
public class IndexModel : PageModel
{
    private readonly IWebHostEnvironment _env;
    
    public IndexModel(IWebHostEnvironment env) => _env = env;
    
    public async Task OnGetAsync()
    {
        var filePath = Path.Combine(_env.ContentRootPath, "Data", "users.csv");
        // Use filePath...
    }
}
```

## Basic File Operations

```csharp
// Read entire file
string[] lines = await File.ReadAllLinesAsync(path);
string content = await File.ReadAllTextAsync(path);

// Write (overwrites existing)
await File.WriteAllLinesAsync(path, lines);
await File.WriteAllTextAsync(path, content);

// Append to file
await File.AppendAllTextAsync(path, "new line\n");

// Check existence
if (File.Exists(path)) { /* ... */ }
```

## Working with CSV Files

**CSV Format:** `Id,Name,Email,Phone,DateOfBirth`

### Reading CSV

```csharp
public async Task<List<User>> LoadUsersAsync()
{
    var path = Path.Combine(_env.ContentRootPath, "Data", "users.csv");
    if (!File.Exists(path)) return new List<User>();
    
    var lines = await File.ReadAllLinesAsync(path);
    var users = new List<User>();
    
    for (int i = 1; i < lines.Length; i++)  // Skip header
    {
        var fields = lines[i].Split(',');
        users.Add(new User
        {
            Id = int.Parse(fields[0]),
            Name = fields[1],
            Email = fields[2],
            Phone = fields[3],
            DateOfBirth = DateTime.Parse(fields[4])
        });
    }
    return users;
}
```

### Writing CSV

```csharp
public async Task SaveUsersAsync(List<User> users)
{
    var path = Path.Combine(_env.ContentRootPath, "Data", "users.csv");
    
    // Ensure directory exists
    Directory.CreateDirectory(Path.GetDirectoryName(path));
    
    var lines = new List<string> { "Id,Name,Email,Phone,DateOfBirth" };
    foreach (var u in users)
        lines.Add($"{u.Id},{u.Name},{u.Email},{u.Phone},{u.DateOfBirth:yyyy-MM-dd}");
    
    await File.WriteAllLinesAsync(path, lines);
}
```

## Error Handling

```csharp
try
{
    var lines = await File.ReadAllLinesAsync(path);
}
catch (FileNotFoundException)
{
    return new List<User>();  // Return empty if file missing
}
catch (IOException ex)
{
    _logger.LogError(ex, "File error");
    throw;
}
```

**Common Exceptions:** `FileNotFoundException`, `IOException`, `UnauthorizedAccessException`

## Best Practices

✅ **Do:**
- Always use async file methods
- Use `Path.Combine()` for cross-platform paths
- Handle `FileNotFoundException` gracefully
- Create directories before writing
- Validate data before saving

❌ **Don't:**
- Use sync methods (blocks threads)
- Hardcode file paths
- Store sensitive data in plain text
- Ignore exceptions

## Quick Reference

```csharp
// Core operations
string[] lines = await File.ReadAllLinesAsync(path);
await File.WriteAllLinesAsync(path, lines);
await File.AppendAllTextAsync(path, "text\n");

// Path utilities
var path = Path.Combine("Data", "file.csv");
Directory.CreateDirectory(Path.GetDirectoryName(path));

// Check existence
if (File.Exists(path)) { /* ... */ }
```
