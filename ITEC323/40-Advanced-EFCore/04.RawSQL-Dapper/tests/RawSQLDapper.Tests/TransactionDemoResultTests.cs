using FluentAssertions;
using RawSQLDapper.Models;

namespace RawSQLDapper.Tests;

/// <summary>
/// Contains simple tests for the transaction demo result.
/// </summary>
public class TransactionDemoResultTests
{
    /// <summary>
    /// Verifies that the shared transaction result can report a stock increase.
    /// </summary>
    [Fact]
    public void NewQuantityInStock_WhenRestocked_IsHigherThanZero()
    {
        var result = new TransactionDemoResult
        {
            ProductName = "Wireless Presenter",
            NewQuantityInStock = 47,
            AuditMessage = "Restocked Wireless Presenter by 5 units using a shared EF Core + Dapper transaction."
        };

        result.NewQuantityInStock.Should().BeGreaterThan(0);
        result.AuditMessage.Should().Contain("shared EF Core + Dapper transaction");
    }
}
