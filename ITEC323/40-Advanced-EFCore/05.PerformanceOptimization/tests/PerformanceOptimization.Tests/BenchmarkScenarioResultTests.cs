using FluentAssertions;
using PerformanceOptimization.Models;

namespace PerformanceOptimization.Tests;

/// <summary>
/// Contains unit tests for benchmark helper calculations.
/// </summary>
public class BenchmarkScenarioResultTests
{
    /// <summary>
    /// Verifies that row reduction is rounded to an easy-to-read percentage.
    /// </summary>
    [Fact]
    public void CalculateRowReductionPercent_BetterScenario_ReturnsRoundedPercentage()
    {
        // Arrange
        var scenario = new BenchmarkScenarioResult
        {
            EstimatedRowsTouched = 120
        };

        // Act
        var reductionPercent = scenario.CalculateRowReductionPercent(480);

        // Assert
        reductionPercent.Should().Be(75);
    }
}
