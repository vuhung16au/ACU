using FluentAssertions;
using HelloMinimalApiDemo.Services;
using Xunit;

namespace HelloMinimalApiDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="GreetingService"/> class.
/// </summary>
public class GreetingServiceTests
{
    private readonly GreetingService _service = new();

    /// <summary>
    /// Verifies that a name is added to the greeting.
    /// </summary>
    [Fact]
    public void CreateGreeting_WithName_ReturnsPersonalGreeting()
    {
        // Arrange
        var name = "Ada";

        // Act
        var result = _service.CreateGreeting(name);

        // Assert
        result.Should().Be("Hello, Ada!");
    }

    /// <summary>
    /// Verifies that a blank name returns the default greeting.
    /// </summary>
    [Fact]
    public void CreateGreeting_WithBlankName_ReturnsDefaultGreeting()
    {
        // Arrange
        var name = "   ";

        // Act
        var result = _service.CreateGreeting(name);

        // Assert
        result.Should().Be("Hello, student!");
    }
}
