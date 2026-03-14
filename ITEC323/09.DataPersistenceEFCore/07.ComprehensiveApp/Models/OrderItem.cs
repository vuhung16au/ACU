using System.ComponentModel.DataAnnotations;

namespace ComprehensiveApp.Models;

/// <summary>
/// Represents a single product line within an order.
/// </summary>
public class OrderItem
{
    /// <summary>
    /// Gets or sets the order item identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the related order identifier.
    /// </summary>
    public int OrderId { get; set; }

    /// <summary>
    /// Gets or sets the related product identifier.
    /// </summary>
    [Display(Name = "Product")]
    public int ProductId { get; set; }

    /// <summary>
    /// Gets or sets the ordered quantity.
    /// </summary>
    [Range(1, 1000)]
    public int Quantity { get; set; }

    /// <summary>
    /// Gets or sets the captured unit price at order time.
    /// </summary>
    [Range(0.01, 100000)]
    public decimal UnitPrice { get; set; }

    /// <summary>
    /// Gets or sets the related order.
    /// </summary>
    public Order? Order { get; set; }

    /// <summary>
    /// Gets or sets the related product.
    /// </summary>
    public Product? Product { get; set; }
}