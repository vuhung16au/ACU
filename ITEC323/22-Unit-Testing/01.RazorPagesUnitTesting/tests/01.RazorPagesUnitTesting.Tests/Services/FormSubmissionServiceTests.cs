using FluentAssertions;
using RazorPagesUnitTestingDemo.Models;
using RazorPagesUnitTestingDemo.Services;
using Xunit;

namespace RazorPagesUnitTestingDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="FormSubmissionService"/> class.
/// </summary>
public class FormSubmissionServiceTests
{
    private readonly FormSubmissionService _service = new();

    /// <summary>
    /// Verifies that valid input values are returned for display.
    /// </summary>
    [Fact]
    public void Process_WithValidInput_ReturnsSubmittedValues()
    {
        // Arrange
        var input = new FormSubmissionInput
        {
            Name = " Ada Lovelace ",
            Email = " ada@example.com ",
            FavoriteLanguage = "Python"
        };

        // Act
        var result = _service.Process(input);

        // Assert
        result.Name.Should().Be("Ada Lovelace");
        result.Email.Should().Be("ada@example.com");
        result.FavoriteLanguage.Should().Be("Python");
    }

    /// <summary>
    /// Verifies that an empty language selection is displayed as Unselected.
    /// </summary>
    [Fact]
    public void Process_WithUnselectedLanguage_ReturnsUnselectedDisplayValue()
    {
        // Arrange
        var input = new FormSubmissionInput
        {
            Name = "Ada Lovelace",
            Email = "ada@example.com",
            FavoriteLanguage = ""
        };

        // Act
        var result = _service.Process(input);

        // Assert
        result.FavoriteLanguage.Should().Be("Unselected");
    }
}
