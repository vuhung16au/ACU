# AI Agent Contribution Guidelines

This document provides guidelines for AI coding agents (Cursor, Copilot, Antigravity, etc.) contributing to the ITEC323 repository.

## Overview

This repository contains educational materials and projects for the ITEC323 unit. The projects cover .NET and Android development, targeting students who are learning these technologies for the first time.

**Target Audience**: First-time learners of .NET and Android development  
**Educational Focus**: Keep all code, documentation, and examples simple, clear, and beginner-friendly

## Quick Reference

| Area | Key Points | Detailed Guide |
|------|-----------|----------------|
| **Code** | • C# for .NET, Java for Android<br>• .NET 10.0 / C# 14<br>• PascalCase classes/methods, camelCase variables<br>• Keep it simple for beginners | [Code Generation Guidelines](docs/agents/code-generation.md) |
| **.NET App Skills** | • Prefer ASP.NET Core Razor Pages for beginner web apps<br>• Teach clear interaction design, UI, and UX basics<br>• Build responsive layouts for MAUI, Razor, and Blazor<br>• Apply simple web and backend security practices | [\.NET App Implementation Guidelines](docs/agents/dotnet-app-implementation.md) |
| **Docs** | • XML comments on all public members<br>• README, QUICKSTART, FRD for each project<br>• Explain "why", not just "what" | [Documentation Guidelines](docs/agents/documentation.md) |
| **Tests** | • xUnit for .NET, JUnit for Android<br>• Tests in `tests/` folder<br>• Descriptive test names<br>• AAA pattern (Arrange-Act-Assert) | [Testing Guidelines](docs/agents/testing.md) |

## Core Principles

### 1. Educational First
- **Simplicity over cleverness**: Write straightforward code that beginners can understand
- **Avoid over-engineering**: Don't use complex patterns or advanced features unnecessarily  
- **Explain concepts**: Include comments that teach, not just describe
- **Progressive complexity**: Start simple, introduce advanced concepts gradually

### 2. Code Quality Standards

#### Naming Conventions

**C# (.NET Projects)**:
- `PascalCase` for classes, methods, properties: `StudentService`, `GetStudentById()`, `FirstName`
- `camelCase` for variables and parameters: `studentId`, `firstName`, `totalCount`
- `_camelCase` for private fields: `_repository`, `_logger`
- `UPPER_CASE` for constants: `MAX_RETRIES`, `DEFAULT_TIMEOUT`

**Java (Android Projects)**:
- `PascalCase` for classes: `MainActivity`, `StudentAdapter`
- `camelCase` for methods, variables: `onCreate()`, `studentName`
- `UPPER_CASE` for constants: `MAX_LENGTH`, `REQUEST_CODE`

#### Documentation Requirements

All code must be well-documented:

```csharp
/// <summary>
/// Retrieves a student by their unique identifier.
/// </summary>
/// <param name="studentId">The unique ID of the student to retrieve</param>
/// <returns>The student object if found</returns>
/// <exception cref="ArgumentException">Thrown when studentId is invalid</exception>
public Student GetStudentById(int studentId)
{
    if (studentId <= 0)
    {
        throw new ArgumentException("Student ID must be positive", nameof(studentId));
    }
    
    return _repository.FindById(studentId);
}
```

### 3. Project Structure

Every sub-project must follow this structure:

```
ProjectName/
├── README.md              # Project overview and learning objectives
├── QUICKSTART.md          # Step-by-step setup and running instructions
├── FRD.md                 # Functional Requirements Document
├── ProjectName.csproj     # Project file (or .sln for solutions)
├── Program.cs             # Main entry point
├── docs/                  # Detailed documentation
│   └── [technical docs]
├── src/                   # Source files (for larger projects)
│   └── ProjectName/
└── tests/                 # Unit tests
    └── ProjectName.Tests/
```

### 4. Technology Standards

#### .NET Projects
- **Target Framework**: .NET 10.0 (LTS)
- **Compatibility**: Must work with .NET 10
- **Language**: C# 14
- **Testing**: xUnit with FluentAssertions
- **Project Types**: Console apps, ASP.NET Core web apps, class libraries

#### .NET App Skills
- **Razor Pages first**: Prefer ASP.NET Core Razor Pages for beginner-friendly web apps
- **Interaction design**: Keep navigation, forms, and feedback simple, clear, and consistent
- **Responsive design**: Support phone, tablet, and desktop layouts in .NET MAUI, Razor, and Blazor
- **Security basics**: Validate input, encode output, parameterize queries, and protect APIs and forms

#### Android Projects
- **Primary Language**: Java
- **Target SDK**: Android API 24+ (Android 7.0+)
- **Build System**: Gradle
- **Testing**: JUnit 4, Espresso

> **Note**: Detailed Android guidelines will be added when Android coursework begins.

### 5. Security Best Practices

**Never include in code**:
- ❌ API keys
- ❌ Connection strings
- ❌ Passwords or credentials
- ❌ Tokens or secrets

**Always use**:
- ✅ Environment variables
- ✅ Configuration files
- ✅ Secure credential storage

```csharp
// ❌ Bad: Hardcoded
var apiKey = "sk_live_abc123xyz";

// ✅ Good: From configuration
var apiKey = configuration["ApiSettings:ApiKey"];
```

### 6. Testing Requirements

All new functionality must include unit tests:

- **Location**: Tests in `tests/` folder, separate from source
- **Framework**: xUnit for .NET, JUnit for Android
- **Naming**: `MethodName_Scenario_ExpectedBehavior`
- **Structure**: Follow AAA pattern (Arrange-Act-Assert)
- **Coverage**: Test public methods, edge cases, and error conditions

```csharp
[Fact]
public void GetStudentById_ValidId_ReturnsStudent()
{
    // Arrange
    var service = new StudentService();
    
    // Act
    var result = service.GetStudentById(12345);
    
    // Assert
    result.Should().NotBeNull();
    result.Id.Should().Be(12345);
}
```

## Repository Rules

### Version Control

- Follow `.gitignore` rules (see root `.gitignore`)
- Don't commit:
  - `bin/` and `obj/` directories
  - IDE-specific files (except `.vscode/` if configured for all)
  - User-specific settings
  - Build artifacts
  - Sensitive information

### File Organization

- Keep related files together
- Follow existing folder structure
- Use consistent naming across the repository
- Place documentation with the code it describes

## Required Documentation Files

### README.md
- Project name and brief description
- Learning objectives
- Prerequisites
- Quick links to other documentation
- Technology stack

### QUICKSTART.md  
- Installation steps
- Build instructions
- How to run the project
- Expected output
- Common troubleshooting

### FRD.md (Functional Requirements Document)
- Purpose statement
- Functional requirements (numbered, prioritized)
- Non-functional requirements
- Constraints
- Success criteria

### docs/ Folder
- Technical documentation
- Architecture diagrams
- API documentation
- Tutorial materials

## Working with This Repository

### Before Contributing

1. **Understand the audience**: Students learning .NET/Android for the first time
2. **Review existing projects**: See `AspNetHelloWorld` and `DotNetHelloWorldCLI` for examples
3. **Check related guidelines**: Read the detailed guides below for your contribution type
4. **Follow the structure**: Maintain consistency with existing projects

### Making Changes

1. **Keep it simple**: Every line should be understandable by a beginner
2. **Document thoroughly**: Add comments that explain concepts, not just code
3. **Test your code**: Include unit tests for new functionality
4. **Update documentation**: Keep all docs current with code changes
5. **Review checklist**:
   - [ ] Code follows naming conventions
   - [ ] All public members have XML documentation
   - [ ] Unit tests are included and passing
   - [ ] Required documentation files are present/updated
   - [ ] No sensitive information in code
   - [ ] `dotnet build` runs without errors (when applicable)
   - [ ] `dotnet run` runs without errors (when applicable)
   - [ ] Builds successfully with .NET 10.0 and C# 14

## Detailed Guidelines

For in-depth instructions specific to different types of contributions, see:

### 📝 [Code Generation Guidelines](docs/agents/code-generation.md)
Detailed instructions for:
- Writing C# and Java code
- Following naming conventions
- Implementing common patterns
- Structuring projects
- Security best practices
- Code quality standards

### 🌐 [\.NET App Implementation Guidelines](docs/agents/dotnet-app-implementation.md)
Short guidance for:
- Building beginner-friendly ASP.NET Core Razor Pages apps
- Teaching interaction design, UI, and UX fundamentals
- Creating responsive layouts for .NET MAUI, Razor, and Blazor
- Applying simple web, API, and database security practices

### 📚 [Documentation Guidelines](docs/agents/documentation.md)  
Comprehensive guide for:
- Creating README, QUICKSTART, and FRD files
- Writing XML documentation comments
- Using inline comments effectively
- Markdown formatting standards
- Documentation maintenance
- Making content educational

### 🧪 [Testing Guidelines](docs/agents/testing.md)
Complete testing reference for:
- Setting up test projects with xUnit
- Writing unit tests and integration tests
- Using mocking frameworks
- Test naming conventions
- AAA pattern and best practices
- Running and debugging tests

## Examples

### Example Project Structure

See existing projects for reference:
- `AspNetHelloWorld/` - Simple ASP.NET Core web application
- `DotNetHelloWorldCLI/` - Basic console application

### Example Code Style

```csharp
namespace ITEC323.Examples
{
    /// <summary>
    /// Demonstrates basic student data management.
    /// This example shows CRUD operations in a simple, educational context.
    /// </summary>
    public class StudentManager
    {
        private readonly List<Student> _students;
        
        /// <summary>
        /// Initializes a new instance of StudentManager with an empty student list.
        /// </summary>
        public StudentManager()
        {
            _students = new List<Student>();
        }
        
        /// <summary>
        /// Adds a new student to the manager.
        /// </summary>
        /// <param name="student">The student to add</param>
        /// <exception cref="ArgumentNullException">Thrown when student is null</exception>
        public void AddStudent(Student student)
        {
            // Validate input before processing
            if (student == null)
            {
                throw new ArgumentNullException(nameof(student));
            }
            
            // Check for duplicate before adding
            if (_students.Any(s => s.Id == student.Id))
            {
                throw new InvalidOperationException(
                    $"Student with ID {student.Id} already exists");
            }
            
            _students.Add(student);
        }
    }
}
```

## Questions or Issues?

When uncertain about how to proceed:

1. **Check existing code**: Look at similar implementations in the repository
2. **Prioritize clarity**: If in doubt, choose the simpler, more readable approach
3. **Ask yourself**: "Would a beginner understand this?"
4. **Refer to guidelines**: Use the detailed guides linked above

## Keeping Guidelines Updated

These guidelines should evolve with the course:

- Guidelines are reviewed at the start of each semester
- Updates are made when new technologies or patterns are introduced
- Android-specific guidelines will be expanded when that coursework begins
- Feedback from students and instructors helps improve these guidelines
