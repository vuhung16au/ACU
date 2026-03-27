using FluentAssertions;
using MockingDependenciesDemo.Models;
using MockingDependenciesDemo.Services;
using Moq;
using Xunit;

namespace MockingDependenciesDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="FormSubmissionValidator"/> class.
/// </summary>
public class FormSubmissionValidatorTests
{
    private readonly Mock<IEmailRegistry> _emailRegistryMock = new();

    /// <summary>
    /// Verifies that valid name and email values pass validation.
    /// </summary>
    [Fact]
    public void Validate_WithValidInput_ReturnsNoErrors()
    {
        // Arrange
        _emailRegistryMock.Setup(registry => registry.EmailExists("linus@example.com")).Returns(false);
        var validator = new FormSubmissionValidator(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = "linus@example.com",
            FavoriteLanguage = "C#"
        };

        // Act
        var result = validator.Validate(input);

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
        var validator = new FormSubmissionValidator(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "",
            Email = "linus@example.com"
        };

        // Act
        var result = validator.Validate(input);

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
        var validator = new FormSubmissionValidator(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = ""
        };

        // Act
        var result = validator.Validate(input);

        // Assert
        result.IsValid.Should().BeFalse();
        result.Errors.Should().Contain("Email is required.");
    }

    /// <summary>
    /// Verifies that invalid email formats return the correct validation error.
    /// </summary>
    /// <param name="email">The invalid email value to test.</param>
    [Theory]
    [InlineData("linus")]
    [InlineData("linus@")]
    [InlineData("@example.com")]
    public void Validate_WithInvalidEmail_ReturnsEmailFormatError(string email)
    {
        // Arrange
        var validator = new FormSubmissionValidator(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = email
        };

        // Act
        var result = validator.Validate(input);

        // Assert
        result.IsValid.Should().BeFalse();
        result.Errors.Should().Contain("Email must be a valid email address.");
    }

    /// <summary>
    /// Verifies that duplicate email values return the correct validation error.
    /// </summary>
    [Fact]
    public void Validate_WithDuplicateEmail_ReturnsDuplicateEmailError()
    {
        // Arrange
        _emailRegistryMock.Setup(registry => registry.EmailExists("ada@example.com")).Returns(true);
        var validator = new FormSubmissionValidator(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Ada Lovelace",
            Email = "  ada@example.com  "
        };

        // Act
        var result = validator.Validate(input);

        // Assert
        result.IsValid.Should().BeFalse();
        result.Errors.Should().Contain("Email is already registered.");
    }

    /// <summary>
    /// Verifies that the registry is called once when the email value is valid.
    /// </summary>
    [Fact]
    public void Validate_WithValidEmail_CallsEmailRegistryOnce()
    {
        // Arrange
        _emailRegistryMock.Setup(registry => registry.EmailExists("linus@example.com")).Returns(false);
        var validator = new FormSubmissionValidator(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = "linus@example.com"
        };

        // Act
        validator.Validate(input);

        // Assert
        _emailRegistryMock.Verify(registry => registry.EmailExists("linus@example.com"), Times.Once);
    }

    /// <summary>
    /// Verifies that the registry is not called when the email value is blank.
    /// </summary>
    [Fact]
    public void Validate_WithBlankEmail_DoesNotCallEmailRegistry()
    {
        // Arrange
        var validator = new FormSubmissionValidator(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = ""
        };

        // Act
        validator.Validate(input);

        // Assert
        _emailRegistryMock.Verify(registry => registry.EmailExists(It.IsAny<string>()), Times.Never);
    }

    /// <summary>
    /// Verifies that the registry is not called when the email format is invalid.
    /// </summary>
    [Fact]
    public void Validate_WithInvalidEmailFormat_DoesNotCallEmailRegistry()
    {
        // Arrange
        var validator = new FormSubmissionValidator(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = "linus"
        };

        // Act
        validator.Validate(input);

        // Assert
        _emailRegistryMock.Verify(registry => registry.EmailExists(It.IsAny<string>()), Times.Never);
    }
}
