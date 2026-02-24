# Testing Guidelines for AI Agents

This document provides guidelines for AI agents on writing and maintaining tests in the ITEC323 repository.

## Overview

Testing is essential for ensuring code quality and teaching students good software engineering practices. All code contributions should include appropriate tests.

## Core Principles

- **Tests are educational tools**: Write tests that demonstrate best practices
- **Keep tests simple**: Tests should be easy to understand and maintain
- **Test behavior, not implementation**: Focus on what the code does, not how it does it
- **Make tests reliable**: Tests should pass consistently and not depend on external factors

## Testing Framework

### .NET Projects
- **Framework**: xUnit (preferred for .NET)
- **Mocking**: Moq or NSubstitute (when needed)
- **Assertions**: xUnit assertions and FluentAssertions (for readability)

### Android Projects
- **Framework**: JUnit 4 for unit tests
- **UI Testing**: Espresso
- **Mocking**: Mockito

**Note**: Detailed Android testing guidelines will be added when the Android portion begins.

## Project Structure

### Test Organization

Tests should be in a separate `tests/` folder within each project:

```
ProjectName/
├── src/
│   └── ProjectName/
│       ├── ProjectName.csproj
│       └── [source files]
└── tests/
    └── ProjectName.Tests/
        ├── ProjectName.Tests.csproj
        └── [test files]
```

### Test File Naming

- Test files should mirror source file names with `.Tests` suffix
- Example: `StudentService.cs` → `StudentServiceTests.cs`

```
src/ProjectName/
├── Services/
│   └── StudentService.cs

tests/ProjectName.Tests/
├── Services/
│   └── StudentServiceTests.cs
```

## Writing Tests with xUnit

### Test Class Structure

```csharp
using Xunit;
using FluentAssertions;
using ProjectName.Services;

namespace ProjectName.Tests.Services
{
    /// <summary>
    /// Tests for the StudentService class.
    /// This demonstrates how to write unit tests for service classes.
    /// </summary>
    public class StudentServiceTests
    {
        // Fields for shared test dependencies
        private readonly StudentService _service;
        private readonly Mock<IStudentRepository> _mockRepository;
        
        /// <summary>
        /// Constructor runs before each test method.
        /// Use this to set up common test dependencies.
        /// </summary>
        public StudentServiceTests()
        {
            _mockRepository = new Mock<IStudentRepository>();
            _service = new StudentService(_mockRepository.Object);
        }
        
        // Test methods go here
    }
}
```

### Test Method Structure

Follow the **Arrange-Act-Assert** (AAA) pattern:

```csharp
[Fact]
public void GetStudentById_ValidId_ReturnsStudent()
{
    // Arrange: Set up test data and dependencies
    var expectedStudent = new Student 
    { 
        Id = 1, 
        Name = "John Doe" 
    };
    _mockRepository
        .Setup(r => r.GetById(1))
        .Returns(expectedStudent);
    
    // Act: Execute the method being tested
    var result = _service.GetStudentById(1);
    
    // Assert: Verify the results
    result.Should().NotBeNull();
    result.Id.Should().Be(1);
    result.Name.Should().Be("John Doe");
}
```

### Test Naming Conventions

Use descriptive names that explain:
1. What is being tested
2. Under what conditions
3. What the expected outcome is

**Format**: `MethodName_Scenario_ExpectedBehavior`

```csharp
// ✅ Good: Clear and descriptive
[Fact]
public void GetStudentById_InvalidId_ThrowsArgumentException()

[Fact]
public void CalculateAverage_EmptyGradeList_ReturnsZero()

[Fact]
public void RegisterStudent_DuplicateEmail_ReturnsFalse()

// ❌ Bad: Unclear or too generic
[Fact]
public void Test1()

[Fact]
public void TestGetStudent()

[Fact]
public void ShouldWork()
```

## Types of Tests

### 1. Unit Tests

Test individual methods or classes in isolation.

```csharp
[Fact]
public void Add_TwoPositiveNumbers_ReturnsSum()
{
    // Arrange
    var calculator = new Calculator();
    
    // Act
    var result = calculator.Add(5, 3);
    
    // Assert
    result.Should().Be(8);
}
```

### 2. Theory Tests

Test the same behavior with multiple inputs using `[Theory]` and `[InlineData]`:

```csharp
[Theory]
[InlineData(0, 0, 0)]
[InlineData(1, 1, 2)]
[InlineData(-1, 1, 0)]
[InlineData(100, 50, 150)]
public void Add_VariousInputs_ReturnsCorrectSum(int a, int b, int expected)
{
    // Arrange
    var calculator = new Calculator();
    
    // Act
    var result = calculator.Add(a, b);
    
    // Assert
    result.Should().Be(expected);
}
```

### 3. Exception Tests

Verify that methods throw appropriate exceptions:

```csharp
[Fact]
public void GetStudentById_InvalidId_ThrowsArgumentException()
{
    // Arrange
    var service = new StudentService();
    
    // Act
    Action act = () => service.GetStudentById(-1);
    
    // Assert
    act.Should().Throw<ArgumentException>()
       .WithMessage("*Student ID must be positive*");
}
```

### 4. Integration Tests

Test multiple components working together (use sparingly for educational projects):

```csharp
[Fact]
public async Task GetStudents_FromDatabase_ReturnsAllStudents()
{
    // Arrange
    using var context = CreateTestDbContext();
    var repository = new StudentRepository(context);
    await SeedTestData(context);
    
    // Act
    var students = await repository.GetAllAsync();
    
    // Assert
    students.Should().HaveCount(3);
}
```

## Mocking Dependencies

Use mocking frameworks to isolate the code being tested:

```csharp
[Fact]
public void ProcessStudent_ValidStudent_CallsRepository()
{
    // Arrange
    var mockRepository = new Mock<IStudentRepository>();
    var service = new StudentService(mockRepository.Object);
    var student = new Student { Id = 1, Name = "John" };
    
    // Act
    service.ProcessStudent(student);
    
    // Assert: Verify the repository method was called exactly once
    mockRepository.Verify(
        r => r.Save(student), 
        Times.Once
    );
}
```

## Assertions

### Using xUnit Assertions

```csharp
Assert.Equal(expected, actual);
Assert.NotEqual(expected, actual);
Assert.True(condition);
Assert.False(condition);
Assert.Null(value);
Assert.NotNull(value);
Assert.Throws<ExceptionType>(() => methodCall);
Assert.Contains(expectedItem, collection);
```

### Using FluentAssertions (Recommended)

FluentAssertions provides more readable assertions:

```csharp
// Basic assertions
result.Should().Be(42);
result.Should().NotBe(0);
result.Should().BeNull();
result.Should().NotBeNull();

// String assertions
name.Should().Be("John Doe");
name.Should().Contain("John");
name.Should().StartWith("John");
name.Should().EndWith("Doe");

// Collection assertions
students.Should().HaveCount(3);
students.Should().Contain(s => s.Name == "John");
students.Should().BeInAscendingOrder(s => s.Name);
students.Should().NotBeEmpty();

// Exception assertions
action.Should().Throw<ArgumentException>()
    .WithMessage("*cannot be null*");

// Type assertions
result.Should().BeOfType<Student>();
result.Should().BeAssignableTo<IStudent>();
```

## Test Data Best Practices

### Use Meaningful Test Data

```csharp
// ❌ Bad: Generic, meaningless data
var student = new Student { Id = 1, Name = "xxx" };

// ✅ Good: Realistic, meaningful data
var student = new Student 
{ 
    Id = 12345, 
    Name = "Jane Smith",
    Email = "jane.smith@student.acu.edu.au",
    EnrollmentDate = new DateTime(2024, 2, 1)
};
```

### Use Test Data Builders

For complex objects, use builder pattern or factory methods:

```csharp
public class StudentTestBuilder
{
    private int _id = 1;
    private string _name = "Test Student";
    private string _email = "test@student.acu.edu.au";
    
    public StudentTestBuilder WithId(int id)
    {
        _id = id;
        return this;
    }
    
    public StudentTestBuilder WithName(string name)
    {
        _name = name;
        return this;
    }
    
    public Student Build()
    {
        return new Student
        {
            Id = _id,
            Name = _name,
            Email = _email
        };
    }
}

// Usage in tests
[Fact]
public void EnrollStudent_ValidStudent_ReturnsTrue()
{
    // Arrange
    var student = new StudentTestBuilder()
        .WithId(12345)
        .WithName("John Doe")
        .Build();
    
    // Act & Assert
    // ...
}
```

## Testing Async Code

```csharp
[Fact]
public async Task GetStudentAsync_ValidId_ReturnsStudent()
{
    // Arrange
    var mockRepository = new Mock<IStudentRepository>();
    mockRepository
        .Setup(r => r.GetByIdAsync(1))
        .ReturnsAsync(new Student { Id = 1, Name = "John" });
    
    var service = new StudentService(mockRepository.Object);
    
    // Act
    var result = await service.GetStudentAsync(1);
    
    // Assert
    result.Should().NotBeNull();
    result.Id.Should().Be(1);
}
```

## Test Coverage

### What to Test

**Do test**:
- Public methods and their behavior
- Edge cases and boundary conditions
- Error handling and exceptions
- Different code paths (if/else branches)
- Important business logic

**Don't test**:
- Private methods (test through public methods)
- Third-party libraries
- Simple getters/setters without logic
- Auto-implemented properties

### Example Test Coverage

```csharp
public class GradeCalculator
{
    /// <summary>
    /// Calculates letter grade from numeric score
    /// </summary>
    public string CalculateLetterGrade(decimal score)
    {
        if (score < 0 || score > 100)
            throw new ArgumentOutOfRangeException(nameof(score));
            
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
}

// Comprehensive test coverage
public class GradeCalculatorTests
{
    private readonly GradeCalculator _calculator;
    
    public GradeCalculatorTests()
    {
        _calculator = new GradeCalculator();
    }
    
    [Theory]
    [InlineData(95, "A")]
    [InlineData(90, "A")]  // Boundary
    [InlineData(85, "B")]
    [InlineData(80, "B")]  // Boundary
    [InlineData(75, "C")]
    [InlineData(70, "C")]  // Boundary
    [InlineData(65, "D")]
    [InlineData(60, "D")]  // Boundary
    [InlineData(55, "F")]
    [InlineData(0, "F")]   // Boundary
    public void CalculateLetterGrade_ValidScores_ReturnsCorrectGrade(
        decimal score, 
        string expectedGrade)
    {
        // Act
        var result = _calculator.CalculateLetterGrade(score);
        
        // Assert
        result.Should().Be(expectedGrade);
    }
    
    [Theory]
    [InlineData(-1)]
    [InlineData(101)]
    public void CalculateLetterGrade_InvalidScore_ThrowsException(decimal score)
    {
        // Act
        Action act = () => _calculator.CalculateLetterGrade(score);
        
        // Assert
        act.Should().Throw<ArgumentOutOfRangeException>();
    }
}
```

## Running Tests

### Command Line

```bash
# Run all tests in solution
dotnet test

# Run tests with detailed output
dotnet test --verbosity normal

# Run tests in specific project
dotnet test tests/ProjectName.Tests/ProjectName.Tests.csproj

# Run tests with coverage
dotnet test /p:CollectCoverage=true
```

### Test Project Setup

Create a test project:

```bash
# Navigate to tests folder
cd tests

# Create xUnit test project
dotnet new xunit -n ProjectName.Tests

# Add reference to project being tested
cd ProjectName.Tests
dotnet add reference ../../src/ProjectName/ProjectName.csproj

# Add required packages
dotnet add package FluentAssertions
dotnet add package Moq
```

Sample test project file (`.csproj`):

```xml
<Project Sdk="Microsoft.NET.Sdk">

  <PropertyGroup>
    <TargetFramework>net8.0</TargetFramework>
    <ImplicitUsings>enable</ImplicitUsings>
    <Nullable>enable</Nullable>
    <IsPackable>false</IsPackable>
  </PropertyGroup>

  <ItemGroup>
    <PackageReference Include="FluentAssertions" Version="6.12.0" />
    <PackageReference Include="Microsoft.NET.Test.Sdk" Version="17.8.0" />
    <PackageReference Include="Moq" Version="4.20.70" />
    <PackageReference Include="xunit" Version="2.6.2" />
    <PackageReference Include="xunit.runner.visualstudio" Version="2.5.4" />
  </ItemGroup>

  <ItemGroup>
    <ProjectReference Include="..\..\src\ProjectName\ProjectName.csproj" />
  </ItemGroup>

</Project>
```

## Common Testing Patterns

### Testing with Dependency Injection

```csharp
public class ServiceTests : IDisposable
{
    private readonly ServiceProvider _serviceProvider;
    private readonly IStudentService _service;
    
    public ServiceTests()
    {
        // Setup DI container for testing
        var services = new ServiceCollection();
        services.AddTransient<IStudentRepository, FakeStudentRepository>();
        services.AddTransient<IStudentService, StudentService>();
        
        _serviceProvider = services.BuildServiceProvider();
        _service = _serviceProvider.GetRequiredService<IStudentService>();
    }
    
    [Fact]
    public void TestWithDI()
    {
        // Test uses the resolved service
        var result = _service.GetAllStudents();
        result.Should().NotBeNull();
    }
    
    public void Dispose()
    {
        _serviceProvider?.Dispose();
    }
}
```

## Educational Considerations

When writing tests for student learning:

1. **Include explanatory comments** in tests to teach concepts
2. **Start with simple tests** before more complex scenarios
3. **Show multiple testing techniques** (Fact, Theory, mocking, etc.)
4. **Demonstrate best practices** in all test code
5. **Keep tests focused** on one behavior per test

## Checklist for Test Quality

Before submitting tests:

- [ ] All tests have descriptive names
- [ ] Tests follow AAA (Arrange-Act-Assert) pattern
- [ ] Each test verifies one specific behavior
- [ ] Tests are independent and can run in any order
- [ ] No hardcoded paths or environment-specific values
- [ ] Proper use of mocking for dependencies
- [ ] Edge cases and error conditions are tested
- [ ] Tests have explanatory comments for students
- [ ] All tests pass consistently
- [ ] Tests are in the correct `tests/` folder structure

## Related Guidelines

- [Code Generation Guidelines](code-generation.md)
- [Documentation Guidelines](documentation.md)
- [Main Agent Guidelines](../../AGENTS.md)
