namespace RawSQLDapper.Models;

/// <summary>
/// Represents a product in the sales reporting lesson.
/// </summary>
public class Product
{
    /// <summary>
    /// Gets or sets the product identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the product name.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the category name.
    /// </summary>
    public string CategoryName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the current stock quantity.
    /// </summary>
    public int QuantityInStock { get; set; }

    /// <summary>
    /// Gets or sets the unit price.
    /// </summary>
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the related sales records.
    /// </summary>
    public ICollection<SaleRecord> SaleRecords { get; set; } = new List<SaleRecord>();
}
