# Unit Testing in .NET

A quick guide to unit testing in .NET for developers familiar with JUnit/TestNG.

## Test Frameworks

.NET has three main testing frameworks (similar to JUnit in Java):

| Framework | Description | Best For |
|-----------|-------------|----------|
| **xUnit** | Modern, lightweight | New projects (most popular) |
| **NUnit** | Feature-rich, mature | Teams from Java/JUnit background |
| **MSTest** | Microsoft's native framework | Enterprise/Microsoft shops |

## Quick Start

### 1. Create a Test Project

```bash
# Create a test project (xUnit)
dotnet new xunit -n MyApp.Tests

# Add to solution
dotnet sln add MyApp.Tests/MyApp.Tests.csproj

# Reference the project you want to test
cd MyApp.Tests
dotnet add reference ../MyApp/MyApp.csproj
```

### 2. Write a Test (xUnit)

```csharp
using Xunit;

namespace MyApp.Tests
{
    public class CalculatorTests
    {
        [Fact]
        public void Add_TwoNumbers_ReturnsSum()
        {
            // Arrange
            var calculator = new Calculator();
            
            // Act
            var result = calculator.Add(2, 3);
            
            // Assert
            Assert.Equal(5, result);
        }
        
        [Theory]
        [InlineData(1, 2, 3)]
        [InlineData(0, 0, 0)]
        [InlineData(-1, 1, 0)]
        public void Add_VariousInputs_ReturnsCorrectSum(int a, int b, int expected)
        {
            // Arrange
            var calculator = new Calculator();
            
            // Act
            var result = calculator.Add(a, b);
            
            // Assert
            Assert.Equal(expected, result);
        }
    }
}
```

### 3. Run Tests

```bash
# Run all tests
dotnet test

# Run tests with detailed output
dotnet test --logger "console;verbosity=detailed"

# Run tests with coverage (requires coverlet)
dotnet test /p:CollectCoverage=true
```

## Annotations Comparison

| Java (JUnit 5) | .NET (xUnit) | Purpose |
|----------------|--------------|---------|
| `@Test` | `[Fact]` | Marks a single test method |
| `@ParameterizedTest` | `[Theory]` | Data-driven test |
| `@ValueSource` | `[InlineData]` | Provides test data inline |
| `@BeforeEach` | Constructor | Setup before each test |
| `@AfterEach` | `IDisposable.Dispose()` | Cleanup after each test |
| `@BeforeAll` | `IClassFixture<T>` | Setup once for all tests |

## Common Assertions

```csharp
// Equality
Assert.Equal(expected, actual);
Assert.NotEqual(expected, actual);

// Boolean
Assert.True(condition);
Assert.False(condition);

// Null checks
Assert.Null(obj);
Assert.NotNull(obj);

// Exceptions
Assert.Throws<ArgumentException>(() => method());

// Collections
Assert.Contains(item, collection);
Assert.Empty(collection);
```

## Mocking Libraries

For mocking dependencies (like Mockito in Java):

```bash
# Install Moq (most popular)
dotnet add package Moq
```

**Example with Moq:**
```csharp
using Moq;
using Xunit;

public class UserServiceTests
{
    [Fact]
    public void GetUser_ValidId_ReturnsUser()
    {
        // Arrange
        var mockRepository = new Mock<IUserRepository>();
        mockRepository.Setup(repo => repo.GetById(1))
            .Returns(new User { Id = 1, Name = "John" });
        
        var service = new UserService(mockRepository.Object);
        
        // Act
        var user = service.GetUser(1);
        
        // Assert
        Assert.Equal("John", user.Name);
        mockRepository.Verify(repo => repo.GetById(1), Times.Once);
    }
}
```

## Project Structure

```
MySolution/
├── MyApp/                    # Main project
│   ├── Calculator.cs
│   └── MyApp.csproj
└── MyApp.Tests/              # Test project
    ├── CalculatorTests.cs
    └── MyApp.Tests.csproj
```

## Best Practices

✅ **Naming:** Use descriptive test names: `MethodName_Scenario_ExpectedResult`  
✅ **AAA Pattern:** Arrange, Act, Assert  
✅ **One assertion per test:** Keep tests focused  
✅ **Separate test project:** Don't mix test code with production code  
✅ **Use `[Theory]`:** For data-driven tests instead of multiple `[Fact]` methods  

## Running Tests in CI/CD

```bash
# Typical CI pipeline command
dotnet test --configuration Release --logger trx --results-directory ./TestResults
```

## Learn More

- [xUnit Documentation](https://xunit.net/)
- [NUnit Documentation](https://nunit.org/)
- [Moq Documentation](https://github.com/moq/moq4)
- [.NET Testing Best Practices](https://docs.microsoft.com/dotnet/core/testing/)
