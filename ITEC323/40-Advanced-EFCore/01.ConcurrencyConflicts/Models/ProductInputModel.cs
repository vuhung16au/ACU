using System.ComponentModel.DataAnnotations;

namespace ConcurrencyConflicts.Models;

/// <summary>
/// Represents editable product form values.
/// </summary>
public class ProductInputModel
{
    /// <summary>
    /// Gets or sets the product identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the product name.
    /// </summary>
    [Required]
    [StringLength(80)]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the price.
    /// </summary>
    [Range(0.01, 100000)]
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the stock quantity.
    /// </summary>
    [Range(0, 100000)]
    public int QuantityInStock { get; set; }

    /// <summary>
    /// Gets or sets the notes.
    /// </summary>
    [StringLength(300)]
    public string Notes { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the version loaded by the user.
    /// </summary>
    public int Version { get; set; }

    /// <summary>
    /// Gets or sets the latest database version after a conflict.
    /// </summary>
    public int DatabaseVersion { get; set; }
}
