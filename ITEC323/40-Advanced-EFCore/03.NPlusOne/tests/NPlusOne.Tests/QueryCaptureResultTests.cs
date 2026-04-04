using FluentAssertions;
using NPlusOne.Models;

namespace NPlusOne.Tests;

/// <summary>
/// Contains simple tests for query capture result handling.
/// </summary>
public class QueryCaptureResultTests
{
    /// <summary>
    /// Verifies that query count can represent an improved result.
    /// </summary>
    [Fact]
    public void QueryCount_ImprovedScenarioLowerThanNaive_IsTrue()
    {
        var naive = new QueryCaptureResult { QueryCount = 9 };
        var improved = new QueryCaptureResult { QueryCount = 1 };

        (improved.QueryCount < naive.QueryCount).Should().BeTrue();
    }
}
