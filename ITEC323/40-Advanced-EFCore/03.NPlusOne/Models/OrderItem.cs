namespace NPlusOne.Models;

/// <summary>
/// Represents an item inside an order.
/// </summary>
public class OrderItem
{
    /// <summary>
    /// Gets or sets the order item identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the order identifier.
    /// </summary>
    public int OrderId { get; set; }

    /// <summary>
    /// Gets or sets the product identifier.
    /// </summary>
    public int ProductId { get; set; }

    /// <summary>
    /// Gets or sets the quantity.
    /// </summary>
    public int Quantity { get; set; }

    /// <summary>
    /// Gets or sets the related order.
    /// </summary>
    public Order Order { get; set; } = default!;

    /// <summary>
    /// Gets or sets the related product.
    /// </summary>
    public Product Product { get; set; } = default!;
}
