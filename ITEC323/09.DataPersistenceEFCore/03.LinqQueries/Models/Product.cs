using System.ComponentModel.DataAnnotations;

namespace LinqQueries.Models;

/// <summary>
/// Represents a product stored in the learning catalog.
/// </summary>
public class Product
{
    /// <summary>
    /// Gets or sets the unique identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the product name.
    /// </summary>
    [Required]
    [MaxLength(100)]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets a short description.
    /// </summary>
    [MaxLength(300)]
    public string? Description { get; set; }

    /// <summary>
    /// Gets or sets the product price.
    /// </summary>
    [Range(0.01, 10000)]
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the available stock quantity.
    /// </summary>
    [Range(0, 100000)]
    public int StockQuantity { get; set; }

    /// <summary>
    /// Gets or sets the UTC created timestamp.
    /// </summary>
    public DateTime CreatedAtUtc { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Gets or sets related reviews for LINQ Include examples.
    /// </summary>
    public List<ProductReview> Reviews { get; set; } = new();
}
