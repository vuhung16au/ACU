# Code Generation Guidelines for AI Agents

This document provides specific guidelines for AI coding agents (Cursor, Copilot, Antigravity, etc.) when generating code for the ITEC323 repository.

## General Principles

- **Educational Focus**: Code should be simple, readable, and easy to understand for students learning .NET and Android development for the first time
- **Avoid Over-Engineering**: Don't use complex patterns, advanced features, or unnecessary abstractions that may confuse beginners
- **Incremental Complexity**: Start with simple implementations and gradually introduce more advanced concepts when appropriate

## .NET Code Generation

### Language and Framework
- **Primary Language**: C# for all .NET projects
- **Target Framework**: .NET 8.0 (LTS) with compatibility for .NET 9
- **Project Types**: Console applications, ASP.NET Core web applications, class libraries

### Naming Conventions
Follow standard C# naming conventions:

- **PascalCase** for:
  - Class names: `HelloWorld`, `UserService`, `StudentController`
  - Method names: `GetStudentById()`, `ProcessData()`, `ValidateInput()`
  - Properties: `FirstName`, `StudentId`, `IsActive`
  - Public fields (avoid when possible)
  - Namespaces: `DotNetHelloWorldCLI.Services`

- **camelCase** for:
  - Local variables: `studentName`, `itemCount`, `isValid`
  - Private fields: `_privateField` (with underscore prefix)
  - Method parameters: `studentId`, `firstName`, `age`

- **UPPER_CASE** for:
  - Constants: `MAX_ATTEMPTS`, `DEFAULT_TIMEOUT`

### Code Structure

```csharp
// Example of well-structured C# code
namespace ProjectName.Feature
{
    /// <summary>
    /// Brief description of the class purpose
    /// </summary>
    public class ExampleClass
    {
        private readonly IService _service;
        
        /// <summary>
        /// Initializes a new instance of the ExampleClass
        /// </summary>
        /// <param name="service">The service dependency</param>
        public ExampleClass(IService service)
        {
            _service = service ?? throw new ArgumentNullException(nameof(service));
        }
        
        /// <summary>
        /// Processes the data and returns result
        /// </summary>
        /// <param name="input">The input data to process</param>
        /// <returns>The processed result</returns>
        public string ProcessData(string input)
        {
            // Implementation with clear, simple logic
            if (string.IsNullOrWhiteSpace(input))
            {
                throw new ArgumentException("Input cannot be null or empty", nameof(input));
            }
            
            return _service.Process(input);
        }
    }
}
```

### Project Structure
When creating new .NET projects:

```
ProjectName/
├── ProjectName.csproj
├── Program.cs
├── README.md
├── QUICKSTART.md
├── FRD.md
├── docs/
│   └── [specific documentation]
├── src/
│   └── [source files for larger projects]
└── tests/
    └── [unit test files]
```

### Common Patterns

1. **Dependency Injection**: Use built-in DI container for ASP.NET Core projects
2. **Async/Await**: Use for I/O operations and long-running tasks
3. **Error Handling**: Use try-catch with specific exception types, provide meaningful error messages
4. **LINQ**: Use for collection operations, but keep queries simple and readable

## Android Code Generation

### Language and Framework
- **Primary Language**: Java for Android projects
- **Target SDK**: Android API level 24 (Android 7.0) minimum, targeting latest stable
- **Build System**: Gradle

### Naming Conventions
Follow standard Java/Android naming conventions:

- **PascalCase** for class names: `MainActivity`, `UserAdapter`, `LoginActivity`
- **camelCase** for methods, variables, and parameters: `onCreate()`, `userName`, `findViewById()`
- **UPPER_CASE** for constants: `MAX_LENGTH`, `REQUEST_CODE`

### Android Project Structure

```
AndroidProjectName/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/acu/projectname/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle
├── README.md
├── QUICKSTART.md
├── FRD.md
└── docs/
    └── [specific documentation]
```

**Note**: Detailed Android guidelines will be added when the Android portion of the course begins.

## Security Best Practices

When generating code:

1. **Never hardcode sensitive information**:
   - API keys
   - Connection strings
   - Passwords
   - Tokens

2. **Use environment variables or configuration files**:
   ```csharp
   // Good: Read from configuration
   var apiKey = configuration["ApiSettings:ApiKey"];
   
   // Bad: Hardcoded
   var apiKey = "sk_live_12345678";
   ```

3. **Validate user input** before processing
4. **Use parameterized queries** for database operations
5. **Avoid exposing sensitive data** in error messages or logs

## Code Quality

### Keep It Simple
- Write straightforward, linear code when possible
- Avoid premature optimization
- One concept per method/class
- Limit method length (prefer 10-20 lines when possible)

### Make It Educational
- Include comments explaining **why**, not just **what**
- Add examples in documentation
- Use descriptive variable names that explain purpose
- Break complex operations into smaller, named methods

### Follow Repository Standards
- Check `.gitignore` rules before adding files
- Follow existing folder structures
- Maintain consistency with existing code style

## Examples to Avoid

```csharp
// ❌ Avoid: Too complex for beginners
public async Task<Result<T>> ExecuteAsync<T>(
    Func<Task<T>> operation, 
    IRetryPolicy policy,
    CancellationToken cancellationToken = default)
{
    // Complex retry logic with circuit breaker pattern
    // ...
}

// ✅ Prefer: Simple and clear
public async Task<Student> GetStudentAsync(int studentId)
{
    if (studentId <= 0)
    {
        throw new ArgumentException("Student ID must be positive", nameof(studentId));
    }
    
    return await _context.Students.FindAsync(studentId);
}
```

## When in Doubt

1. Check existing code in the repository for patterns
2. Prioritize clarity over cleverness
3. Ask yourself: "Would a beginner understand this?"
4. Refer to the main [AGENTS.md](../../AGENTS.md) for general guidelines
