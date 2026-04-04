using ConcurrencyConflicts.Models;
using ConcurrencyConflicts.Services;
using FluentAssertions;

namespace ConcurrencyConflicts.Tests;

/// <summary>
/// Contains tests for teaching helpers used by the concurrency lesson.
/// </summary>
public class ConcurrencyConflictDemoServiceTests
{
    /// <summary>
    /// Verifies that the mapping helper copies all relevant editable values.
    /// </summary>
    [Fact]
    public void ToInputModel_ProductProvided_MapsEditableValues()
    {
        var product = new Product
        {
            Id = 3,
            Name = "Warehouse Scanner",
            Price = 249.50m,
            QuantityInStock = 8,
            Notes = "Shared inventory item.",
            Version = 4
        };

        var mapped = ConcurrencyConflictDemoService.ToInputModel(product);

        mapped.Id.Should().Be(3);
        mapped.Name.Should().Be("Warehouse Scanner");
        mapped.Price.Should().Be(249.50m);
        mapped.QuantityInStock.Should().Be(8);
        mapped.Notes.Should().Be("Shared inventory item.");
        mapped.Version.Should().Be(4);
        mapped.DatabaseVersion.Should().Be(4);
    }
}
