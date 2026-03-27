using FluentAssertions;
using HelloMinimalApiDemo.Services;
using Xunit;

namespace HelloMinimalApiDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="LanguageCatalogService"/> class.
/// </summary>
public class LanguageCatalogServiceTests
{
    private readonly LanguageCatalogService _service = new();

    /// <summary>
    /// Verifies that the language list contains the expected values.
    /// </summary>
    [Fact]
    public void GetLanguages_WhenCalled_ReturnsExpectedLanguages()
    {
        // Arrange

        // Act
        var result = _service.GetLanguages();

        // Assert
        result.Should().Equal("Java", "Python", "C#");
    }
}
