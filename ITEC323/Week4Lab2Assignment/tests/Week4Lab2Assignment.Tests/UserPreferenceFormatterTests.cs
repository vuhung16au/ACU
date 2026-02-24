using FluentAssertions;
using Week4Lab2Assignment;
using Xunit;

namespace Week4Lab2Assignment.Tests;

/// <summary>
/// Unit tests for the UserPreferenceFormatter helper class.
/// </summary>
public class UserPreferenceFormatterTests
{
    [Fact]
    public void FormatMessage_WithNameAndLanguage_ReturnsExpectedSentence()
    {
        // Arrange
        var name = "Alice";
        var language = "C#";

        // Act
        var result = UserPreferenceFormatter.FormatMessage(name, language);

        // Assert
        result.Should().Be("Your name is Alice and your favourite programming language is C#");
    }
}

