using FluentAssertions;
using GeminiTextAndRagApiDemo.Services;
using Xunit;

namespace GeminiTextAndRagApiDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="EnvironmentFileLoader"/> class.
/// </summary>
public class EnvironmentFileLoaderTests
{
    /// <summary>
    /// Verifies that valid key-value lines are loaded and comments are ignored.
    /// </summary>
    [Fact]
    public void Load_WithCommentsAndBlankLines_SetsEnvironmentVariables()
    {
        // Arrange
        var filePath = Path.GetTempFileName();
        File.WriteAllLines(filePath,
        [
            "# comment",
            "",
            "TEST_ENV_KEY=alpha",
            "TEST_OTHER_KEY=beta"
        ]);

        var loader = new EnvironmentFileLoader();

        try
        {
            // Act
            loader.Load(filePath);

            // Assert
            Environment.GetEnvironmentVariable("TEST_ENV_KEY").Should().Be("alpha");
            Environment.GetEnvironmentVariable("TEST_OTHER_KEY").Should().Be("beta");
        }
        finally
        {
            Environment.SetEnvironmentVariable("TEST_ENV_KEY", null);
            Environment.SetEnvironmentVariable("TEST_OTHER_KEY", null);
            File.Delete(filePath);
        }
    }
}
