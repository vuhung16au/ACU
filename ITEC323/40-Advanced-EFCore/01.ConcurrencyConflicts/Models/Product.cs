using System.ComponentModel.DataAnnotations;

namespace ConcurrencyConflicts.Models;

/// <summary>
/// Represents an editable product in the concurrency lesson.
/// </summary>
public class Product
{
    /// <summary>
    /// Gets or sets the product identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the display name.
    /// </summary>
    [Required]
    [StringLength(80)]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the current price.
    /// </summary>
    [Range(0.01, 100000)]
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the quantity on hand.
    /// </summary>
    [Range(0, 100000)]
    public int QuantityInStock { get; set; }

    /// <summary>
    /// Gets or sets instructor notes or product notes.
    /// </summary>
    [StringLength(300)]
    public string Notes { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the concurrency version used in the update WHERE clause.
    /// </summary>
    public int Version { get; set; }

    /// <summary>
    /// Gets or sets the last modified UTC timestamp.
    /// </summary>
    public DateTime LastModifiedUtc { get; set; }
}
