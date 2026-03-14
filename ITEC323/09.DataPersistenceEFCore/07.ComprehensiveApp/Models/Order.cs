using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ComprehensiveApp.Models;

/// <summary>
/// Represents a customer order.
/// </summary>
public class Order
{
    /// <summary>
    /// Gets or sets the order identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the related customer identifier.
    /// </summary>
    [Display(Name = "Customer")]
    public int CustomerId { get; set; }

    /// <summary>
    /// Gets or sets the order date in UTC.
    /// </summary>
    [Display(Name = "Order Date (UTC)")]
    public DateTime OrderDateUtc { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Gets or sets the current order status.
    /// </summary>
    [Required]
    [MaxLength(30)]
    public string Status { get; set; } = "Pending";

    /// <summary>
    /// Gets or sets optional delivery or handling notes.
    /// </summary>
    [MaxLength(300)]
    public string? Notes { get; set; }

    /// <summary>
    /// Gets or sets the related customer.
    /// </summary>
    public Customer? Customer { get; set; }

    /// <summary>
    /// Gets or sets order line items.
    /// </summary>
    public List<OrderItem> OrderItems { get; set; } = new();

    /// <summary>
    /// Gets the calculated total amount for the order.
    /// </summary>
    [NotMapped]
    public decimal TotalAmount => OrderItems.Sum(item => item.UnitPrice * item.Quantity);
}