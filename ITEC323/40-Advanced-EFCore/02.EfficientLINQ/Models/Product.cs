namespace EfficientLINQ.Models;

/// <summary>
/// Represents a product used in the LINQ lesson.
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
    /// Gets or sets the price.
    /// </summary>
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the current stock quantity.
    /// </summary>
    public int QuantityInStock { get; set; }

    /// <summary>
    /// Gets or sets a longer product description.
    /// </summary>
    public string Description { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the category identifier.
    /// </summary>
    public int CategoryId { get; set; }

    /// <summary>
    /// Gets or sets the related category.
    /// </summary>
    public Category Category { get; set; } = default!;
}
