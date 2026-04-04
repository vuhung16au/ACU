namespace RawSQLDapper.Models;

/// <summary>
/// Represents a sale associated with a product.
/// </summary>
public class SaleRecord
{
    /// <summary>
    /// Gets or sets the sale identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the product identifier.
    /// </summary>
    public int ProductId { get; set; }

    /// <summary>
    /// Gets or sets the quantity sold.
    /// </summary>
    public int QuantitySold { get; set; }

    /// <summary>
    /// Gets or sets the sale date in UTC.
    /// </summary>
    public DateTime SoldAtUtc { get; set; }

    /// <summary>
    /// Gets or sets the related product.
    /// </summary>
    public Product Product { get; set; } = default!;
}
