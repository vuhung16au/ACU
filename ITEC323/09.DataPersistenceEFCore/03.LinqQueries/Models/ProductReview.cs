using System.ComponentModel.DataAnnotations;

namespace LinqQueries.Models;

/// <summary>
/// Represents a product review used for Include query demonstrations.
/// </summary>
public class ProductReview
{
    /// <summary>
    /// Gets or sets the review identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the product identifier.
    /// </summary>
    public int ProductId { get; set; }

    /// <summary>
    /// Gets or sets the review rating from 1 to 5.
    /// </summary>
    [Range(1, 5)]
    public int Rating { get; set; }

    /// <summary>
    /// Gets or sets optional review comments.
    /// </summary>
    [MaxLength(300)]
    public string? Comment { get; set; }

    /// <summary>
    /// Gets or sets the UTC creation timestamp.
    /// </summary>
    public DateTime CreatedAtUtc { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Gets or sets the reviewed product.
    /// </summary>
    public Product? Product { get; set; }
}
