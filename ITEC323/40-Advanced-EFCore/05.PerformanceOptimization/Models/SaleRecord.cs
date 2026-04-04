namespace PerformanceOptimization.Models;

/// <summary>
/// Represents a historical sale entry for a product.
/// </summary>
public class SaleRecord
{
    /// <summary>
    /// Gets or sets the database identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the related product identifier.
    /// </summary>
    public int ProductId { get; set; }

    /// <summary>
    /// Gets or sets the quantity sold in this record.
    /// </summary>
    public int QuantitySold { get; set; }

    /// <summary>
    /// Gets or sets the UTC time when the sale happened.
    /// </summary>
    public DateTime SoldAtUtc { get; set; }

    /// <summary>
    /// Gets or sets the related product.
    /// </summary>
    public Product? Product { get; set; }
}
