using FluentAssertions;
using GeminiTextAndRagRazorDemo.Models;
using GeminiTextAndRagRazorDemo.Services;
using Xunit;

namespace GeminiTextAndRagRazorDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="ChatInputValidator"/> class.
/// </summary>
public class ChatInputValidatorTests
{
    private readonly ChatInputValidator _validator = new();

    /// <summary>
    /// Verifies that an empty question returns a friendly validation message.
    /// </summary>
    [Fact]
    public void Validate_WithEmptyQuestion_ReturnsValidationMessage()
    {
        // Arrange
        var input = new ChatInput
        {
            Question = "   "
        };

        // Act
        var message = _validator.Validate(input);

        // Assert
        message.Should().NotBeEmpty();
    }

    /// <summary>
    /// Verifies that a clear question passes validation.
    /// </summary>
    [Fact]
    public void Validate_WithLongerQuestion_ReturnsEmptyMessage()
    {
        // Arrange
        var input = new ChatInput
        {
            Question = "Why should I keep an API key in .env.local?"
        };

        // Act
        var message = _validator.Validate(input);

        // Assert
        message.Should().BeEmpty();
    }
}
