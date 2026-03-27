using ExceptionTestingDemo.Models;
using ExceptionTestingDemo.Services;
using FluentAssertions;
using Moq;
using Xunit;

namespace ExceptionTestingDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="FormSubmissionService"/> class.
/// </summary>
public class FormSubmissionServiceTests
{
    private readonly Mock<IAsyncEmailRegistry> _emailRegistryMock = new();

    /// <summary>
    /// Verifies that missing names throw an argument exception.
    /// </summary>
    [Fact]
    public async Task SubmitAsync_WithMissingName_ThrowsArgumentException()
    {
        // Arrange
        var service = new FormSubmissionService(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "",
            Email = "linus@example.com"
        };

        // Act
        Func<Task> act = async () => await service.SubmitAsync(input);

        // Assert
        await act.Should().ThrowAsync<ArgumentException>()
            .WithMessage("Name is required.");
    }

    /// <summary>
    /// Verifies that missing emails throw an argument exception.
    /// </summary>
    [Fact]
    public async Task SubmitAsync_WithMissingEmail_ThrowsArgumentException()
    {
        // Arrange
        var service = new FormSubmissionService(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = ""
        };

        // Act
        Func<Task> act = async () => await service.SubmitAsync(input);

        // Assert
        await act.Should().ThrowAsync<ArgumentException>()
            .WithMessage("Email is required.");
    }

    /// <summary>
    /// Verifies that invalid email values throw a format exception.
    /// </summary>
    /// <param name="email">The invalid email value to test.</param>
    [Theory]
    [InlineData("linus")]
    [InlineData("linus@")]
    [InlineData("@example.com")]
    public async Task SubmitAsync_WithInvalidEmail_ThrowsFormatException(string email)
    {
        // Arrange
        var service = new FormSubmissionService(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = email
        };

        // Act
        Func<Task> act = async () => await service.SubmitAsync(input);

        // Assert
        await act.Should().ThrowAsync<FormatException>()
            .WithMessage("Email must be a valid email address.");
    }

    /// <summary>
    /// Verifies that duplicate email values throw an invalid operation exception.
    /// </summary>
    [Fact]
    public async Task SubmitAsync_WithDuplicateEmail_ThrowsInvalidOperationException()
    {
        // Arrange
        _emailRegistryMock.Setup(registry => registry.EmailExistsAsync("ada@example.com")).ReturnsAsync(true);
        var service = new FormSubmissionService(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Ada Lovelace",
            Email = "  ada@example.com  "
        };

        // Act
        Func<Task> act = async () => await service.SubmitAsync(input);

        // Assert
        await act.Should().ThrowAsync<InvalidOperationException>()
            .WithMessage("Email is already registered.");
    }

    /// <summary>
    /// Verifies that dependency failures are propagated to the caller.
    /// </summary>
    [Fact]
    public async Task SubmitAsync_WhenEmailRegistryThrows_PropagatesException()
    {
        // Arrange
        _emailRegistryMock.Setup(registry => registry.EmailExistsAsync("linus@example.com"))
            .ThrowsAsync(new TimeoutException("The email registry timed out."));

        var service = new FormSubmissionService(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = "linus@example.com"
        };

        // Act
        Func<Task> act = async () => await service.SubmitAsync(input);

        // Assert
        await act.Should().ThrowAsync<TimeoutException>()
            .WithMessage("The email registry timed out.");
    }

    /// <summary>
    /// Verifies that valid input returns the processed result values.
    /// </summary>
    [Fact]
    public async Task SubmitAsync_WithValidInput_ReturnsSubmissionResult()
    {
        // Arrange
        _emailRegistryMock.Setup(registry => registry.EmailExistsAsync("linus@example.com")).ReturnsAsync(false);
        var service = new FormSubmissionService(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = " Linus Torvalds ",
            Email = " linus@example.com ",
            FavoriteLanguage = "Python"
        };

        // Act
        var result = await service.SubmitAsync(input);

        // Assert
        result.Name.Should().Be("Linus Torvalds");
        result.Email.Should().Be("linus@example.com");
        result.FavoriteLanguage.Should().Be("Python");
    }

    /// <summary>
    /// Verifies that an empty language selection is displayed as Unselected.
    /// </summary>
    [Fact]
    public async Task SubmitAsync_WithUnselectedLanguage_ReturnsUnselectedDisplayValue()
    {
        // Arrange
        _emailRegistryMock.Setup(registry => registry.EmailExistsAsync("linus@example.com")).ReturnsAsync(false);
        var service = new FormSubmissionService(_emailRegistryMock.Object);
        var input = new FormSubmissionInput
        {
            Name = "Linus Torvalds",
            Email = "linus@example.com",
            FavoriteLanguage = ""
        };

        // Act
        var result = await service.SubmitAsync(input);

        // Assert
        result.FavoriteLanguage.Should().Be("Unselected");
    }
}
