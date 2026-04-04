using EfficientLINQ.Services;
using FluentAssertions;

namespace EfficientLINQ.Tests;

/// <summary>
/// Contains tests for query comparison helper metrics.
/// </summary>
public class QueryMetricsTests
{
    /// <summary>
    /// Verifies that estimated payload cells multiply rows by columns.
    /// </summary>
    [Fact]
    public void EstimatePayloadCells_ValidInputs_ReturnsExpectedProduct()
    {
        var result = QueryMetrics.EstimatePayloadCells(12, 4);

        result.Should().Be(48);
    }
}
