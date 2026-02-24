# Documentation Guidelines for AI Agents

This document provides guidelines for AI agents on creating and maintaining documentation in the ITEC323 repository.

## Overview

Documentation is a critical part of this educational project. All documentation should be:
- **Clear and accessible** for students new to .NET and Android development
- **Comprehensive** enough to understand the project without additional resources
- **Up-to-date** with the current codebase
- **Well-organized** following the established structure

## Documentation Structure

### Required Files for Each Project

Every sub-project must include these four core documentation files:

#### 1. README.md
The main overview and entry point for the project.

**Should contain**:
- Project name and brief description (1-2 sentences)
- Purpose and learning objectives
- Prerequisites (software, tools, knowledge)
- Quick links to other documentation
- Technology stack overview
- Project status/completion level

**Template**:
```markdown
# Project Name

Brief description of what this project does and demonstrates.

## Learning Objectives
- Objective 1
- Objective 2
- Objective 3

## Prerequisites
- .NET 8.0 SDK or later
- [Other requirements]

## Quick Start
See [QUICKSTART.md](QUICKSTART.md) for setup and running instructions.

## Documentation
- [Functional Requirements](FRD.md)
- [Detailed Documentation](docs/)

## Technology Stack
- .NET 8.0 / ASP.NET Core
- [Other technologies]
```

#### 2. QUICKSTART.md
Step-by-step instructions to get the project running.

**Should contain**:
- Installation steps
- Build instructions
- How to run the project
- How to verify it's working
- Common troubleshooting tips

**Template**:
```markdown
# Quick Start Guide

## Installation

### 1. Clone the Repository
\`\`\`bash
git clone [repository-url]
cd ITEC323/ProjectName
\`\`\`

### 2. Restore Dependencies
\`\`\`bash
dotnet restore
\`\`\`

### 3. Build the Project
\`\`\`bash
dotnet build
\`\`\`

### 4. Run the Application
\`\`\`bash
dotnet run
\`\`\`

## Expected Output
Describe what the user should see when the application runs successfully.

## Troubleshooting
**Issue**: [Common problem]
**Solution**: [How to fix it]
```

#### 3. FRD.md (Functional Requirements Document)
Clear specification of what the project should do.

**Should contain**:
- Purpose statement
- Functional requirements (numbered list)
- Non-functional requirements
- Constraints or limitations
- Success criteria

**Template**:
```markdown
# Functional Requirements Document

## Purpose
[What problem does this project solve? What does it demonstrate?]

## Functional Requirements

### FR1: [Requirement Name]
**Description**: [Detailed description]
**Priority**: High/Medium/Low
**Acceptance Criteria**:
- Criterion 1
- Criterion 2

### FR2: [Requirement Name]
[Continue for each requirement...]

## Non-Functional Requirements

### NFR1: Performance
[Performance expectations]

### NFR2: Usability
[Usability expectations]

## Constraints
- [Any limitations or constraints]

## Success Criteria
- [ ] All functional requirements are implemented
- [ ] Code passes all tests
- [ ] Documentation is complete
```

#### 4. docs/ Folder
Contains detailed technical documentation, diagrams, and supplementary materials.

**Typical contents**:
- Architecture diagrams
- API documentation
- Code walkthroughs
- Tutorial materials
- Design decisions

## Code Documentation

### XML Documentation Comments

All public types and members **must** have XML documentation comments.

**Required tags**:
- `<summary>`: Brief description of the element
- `<param>`: For each method parameter
- `<returns>`: For methods that return values
- `<exception>`: For exceptions that may be thrown
- `<example>`: When helpful for understanding usage

**Example**:
```csharp
/// <summary>
/// Calculates the average grade for a student across all courses.
/// </summary>
/// <param name="studentId">The unique identifier for the student</param>
/// <returns>The average grade as a decimal value between 0.0 and 100.0</returns>
/// <exception cref="ArgumentException">Thrown when studentId is less than or equal to zero</exception>
/// <exception cref="NotFoundException">Thrown when no student is found with the given ID</exception>
/// <example>
/// <code>
/// var average = calculator.CalculateAverage(12345);
/// Console.WriteLine($"Average: {average:F2}%");
/// </code>
/// </example>
public decimal CalculateAverage(int studentId)
{
    if (studentId <= 0)
    {
        throw new ArgumentException("Student ID must be positive", nameof(studentId));
    }
    
    var student = _repository.GetStudent(studentId) 
        ?? throw new NotFoundException($"Student {studentId} not found");
    
    return student.Grades.Average();
}
```

### Inline Comments

Use inline comments to explain **why** something is done, not **what** is being done.

```csharp
// ❌ Bad: Describing what the code does (obvious from reading)
// Loop through all students
foreach (var student in students)
{
    // Calculate the grade
    var grade = CalculateGrade(student);
}

// ✅ Good: Explaining why or providing context
// We need to recalculate grades because the grading policy changed mid-semester
foreach (var student in students)
{
    var grade = CalculateGrade(student);
}

// ✅ Good: Explaining non-obvious decisions
// Using ToList() here to avoid multiple enumeration since we iterate twice
var activeStudents = students.Where(s => s.IsActive).ToList();
```

### Documentation for Beginners

When writing for students learning .NET/Android:

1. **Define technical terms** when first used
   ```csharp
   /// <summary>
   /// Represents a Data Transfer Object (DTO) - a simple object used to 
   /// transfer data between different layers of the application without 
   /// including business logic.
   /// </summary>
   ```

2. **Explain the purpose, not just the mechanics**
   ```csharp
   /// <summary>
   /// Validates user input before processing. This prevents security issues
   /// like SQL injection and ensures data integrity throughout the application.
   /// </summary>
   ```

3. **Provide context for patterns**
   ```csharp
   /// <summary>
   /// Constructor injection is used here to receive dependencies. This makes
   /// the class easier to test and follows the Dependency Inversion Principle,
   /// allowing us to swap implementations without changing this class.
   /// </summary>
   ```

## Markdown Formatting

### Consistent Style

- Use ATX-style headers (`#`, `##`, `###`)
- Use fenced code blocks with language specification
- Use tables for structured data
- Use task lists for checklists
- Use blockquotes for important notes

### Code Blocks

Always specify the language for syntax highlighting:

````markdown
```csharp
public class Example
{
    // C# code
}
```

```bash
dotnet run
```

```json
{
    "key": "value"
}
```
````

### Links

Use descriptive link text:

```markdown
<!-- ❌ Bad -->
Read more [here](docs/guide.md).

<!-- ✅ Good -->
Read the [detailed implementation guide](docs/guide.md).
```

### Emphasis

- Use **bold** for important terms or UI elements
- Use *italics* for emphasis or introducing technical terms
- Use `code` for:
  - Class names: `StudentService`
  - Method names: `GetStudentById()`
  - File names: `Program.cs`
  - Command-line commands: `dotnet build`
  - Variable names: `studentId`

## Documentation Updates

### When to Update Documentation

Update documentation whenever:
- Adding new features or functionality
- Changing existing behavior
- Fixing bugs that affect documented behavior
- Adding or removing dependencies
- Changing prerequisites or setup steps
- Refactoring public APIs

### Review Checklist

Before considering documentation complete:

- [ ] All required files (README, QUICKSTART, FRD, docs/) are present
- [ ] Code examples are tested and work correctly
- [ ] Links are valid and point to existing files
- [ ] Grammar and spelling are correct
- [ ] Technical terms are defined on first use
- [ ] Instructions are step-by-step and complete
- [ ] Code examples follow the style guide
- [ ] XML comments are present on all public members
- [ ] Examples are relevant and educational

## Special Considerations

### Educational Context

Remember that students are the primary audience:

- **Assume minimal prior knowledge** of frameworks and tools
- **Explain concepts progressively** from simple to complex
- **Include "why" explanations** not just "how"
- **Provide working examples** for every major concept
- **Link to external learning resources** when appropriate
- **Use consistent terminology** throughout the repository

### Version Compatibility

When documenting version-specific features:

```markdown
## Requirements
- .NET 8.0 SDK or later (tested with .NET 8.0 and .NET 9.0)
- Compatible with C# 12

> **Note**: This project targets .NET 8.0 (LTS) but is compatible with .NET 9.0.
```

## Examples

### Good Documentation Example

```csharp
namespace DotNetHelloWorldCLI
{
    /// <summary>
    /// Main entry point for the Hello World CLI application.
    /// This demonstrates the basic structure of a .NET console application.
    /// </summary>
    public class Program
    {
        /// <summary>
        /// Application entry point. This method is automatically called when 
        /// the application starts.
        /// </summary>
        /// <param name="args">Command-line arguments passed to the application</param>
        /// <remarks>
        /// In this simple example, we're not using the args parameter, but it's
        /// included to show the standard signature of the Main method.
        /// </remarks>
        public static void Main(string[] args)
        {
            // Output a greeting to the console
            // Console.WriteLine() writes text followed by a line break
            Console.WriteLine("Hello, World!");
        }
    }
}
```

## Related Guidelines

- [Code Generation Guidelines](code-generation.md)
- [Testing Guidelines](testing.md)
- [Main Agent Guidelines](../../AGENTS.md)
