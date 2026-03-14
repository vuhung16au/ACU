using System.ComponentModel.DataAnnotations;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace MongoDBDocker.Models;

/// <summary>
/// Represents a product stored in the learning catalog.
/// </summary>
public class Product
{
    /// <summary>
    /// Gets or sets the unique identifier.
    /// </summary>
    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string Id { get; set; } = string.Empty;

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
    [BsonDateTimeOptions(Kind = DateTimeKind.Utc)]
    public DateTime CreatedAtUtc { get; set; } = DateTime.UtcNow;
}
