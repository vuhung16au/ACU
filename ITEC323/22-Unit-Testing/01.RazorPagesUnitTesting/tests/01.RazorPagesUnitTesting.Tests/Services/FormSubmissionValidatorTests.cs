using FluentAssertions;
using RazorPagesUnitTestingDemo.Models;
using RazorPagesUnitTestingDemo.Services;
using Xunit;

namespace RazorPagesUnitTestingDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="FormSubmissionValidator"/> class.
/// </summary>
public class FormSubmissionValidatorTests
{
    private readonly FormSubmissionValidator _validator = new();

    /// <summary>
    /// Verifies that valid name and email values pass validation.
    /// </summary>
    [Fact]
    public void Validate_WithValidNameAndEmail_ReturnsNoErrors()
    {
        // Arrange
        var input = new FormSubmissionInput
        {
            Name = "Ada Lovelace",
            Email = "ada@example.com",
            FavoriteLanguage = "C#"
        };

        // Act
        var result = _validator.Validate(input);

        // Assert
        result.IsValid.Should().BeTrue();
        result.Errors.Should().BeEmpty();
    }

    /// <summary>
    /// Verifies that a missing name returns the correct validation error.
    /// </summary>
    [Fact]
    public void Validate_WithMissingName_ReturnsNameRequiredError()
    {
        // Arrange
        var input = new FormSubmissionInput
        {
            Name = "",
            Email = "ada@example.com"
        };

        // Act
        var result = _validator.Validate(input);

        // Assert
        result.IsValid.Should().BeFalse();
        result.Errors.Should().Contain("Name is required.");
    }

    /// <summary>
    /// Verifies that a missing email returns the correct validation error.
    /// </summary>
    [Fact]
    public void Validate_WithMissingEmail_ReturnsEmailRequiredError()
    {
        // Arrange
        var input = new FormSubmissionInput
        {
            Name = "Ada Lovelace",
            Email = ""
        };

        // Act
        var result = _validator.Validate(input);

        // Assert
        result.IsValid.Should().BeFalse();
        result.Errors.Should().Contain("Email is required.");
    }

    /// <summary>
    /// Verifies that invalid email formats return the correct validation error.
    /// </summary>
    /// <param name="email">The invalid email value to test.</param>
    [Theory]
    [InlineData("ada")]
    [InlineData("ada@")]
    [InlineData("@example.com")]
    public void Validate_WithInvalidEmail_ReturnsEmailFormatError(string email)
    {
        // Arrange
        var input = new FormSubmissionInput
        {
            Name = "Ada Lovelace",
            Email = email
        };

        // Act
        var result = _validator.Validate(input);

        // Assert
        result.IsValid.Should().BeFalse();
        result.Errors.Should().Contain("Email must be a valid email address.");
    }
}
