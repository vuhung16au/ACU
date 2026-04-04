namespace PerformanceOptimization.Models;

/// <summary>
/// Represents a product shown in the performance optimization demos.
/// </summary>
public class Product
{
    /// <summary>
    /// Gets or sets the database identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the display name.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the category name.
    /// </summary>
    public string CategoryName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the sale price.
    /// </summary>
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the quantity currently in stock.
    /// </summary>
    public int QuantityInStock { get; set; }

    /// <summary>
    /// Gets the related sale records.
    /// </summary>
    public ICollection<SaleRecord> SaleRecords { get; } = new List<SaleRecord>();
}
