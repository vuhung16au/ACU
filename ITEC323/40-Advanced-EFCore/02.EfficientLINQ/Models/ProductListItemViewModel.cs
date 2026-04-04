namespace EfficientLINQ.Models;

/// <summary>
/// Represents the lightweight projection shown on the products page.
/// </summary>
public class ProductListItemViewModel
{
    /// <summary>
    /// Gets or sets the product name.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the category name.
    /// </summary>
    public string CategoryName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the price.
    /// </summary>
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the stock quantity.
    /// </summary>
    public int QuantityInStock { get; set; }
}
